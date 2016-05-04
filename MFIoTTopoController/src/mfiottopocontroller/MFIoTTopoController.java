/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mfiottopocontroller;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import mfiotconnector.IPacketListener;
import mfiotconnector.MFIoTConnector;
import mfiotconnector.MFIoTConnectorPanel;
import packets.Packet;

/**
 *
 * @author ubuntu
 */
public class MFIoTTopoController {

    private static final String DEFAULT_PORT = "/dev/ttyACM0";

    /**
     * @param args the command line arguments
     * @throws java.lang.InterruptedException
     */
    public static void main(String[] args) throws InterruptedException, Exception {
        String acmID;
        if (args.length == 0) {
            acmID = DEFAULT_PORT;
            System.out.printf("default port %s chosen%n", DEFAULT_PORT);
        } else {
            acmID = args[0];
            System.out.printf("port %s chosen%n", acmID);
        }
        MFIoTConnector connector = new MFIoTConnector("/home/ubuntu/share/RIOT/dist/tools/pyterm/pyterm", acmID, "/home/ubuntu");

        connector.addPacketListener(new IPacketListener() {

            @Override
            public void handlePacket(IPacketListener.PacketMessageType type, Packet packet) {
                System.err.printf("Got packet [%s]:%n%s%n", type.toString(), packet);
            }
        });
        final MFIoTConnectorPanel panel = new MFIoTConnectorPanel(connector);

        final JFrame frame = new JFrame("Topology Controller on " + acmID);
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

//        String s1 = "2016-02-25 23:14:10,221 - INFO # ~~ SNIP  0 - size: 116 byte, type: NETTYPE_UNDEF (0)";
//        String s2 = "2016-02-25 23:14:10,227 - INFO # 000000 36 99 bc 5a d2 71 75 b1 e2 d6 7e b8 e7 ac 7c 11";
//        Pattern p1 = Pattern.compile("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2},\\d{3} - INFO #\\W+\\d+\\W+");
//        Pattern p2 = Pattern.compile("~~ SNIP\\W+\\d+\\W+-\\W+size:\\W+(\\d+)\\W+byte,\\W+type:\\W+(.+)\\W+\\(([-\\d]+)\\)");
//
//        Matcher m = p2.matcher(s1);
//        if (m.find()) {
//            System.out.println(m.group(0));
//            System.out.println(m.group(1));
//            System.out.println(m.group(2));
//            System.out.println(m.group(3));
//        }
//        m = p1.matcher(s2);
//        if (m.find()) {
//            System.out.println(m.group(0));
//            System.out.println(s2.substring(m.end()));
//            String s3 = s2.substring(m.end());
//            String[] tmp = s3.split("\\W");
//            for (String b : tmp) {
//                if (b.length() != 2) throw new Exception("");
//                byte x = (byte)Integer.parseInt(b, 16);
//                
//                System.out.println(x);
//            }
//        }
    }

}
