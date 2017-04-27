#include <SoftwareSerial.h>

SoftwareSerial XBee(11, 1); // RX, TX
SoftwareSerial RFID(15, 0);

#define START_FRAME_MARKER 35
#define   END_FRAME_MARKER 36

// For L298n
int enable_pin = 10;
int in1_pin = 9;
int in2_pin = 8;

// For optical tie counter
int tieCount = 0;
int isTie = false;
int check = 0;
int tiesPassed = 0;
bool stopTieCounting = false;
int tiesToPass = 5;
String tieMsg = "Passed 5 Ties";

void setup() {
  pinMode(enable_pin, OUTPUT);
  pinMode(in1_pin, OUTPUT);
  pinMode(in2_pin, OUTPUT);

  pinMode(11, INPUT);
  pinMode(1, OUTPUT);
  pinMode(15, INPUT);
  pinMode(A5, INPUT_PULLUP);
  pinMode(2, INPUT);
  attachInterrupt(digitalPinToInterrupt(2), setRFIDListen, RISING);
  
  XBee.begin(9600);
  RFID.begin(9600);
  XBee.listen();
  
}

void loop() {
  if(XBee.available()) {
    listenForData();
  }
  if(RFID.available() > 0) {
    sendPositionData();
  }
  runTieCounter();
}

void listenForData() {
    uint8_t rcv;
    rcv = XBee.read();
    if(rcv != 0xff){
      if (rcv == 48) {// 0
        analogWrite(enable_pin, 0);
        stopTieCounting = true;
      }
      else if (rcv == 49) { // 1
        analogWrite(enable_pin, 105);
        stopTieCounting = false;
        tiesToPass = 1;
        tieMsg = "Passed one tie.";
      }
      else if (rcv == 50) { // 2
        analogWrite(enable_pin, 155);
        stopTieCounting = false;
        tiesToPass = 2;
        tieMsg = "Passed two ties.";
      }
      else if (rcv == 51) { // 3
        analogWrite(enable_pin, 205);
        stopTieCounting = false;
        tiesToPass = 5;
        tieMsg = "Passed five ties.";
      }
      else if (rcv == 52) { // 4
        analogWrite(enable_pin, 255);
        stopTieCounting = false;
        tiesToPass = 10;
        tieMsg = "Passed ten ties.";
      }
      else if (rcv == 0x46) { // F for Forward
        digitalWrite(in1_pin, LOW);
        digitalWrite(in2_pin, HIGH);
      }
      else if (rcv == 0x53) { // S for Stop motors
        digitalWrite(in1_pin, LOW);
        digitalWrite(in2_pin, LOW);
        stopTieCounting = true;
      }
      else if (rcv == 0x52) { // R for Reverse
        digitalWrite(in1_pin, HIGH);
        digitalWrite(in2_pin, LOW);
      }
    }
}

void sendPositionData() {
  byte val = 0;
  byte checksum = 0;
  byte bytesRead = 0;
  byte tempByte = 0;
  byte tagBytes[6]; // "Unique" tags are only 5 bytes but we need an extra byte for the checksum
  char tagValue[12];
  
  // Read from the RFID module. Because this connection uses SoftwareSerial
  // there is no equivalent to the Serial.available() function, so at this
  // point the program blocks while waiting for a value from the module
    if((val = RFID.read()) == 2) { // Check for header
      bytesRead = 0;
      while (bytesRead < 12) { // Read 10 digit code + 2 digit checksum
        if(RFID.available() > 0) {
          val = RFID.read();
          // Append the first 10 bytes (0 to 9) to the raw tag value
          if (bytesRead < 12) {
            tagValue[bytesRead] = val;
          }
          // Check if this is a header or stop byte before the 10 digit reading is complete
          if((val == 0x0D)||(val == 0x0A)||(val == 0x03)||(val == 0x02)) {
            break; // Stop reading
          }
          // Ascii/Hex conversion:
          if ((val >= '0') && (val <= '9')) {
            val = val - '0';
          }
          else if ((val >= 'A') && (val <= 'F')) {
            val = 10 + val - 'A';
          }
          // Every two hex-digits, add a byte to the code:
          if (bytesRead & 1 == 1) {
            // Make space for this hex-digit by shifting the previous digit 4 bits to the left
            tagBytes[bytesRead >> 1] = (val | (tempByte << 4));
            if (bytesRead >> 1 != 5) { // If we're at the checksum byte,
              checksum ^= tagBytes[bytesRead >> 1]; // Calculate the checksum... (XOR)
            }
          } else {
            tempByte = val; // Store the first hex digit first
          }
          bytesRead++;
         }
         else {
          // If nothing to read might as well spend some time elsewhere real quick. May be unnecessary
            runTieCounter();
         }
      }
      
      // Send the result to the host connected via USB
      if (bytesRead == 12) { // 12 digit read is complete
        // Start listening to Xbee regardless of whether it successfully read in a tag
        XBee.listen();
        tagValue[12] = '\0'; // Null-terminate the string
        XBee.write(tagValue);
        XBee.print("/");
        XBee.print(tiesPassed);
        tiesPassed = 0;
      }
      else {
        String msg = "Tag read failed. Resetting tie count.";
        XBee.print(msg);
      }
      bytesRead = 0;
    }
}
int avg = 0;
void runTieCounter() {
   // Tie Counter
  int value = analogRead(A5);
  if(value > 970 && !isTie) {
    check++;
    if(check > 10) {
      //if(avg > 970) {
      isTie = true;
      tiesPassed++;
      
    }
  }
  else if(value <= 960 && !isTie) {
     check = 0;
  }
  // The tie gives a more consistent value compared to the cork bed
  else if(value <= 960 && isTie) {
    isTie = false;
  }
//  if(tiesPassed == tiesToPass) {
//    tiesPassed = 0;
//    XBee.print(tieMsg);
//    check = 0;
//  }
}

void setRFIDListen()
{
  RFID.listen();
}

