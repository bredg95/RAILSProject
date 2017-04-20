
package my.trainguiregular;

import com.digi.xbee.api.XBeeDevice;
import com.digi.xbee.api.exceptions.XBeeException;
import com.digi.xbee.api.models.*;
import com.digi.xbee.api.packet.common.*;
import javax.swing.*;
import java.util.HashMap;
import javax.swing.text.DefaultCaret;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Pattern;

/**
 *
 * @author Matt
 */
public class TrainGUIRegularUI extends javax.swing.JFrame {
    // TODO Replace with the serial port where your receiver module is connected.
	private static final String PORT = "COM3";
	// TODO Replace with the baud rate of you receiver module.
	private static final int BAUD_RATE = 9600;
        
        private final HashMap <String, String> addressMap = new HashMap<String, String>();
        
        public XBeeDevice myDevice = null;
        private DefaultListModel trainListModel = null;
    /**
     * Creates new form TrainGUIRegularUI
     */
    public TrainGUIRegularUI() {
        initComponents();
        setConnection(PORT, BAUD_RATE);
        DefaultCaret caret = (DefaultCaret) consoleOutput.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        this.setTitle("RAILS Command Post");
        trainListModel = new DefaultListModel();
        trainList.setModel(trainListModel);
        trainListModel.addElement("Train 1");
        trainListModel.addElement("Train 2");
    }
    
    public void setConnection(String port, int baud) {
        myDevice = new XBeeDevice(port, baud);
        XBeeDataListener listener = new XBeeDataListener();
        listener.setGUIInstance(this);
        addressMap.put("Train 1", "13A2004105EE5A");
        addressMap.put("Train 2", "13A200415B7AE5");
        try {
                myDevice.open();

                myDevice.addDataListener(listener);

        } catch (XBeeException e) {
                consoleOutput.append(e.getMessage() + "\n");
        }
        
        this.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                if(myDevice.isOpen())
                    myDevice.close();
                System.exit(0);
            }
        });
    }
    
    public void addTrain(String name, String mac) {
        Iterator it = addressMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            if(name.compareTo((String)pair.getKey()) == 0) {
                consoleOutput.append("List already contains this train.\n");
                return;
            }
        }
        if(!Pattern.matches("[a-fA-F0-9]{14}", mac)){
            consoleOutput.append("The MAC address is not valid. Make sure address has 14 characters and is in hexadecimal format.\n");
            return;
        }
        addressMap.put(name, mac);
        trainListModel.addElement(name);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        consoleOutput = new javax.swing.JTextArea();
        speedSlider = new javax.swing.JSlider();
        forwardRadioButton = new javax.swing.JRadioButton();
        brakeRadioButton = new javax.swing.JRadioButton();
        reverseRadioButton = new javax.swing.JRadioButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        trainList = new javax.swing.JList<>();
        addTrainButton = new javax.swing.JButton();
        deleteTrainButton = new javax.swing.JButton();
        sendButton = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        setConnMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(204, 204, 204));
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/my/trainguiregular/Resources/track_layout.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 490, Short.MAX_VALUE)
                .addContainerGap())
        );

        consoleOutput.setColumns(20);
        consoleOutput.setRows(5);
        consoleOutput.setName("consoleOutput"); // NOI18N
        jScrollPane2.setViewportView(consoleOutput);

        speedSlider.setMajorTickSpacing(1);
        speedSlider.setMaximum(4);
        speedSlider.setMinorTickSpacing(1);
        speedSlider.setOrientation(javax.swing.JSlider.VERTICAL);
        speedSlider.setPaintLabels(true);
        speedSlider.setPaintTicks(true);
        speedSlider.setSnapToTicks(true);
        speedSlider.setValue(0);

        buttonGroup1.add(forwardRadioButton);
        forwardRadioButton.setText("Forward");

        buttonGroup1.add(brakeRadioButton);
        brakeRadioButton.setSelected(true);
        brakeRadioButton.setText("Brake");

        buttonGroup1.add(reverseRadioButton);
        reverseRadioButton.setText("Reverse");

        jScrollPane1.setViewportView(trainList);

        addTrainButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/my/trainguiregular/Resources/plus.png"))); // NOI18N
        addTrainButton.setMaximumSize(new java.awt.Dimension(669, 669));
        addTrainButton.setMinimumSize(new java.awt.Dimension(669, 669));
        addTrainButton.setPreferredSize(new java.awt.Dimension(669, 669));
        addTrainButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addTrainButtonActionPerformed(evt);
            }
        });

        deleteTrainButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/my/trainguiregular/Resources/blue.png"))); // NOI18N
        deleteTrainButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteTrainButtonActionPerformed(evt);
            }
        });

        sendButton.setText("Send");
        sendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendButtonActionPerformed(evt);
            }
        });

        jMenu1.setText("File");

        setConnMenuItem.setText("Set Connection");
        setConnMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setConnMenuItemActionPerformed(evt);
            }
        });
        jMenu1.add(setConnMenuItem);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(forwardRadioButton)
                            .addComponent(brakeRadioButton)
                            .addComponent(reverseRadioButton))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(speedSlider, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(addTrainButton, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deleteTrainButton, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sendButton))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(addTrainButton, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(deleteTrainButton, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addComponent(speedSlider, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(forwardRadioButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(brakeRadioButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(reverseRadioButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(sendButton)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void sendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendButtonActionPerformed
        // Edit this in regards to train direction
        if(trainList.getSelectedIndex() < 0) {
            consoleOutput.append("Must select a train.\n");
            return;
        }
        if(!myDevice.isOpen()) {
            consoleOutput.append("No connection available.");
            return;
        }
        XBee64BitAddress dest64 = new XBee64BitAddress(hexStringToByteArray(addressMap.get(trainList.getSelectedValue())));
        // Should be set to this default
        XBee16BitAddress dest16 = new XBee16BitAddress(hexStringToByteArray("FFFE"));
        
        if(forwardRadioButton.isSelected()) {
            byte[] data = "F".getBytes();
            TransmitPacket packet = new TransmitPacket(1, dest64, dest16, 0, 0, data);
            try {
                myDevice.sendPacket(packet, new PacketReceiveListener());
            }
            catch (XBeeException e) {
                e.printStackTrace();
            }
        }
        else if(reverseRadioButton.isSelected()) {
            byte[] data = "R".getBytes();
            TransmitPacket packet = new TransmitPacket(1, dest64, dest16, 0, 0, data);
            try {
                myDevice.sendPacket(packet, new PacketReceiveListener());
            }
            catch (XBeeException e) {
                e.printStackTrace();
            }
        }
        else { // Stop Motor
            byte[] data = "S".getBytes();
            TransmitPacket packet = new TransmitPacket(1, dest64, dest16, 0, 0, data);
            try {
                myDevice.sendPacket(packet, new PacketReceiveListener());
            }
            catch (XBeeException e) {
                e.printStackTrace();
            }
            finally{
                return;
            }
        }
        byte [] data = Integer.toString(speedSlider.getValue()).getBytes();
        TransmitPacket packet = new TransmitPacket(1, dest64, dest16, 0, 0, data);
        try {
            myDevice.sendPacket(packet, new PacketReceiveListener());
        }
        catch (XBeeException e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_sendButtonActionPerformed

    private void setConnMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_setConnMenuItemActionPerformed
        // TODO add your handling code here:
        JTextField port = new JTextField();
        JTextField baudRate = new JTextField();
        final JComponent[] inputs = new JComponent[] {
                new JLabel("Port"),
                port,
                new JLabel("Baud Rate"),
                baudRate
        };
        int result = JOptionPane.showConfirmDialog(null, inputs, "Set Connection", JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String portVal = port.getText();
            int baudVal = 0;
            try {
                baudVal = Integer.parseInt(baudRate.getText());
            }
            catch(NumberFormatException e) {
                consoleOutput.append("Invalid format for baud rate.\n");
                return;
            }
            setConnection(portVal, baudVal);
        } else {
            System.out.println("User canceled / closed the dialog, result = " + result);
        }
    }//GEN-LAST:event_setConnMenuItemActionPerformed

    private void addTrainButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addTrainButtonActionPerformed
        JTextField name = new JTextField();
        JTextField mac = new JTextField();
        final JComponent[] inputs = new JComponent[] {
                new JLabel("Name"),
                name,
                new JLabel("MAC Address"),
                mac
        };
        int result = JOptionPane.showConfirmDialog(null, inputs, "Set Connection", JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String nameVal = name.getText();
            String macVal = mac.getText();
            addTrain(nameVal, macVal);
        } else {
            System.out.println("User canceled / closed the dialog, result = " + result);
        }
    }//GEN-LAST:event_addTrainButtonActionPerformed

    private void deleteTrainButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteTrainButtonActionPerformed
        if(trainList.isSelectionEmpty()){
            consoleOutput.append("No train selected.\n");
            return;
        }
        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this train?", "Warning", dialogButton);
        if(dialogResult != 0) {
          return;
        } 
        int index = trainList.getSelectedIndex();
        addressMap.remove((String)trainListModel.get(index));
        trainListModel.remove(index);
    }//GEN-LAST:event_deleteTrainButtonActionPerformed

    public static byte[] hexStringToByteArray(String s) {
    int len = s.length();
    byte[] data = new byte[len / 2];
    for (int i = 0; i < len; i += 2) {
        data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                             + Character.digit(s.charAt(i+1), 16));
    }
    return data;
}
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TrainGUIRegularUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TrainGUIRegularUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TrainGUIRegularUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TrainGUIRegularUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TrainGUIRegularUI().setVisible(true);
            }
        });
    }
    
    public static TrainGUIRegularUI getInstance() {
        return instance;
    }
    private static TrainGUIRegularUI instance;
    
    public final void appendToConsole(String output) {
        consoleOutput.append(output);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addTrainButton;
    private javax.swing.JRadioButton brakeRadioButton;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JTextArea consoleOutput;
    private javax.swing.JButton deleteTrainButton;
    private javax.swing.JRadioButton forwardRadioButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JRadioButton reverseRadioButton;
    private javax.swing.JButton sendButton;
    private javax.swing.JMenuItem setConnMenuItem;
    private javax.swing.JSlider speedSlider;
    private javax.swing.JList<String> trainList;
    // End of variables declaration//GEN-END:variables
}
