/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.trainguiregular;

import com.digi.xbee.api.listeners.IDataReceiveListener;
import com.digi.xbee.api.models.XBeeMessage;
import com.digi.xbee.api.utils.HexUtils;
import javax.swing.*;

/**
 *
 * @author 1
 */
public class XBeeDataListener implements IDataReceiveListener {
	/*
	 * (non-Javadoc)
	 * @see com.digi.xbee.api.listeners.IDataReceiveListener#dataReceived(com.digi.xbee.api.models.XBeeMessage)
	 */
         TrainGUIRegularUI gui = null;
    
	@Override
	public final void dataReceived(XBeeMessage msg) {
            
            if(gui != null) 
                gui.dataReceived(msg.getDevice().get64BitAddress(), new String(msg.getData()));
                //gui.appendToConsole(String.format("From %s >> %s | %s%n", xbeeMessage.getDevice().get64BitAddress(), 
		//		HexUtils.prettyHexString(HexUtils.byteArrayToHexString(xbeeMessage.getData())), 
		//		new String(xbeeMessage.getData())));
	}
        /*
        Gives this listener the instance of the gui so that it can append received data to console
        */
        public final void setGUIInstance(TrainGUIRegularUI instance) {
            gui = instance;
        }
}

/**
 * Class to manage the XBee received data that was sent by other modules in the 
 * same network.
 * 
 * <p>Acts as a data listener by implementing the 
 * {@link IDataReceiveListener} interface, and is notified when new 
 * data for the module is received.</p>
 * 
 * @see IDataReceiveListener
 *
 */
