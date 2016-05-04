/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mfiottoposetting;

import java.util.LinkedList;
import com.google.gson.annotations.Expose;

/**
 *
 * @author ubuntu
 */
public class EmulationEnvironment {

    @Expose
    private int nodeSize;
    @Expose
    private int viewWidth;
    @Expose
    private int viewHeight;
    @Expose
    private LinkedList<Domain> domains;
    @Expose
    private LinkedList<Node> nodes;

    public int getViewWidth() {
        return viewWidth;
    }

    public int getViewHeight() {
        return viewHeight;
    }

    public int getNodeSize() {
        return nodeSize;
    }

    public LinkedList<Node> getNodes() {
        return nodes;
    }

    public LinkedList<Domain> getDomains() {
        return domains;
    }

    @Override
    public String toString() {
        return "EmulationEnvironment{" + "nodeSize=" + nodeSize + ", viewWidth=" + viewWidth + ", viewHeight=" + viewHeight + ", domains=" + domains + ", nodes=" + nodes + '}';
    }
}
