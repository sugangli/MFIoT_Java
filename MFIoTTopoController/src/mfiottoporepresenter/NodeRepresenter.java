/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mfiottoporepresenter;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import mfiottoposetting.Node;

/**
 *
 * @author ubuntu
 */
public class NodeRepresenter extends RepaintRequester {

    private static final float[] NODE_DIST = {.0f, .3f, 1.0f};

    private static final Color[] GW_COLORS = {
        Color.getHSBColor(0 / 3f, 0.5f, 2f),
        Color.getHSBColor(0 / 3f, 0.5f, 1f),
        Color.getHSBColor(0 / 3f, 0.5f, 0.5f)};

    private static final Color[] NODE_COLORS = {
        Color.getHSBColor(2 / 3f, 0.5f, 2f),
        Color.getHSBColor(2 / 3f, 0.5f, 1f),
        Color.getHSBColor(2 / 3f, 0.5f, 0.5f)};

    private static final Color[] NODE_HIGHLIGHT_COLORS = {
        Color.getHSBColor(1 / 6f, 0.5f, 2f),
        Color.getHSBColor(1 / 6f, 0.5f, 1f),
        Color.getHSBColor(1 / 6f, 0.5f, 0.5f)};

    private final Node _node;
    private final int _nodeSize;
    private final RadialGradientPaint _paint;
    private final RadialGradientPaint _highlightPaint;
    private boolean _highlight = false;

    private boolean _movable = true;
    private Point lastMousePoint;

    public boolean isMovable() {
        return _movable;
    }

    public NodeRepresenter(Node n, int nodeSize, boolean isGateway) {
        _node = n;
        _nodeSize = nodeSize;
        _movable = !isGateway;
        setToolTipText(String.format("%s(%s)", _node.getName(), _node.getMac()));

        Point2D center = new Point2D.Float(_nodeSize * .6f, _nodeSize * .4f);
        float radius = _nodeSize / 2;
        _paint = new RadialGradientPaint(center, radius, NODE_DIST, isGateway ? GW_COLORS : NODE_COLORS);
        _highlightPaint = new RadialGradientPaint(center, radius, NODE_DIST, NODE_HIGHLIGHT_COLORS);

        updateNodePosition();
        setOpaque(false);

        addMouseMotionListener(new MouseMotionListener() {

            @Override
            public void mouseDragged(MouseEvent e) {
                if (!_movable) {
                    return;
                }
                Point p = e.getLocationOnScreen();
                _node.setLeft(_node.getLeft() + p.x - lastMousePoint.x);
                _node.setTop(_node.getTop() + p.y - lastMousePoint.y);
                lastMousePoint = p;
                updateNodePosition();
            }

            @Override
            public void mouseMoved(MouseEvent e) {
            }
        });

        this.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println(_node);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (!_movable) {
                    return;
                }
                lastMousePoint = e.getLocationOnScreen();
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

    private void updateNodePosition() {
        setBounds(_node.getLeft() - _nodeSize / 2, _node.getTop() - _nodeSize / 2, _nodeSize, _nodeSize);
        callRepaints();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (_highlight) {
            g2d.setPaint(_highlightPaint);
        } else {
            g2d.setPaint(_paint);
        }
        g2d.fillOval(0, 0, _nodeSize, _nodeSize);
    }

}
