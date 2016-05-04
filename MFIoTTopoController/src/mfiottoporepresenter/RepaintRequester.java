/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mfiottoporepresenter;

import java.util.LinkedList;
import javax.swing.JPanel;

/**
 *
 * @author ubuntu
 */
public class RepaintRequester extends JPanel {

    private final LinkedList<RepaintHandler> _repaintHandlers = new LinkedList<>();
    
    public void addRepaintHandler(RepaintHandler r) {
        _repaintHandlers.add(r);
    }
    
    public void removeRepaintHandler(RepaintHandler r) {
        _repaintHandlers.remove(r);
    }
    
    protected void callRepaints() {
        for (RepaintHandler h : _repaintHandlers) {
            h.onRepaint(this);
        }
    }

}
