/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.trainguiregular;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.digi.xbee.api.XBeeDevice;
import com.digi.xbee.api.exceptions.XBeeException;


/**
 *
 * @author Matt
 */
public class TrainGUIRegular extends Application {
    private static final String DATA_TO_SEND = "Hello XBee World!";
        /* Constants */
	
	// TODO Replace with the serial port where your receiver module is connected.
	private static final String PORT = "COM3";
	// TODO Replace with the baud rate of you receiver module.
	private static final int BAUD_RATE = 9600;
    @Override
    public void start(Stage primaryStage) {
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        XBeeDevice myDevice = new XBeeDevice(PORT, BAUD_RATE);
        try {
                myDevice.open();

                myDevice.addDataListener(new XBeeDataListener());

                System.out.println("\n>> Waiting for data...");

        } catch (XBeeException e) {
                e.printStackTrace();
        }
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                TrainGUIRegularUI gui = new TrainGUIRegularUI();
                gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                gui.pack();
                gui.setVisible(true);
            }
        });
    }
    
}
