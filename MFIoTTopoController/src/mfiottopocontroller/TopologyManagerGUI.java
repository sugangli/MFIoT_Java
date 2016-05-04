/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mfiottopocontroller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
//import javax.swing.JScrol lPane;
import mfiottoporepresenter.EmulationEnvironmentRepresenter;
import mfiottoposetting.EmulationEnvironment;

/**
 *
 * @author ubuntu
 */
public class TopologyManagerGUI {

    public static void main(String[] args) {
        final Gson gson = new GsonBuilder().serializeNulls().excludeFieldsWithoutExposeAnnotation().create();
        final EmulationEnvironment env;
        final JFrame frame;
        try (FileReader reader = new FileReader("topology.json")) {
            env = gson.fromJson(reader, EmulationEnvironment.class);

        } catch (IOException ex) {
            Logger.getLogger(TopologyManagerGUI.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        frame = new JFrame("Test");
        EmulationEnvironmentRepresenter p = new EmulationEnvironmentRepresenter(env);
//            frame.getContentPane().add(new JScrollPane(p), BorderLayout.CENTER);
        frame.add(p, BorderLayout.CENTER);
        frame.setLocation(new Point(0, 0));
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                saveEnv(gson.toJson(env));
            }

        });
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                frame.setVisible(true);
            }
        });
        //            frame.setVisible(true);
        //            Thread.sleep(100);
        //            frame.setLocation(new Point(0, 0));   
    }

    private static void saveEnv(String env) {
        try (PrintStream ps = new PrintStream("topology.json")) {
            ps.println(env);
            ps.flush();
        } catch (IOException ex) {
            Logger.getLogger(TopologyManagerGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
