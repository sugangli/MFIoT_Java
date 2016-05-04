/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package aggregator;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.xml.bind.DatatypeConverter;
import mfiotconnector.IPacketListener;
import mfiotconnector.MFIoTConnector;
import mfiotconnector.MFIoTConnectorPanel;
import packets.MFIoTAppPacket;
import packets.Packet;
import tables.GUIDEntry;
import tables.GUIDTable;
import edu.rutgers.winlab.jmfapi.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author suggang
 */
public class Aggregator {

    private static final String DEFAULTPORT = "/dev/ttyACM0";
    private final static GUIDTable _g_table = new GUIDTable();
    private static JMFAPI agg_service;
    
    /**
     * @param args the command line arguments
     * @throws java.lang.InterruptedException
     * @throws edu.rutgers.winlab.jmfapi.JMFException
     */
    public static void main(String[] args) throws InterruptedException, JMFException {
        String acmID;

        if (args.length == 0){
            acmID = DEFAULTPORT;
            System.out.println("Target Port:"+acmID);
        }else{
            acmID = args[0];
            System.out.println("Target Port:"+acmID);
        }
        _g_table.add(new GUIDEntry(DatatypeConverter.parseHexBinary("0102"), DatatypeConverter.parseHexBinary("0000000000000000000000000000000000000102"), "L"));
        _g_table.add(new GUIDEntry(DatatypeConverter.parseHexBinary("0101"), DatatypeConverter.parseHexBinary("0000000000000000000000000000000000000101"), "L"));
     
        
        MFIoTConnector connector = new MFIoTConnector("/home/suggang/RIOT/dist/tools/pyterm/pyterm", acmID, "/home/suggang");
        agg_service = new JMFAPI();
        Runtime.getRuntime().addShutdownHook(
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        System.err.println("Closing");
                        try {
                            if(agg_service.isOpen())
                                agg_service.jmfclose();
                        } catch (JMFException ex) {
                            Logger.getLogger(Aggregator.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                })
        );

        
        connector.addPacketListener(new IPacketListener() {
            @Override
            public void handlePacket(IPacketListener.PacketMessageType type, Packet packet) {
//                System.err.printf("Got packet [%s]:%n%s%n", type.toString(), packet);

                MFIoTAppPacket m_packet = new MFIoTAppPacket(packet.getNext());

                byte[] src_luid, dst_luid;
                src_luid = m_packet.getSrc();
                dst_luid = m_packet.getDst();
                System.err.println("src:" + DatatypeConverter.printHexBinary(src_luid) + " dst:" + DatatypeConverter.printHexBinary(dst_luid));

                GUIDEntry src_entry = _g_table.lookupByLUID(src_luid);
                GUIDEntry dst_entry = _g_table.lookupByLUID(dst_luid);

                if (src_entry != null && dst_entry != null){
                   System.err.printf("src & dst found!");
                   
                   GUID src_guid = new GUID(src_entry.getGuidInt());
                   GUID dst_guid = new GUID(dst_entry.getGuidInt());
                   
                    try {
                        agg_service.jmfopen("basic", src_guid);
                        byte[] payload = packet.getNext().getNext().getBuf();
                        int ret = agg_service.jmfsend(payload, payload.length, dst_guid);
                        System.err.printf("ret=%d%n", ret);
                        agg_service.jmfclose();
                    } catch (JMFException ex) {
                        Logger.getLogger(Aggregator.class.getName()).log(Level.SEVERE, null, ex);
                    }
                   
                }else{
                   System.err.println("src or dst not found in the GUID table");
                }




                
                

                
            }
        });

        final MFIoTConnectorPanel panel = new MFIoTConnectorPanel(connector);

        final JFrame frame = new JFrame("Aggregator on " + acmID);
        frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowOpened(WindowEvent e) {
                panel.grabFocus();
            }

        });
        
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setVisible(true);
        Thread.sleep(300);
        frame.setLocation(0, 0);
        Thread.sleep(300);
        connector.start();
        
    }
    
    private class MFPacketListener extends Thread {
        public void run(){

            System.out.println("Aggregator:Host Service is Running.");
            try {
                    GUID senderGUID = new GUID();
                    byte[] buf = new byte[1000000];
                    int recv_size;
                    while(true){//One client at a time for now
                        recv_size = agg_service.jmfrecv_blk(senderGUID, buf, buf.length);
                        Thread mThread = new Thread(new MessageHandleWorker(buf));
                        mThread.start();
                    }
            } catch (JMFException e) { 
                    // TODO Auto-generated catch block
                    e.printStackTrace();
            }


                }
    
    }

    
}
