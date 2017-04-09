/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package my.trainguiregular;

import com.digi.xbee.api.listeners.*;
import com.digi.xbee.api.packet.common.*;
import com.digi.xbee.api.packet.XBeePacket;
import com.digi.xbee.api.utils.HexUtils;

/**
 *
 * @author 1
 */
public class PacketReceiveListener implements IPacketReceiveListener{
    TrainGUIRegularUI gui = null;
    @Override
    public final void packetReceived(XBeePacket packet) {
        if(gui != null) {
            // Implement code to handle status messages here
        }
    }
    public final void setGUIInstance(TrainGUIRegularUI instance) {
        gui = instance;
    }
}
