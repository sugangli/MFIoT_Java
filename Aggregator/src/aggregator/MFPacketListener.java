/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package aggregator;

import edu.rutgers.winlab.jmfapi.GUID;
import edu.rutgers.winlab.jmfapi.JMFAPI;
import edu.rutgers.winlab.jmfapi.JMFException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author suggang
 */
public class MFPacketListener implements Runnable{
    
    private JMFAPI host_service;
    private byte[] buf;
    private GUID sender_guid;

    public MFPacketListener (JMFAPI h_service, byte[] buffer, GUID s_guid){
        
        host_service = h_service;
        buf = buffer;
        sender_guid = s_guid;
    
    }
    @Override
    public void run() {
        while(true){
            try {
                int recv_size;
                recv_size = host_service.jmfrecv_blk(sender_guid, buf, buf.length);
            } catch (JMFException ex) {
                Logger.getLogger(MFPacketListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    
}
