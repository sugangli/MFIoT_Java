/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mfiottoposetting;

import com.google.gson.annotations.Expose;
/**
 *
 * @author ubuntu
 */
public class Node {
    @Expose
    private String name;
    @Expose
    private String mac;
    @Expose
    private int top;
    @Expose
    private int left;
    @Expose
    private int range;

    public String getName() {
        return name;
    }

    public String getMac() {
        return mac;
    }

    public int getTop() {
        return top;
    }

    public int getLeft() {
        return left;
    }

    public int getRange() {
        return range;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public void setRange(int range) {
        this.range = range;
    }
    
    public boolean canReach(Node n) {
        int xDiff = n.left - left;
        int yDiff = n.top - top;
        return xDiff * xDiff + yDiff * yDiff <= range * range;
    }

    @Override
    public String toString() {
        return "Node{" + "name=" + name + ", mac=" + mac + ", top=" + top + ", left=" + left + ", range=" + range + '}';
    }
}
