/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mfiottoporepresenter;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.Point;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import mfiottoposetting.Domain;
import mfiottoposetting.Node;

/**
 *
 * @author ubuntu
 */
public class DomainRepresenter extends RepaintRequester {

    private static final float[] RANGE_DIST = {.0f, .3f, 1.0f};
    private static final int[] RANGE_ALPHAS = {128, 64, 0};
    private static final Color[] RANGE_COLORS;

    private static final float[] LINK_DIST = {.0f, 1.0f};
    private static final Color[] LINK_COLORS = {Color.black, Color.lightGray};

    static {
        Color[] tmpColors = {
            Color.getHSBColor(1 / 3f, 1f, 1f),
            Color.getHSBColor(1 / 3f, 1f, 1f),
            Color.getHSBColor(1 / 3f, 1f, 1f)
        };
        RANGE_COLORS = new Color[tmpColors.length];
        for (int i = 0; i < tmpColors.length; i++) {
            RANGE_COLORS[i] = new Color(tmpColors[i].getRed(), tmpColors[i].getGreen(), tmpColors[i].getBlue(), RANGE_ALPHAS[i]);
        }
    }

    private final Domain _domain;
    private final HashMap<Node, NodeRepresenter> _nodes;
    private boolean _highlight = false;

    public DomainRepresenter(Domain d, HashMap<Node, NodeRepresenter> nodes) {
        _domain = d;
        _nodes = nodes;
        this.setLayout(null);
        setBackground(Color.white);
        setBounds(_domain.getLeft() - _domain.getWidth() / 2, _domain.getTop() - _domain.getHeight() / 2, _domain.getWidth(), _domain.getHeight());
        setToolTipText(_domain.getName());

        addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println(_domain);
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                _highlight = true;
                callRepaints();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                _highlight = false;
                callRepaints();
            }
        });
    }

    private static void drawDirectedLink(Graphics2D g2d, Point from, Point to) {
        LinearGradientPaint paint = new LinearGradientPaint(from, to, LINK_DIST, LINK_COLORS);
        g2d.setPaint(paint);
        g2d.drawLine(from.x, from.y, to.x, to.y);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g); //To change body of generated methods, choose Tools | Templates.

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        ArrayList<Node> nodesInDomain = new ArrayList<>();
        ArrayList<Point> pointsInDomain = new ArrayList<>();

        for (Node n : _nodes.keySet()) {
            if (_domain.inDomain(n)) {
                nodesInDomain.add(n);
                Point p = _domain.relativePosition(n);
                pointsInDomain.add(p);
//                System.out.printf("%s, %s, %s%n", n, _domain, p);
                Point2D center = new Point2D.Float(p.x, p.y);
                float radius = n.getRange() * 2;
                RadialGradientPaint paint = new RadialGradientPaint(center, radius, RANGE_DIST, RANGE_COLORS);
                g2d.setPaint(paint);
                g2d.fillOval(p.x - n.getRange(), p.y - n.getRange(), n.getRange() * 2, n.getRange() * 2);

            }
        }

        for (int i = 0; i < nodesInDomain.size(); i++) {
            Node nodeI = nodesInDomain.get(i);
            Point pI = pointsInDomain.get(i);
            g2d.setColor(Color.black);
            g2d.drawOval(pI.x - nodeI.getRange(), pI.y - nodeI.getRange(), nodeI.getRange() * 2, nodeI.getRange() * 2);
            for (int j = i + 1; j < nodesInDomain.size(); j++) {
                Node nodeJ = nodesInDomain.get(j);
                Point pJ = pointsInDomain.get(j);
                boolean iCanReachj = nodeI.canReach(nodeJ);
                boolean jCanReachi = nodeJ.canReach(nodeI);
                if (iCanReachj && jCanReachi) {
                    g2d.setColor(Color.black);
                    g2d.drawLine(pJ.x, pJ.y, pI.x, pI.y);
                } else if (iCanReachj) {
                    drawDirectedLink(g2d, pI, pJ);
                } else if (jCanReachi) {
                    drawDirectedLink(g2d, pJ, pI);
                } else {

                }
            }
        }

        g2d.setColor(_highlight ? Color.red : Color.black);
        g2d.drawRect(0, 0, _domain.getWidth() - 1, _domain.getHeight() - 1);
        FontMetrics metrics = g2d.getFontMetrics();
        int width = metrics.stringWidth(_domain.getName());
        int height = metrics.getHeight();
        g2d.drawString(_domain.getName(), (_domain.getWidth() - width) / 2, 5 + height);
    }

}
