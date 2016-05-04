/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mfiottoposetting;

import com.google.gson.annotations.Expose;
import java.awt.Dimension;
import java.awt.Point;

/**
 *
 * @author ubuntu
 */
public class Domain {

    @Expose
    private String name;
    @Expose
    private int left;
    @Expose
    private int top;
    @Expose
    private int width;
    @Expose
    private int height;
    @Expose
    private Node gateway;

    public Node getGateway() {
        return gateway;
    }

    public String getName() {
        return name;
    }

    public int getLeft() {
        return left;
    }

    public int getTop() {
        return top;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isCollision(Domain d2) {
        int minX = Math.max(left - width / 2, d2.left - d2.width / 2);
        int maxX = Math.min(left - width / 2 + width, d2.left - d2.width / 2 + d2.width);
        int minY = Math.max(top - height / 2, d2.top - d2.height / 2);
        int maxY = Math.min(top - height / 2 + height, d2.top - d2.height / 2 + d2.height);
        return minX < maxX && minY < maxY;
    }

    public boolean inDomain(Node n) {
        return n.getTop() >= top - height / 2 && n.getTop() <= top - height / 2 + height
                && n.getLeft() >= left - width / 2 && n.getLeft() <= left - width / 2 + width;
    }

    public Point relativePosition(Node n) {
        return new Point(n.getLeft() - left + width / 2, n.getTop() - top + height / 2);
    }

    @Override
    public String toString() {
        return "Domain{" + "name=" + name + ", left=" + left + ", top=" + top + ", width=" + width + ", height=" + height + '}';
    }

}
