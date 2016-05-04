/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mfiottoporepresenter;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.HashMap;
import javax.swing.JPanel;
import mfiottoposetting.Domain;
import mfiottoposetting.EmulationEnvironment;
import mfiottoposetting.Node;

/**
 *
 * @author ubuntu
 */
public class EmulationEnvironmentRepresenter extends JPanel {

    private final EmulationEnvironment _env;
    private final HashMap<Domain, DomainRepresenter> _domains = new HashMap<>();
    private final HashMap<Node, NodeRepresenter> _nodes = new HashMap<>();

    private final JPanel _domainLayer = new JPanel(null, true);
    private final JPanel _nodeLayer = new JPanel(null, true);
    private final RepaintHandler _handler = new RepaintHandler() {

        @Override
        public void onRepaint(Object obj) {
            repaint();
        }
    };

    public EmulationEnvironmentRepresenter(EmulationEnvironment env) {
        this.setLayout(null);
        _env = env;
        setBounds(0, 0, _env.getViewWidth(), _env.getViewHeight());
        setPreferredSize(new Dimension(_env.getViewWidth(), _env.getViewHeight()));

        _nodeLayer.setOpaque(false);
        _nodeLayer.setBounds(0, 0, _env.getViewWidth(), _env.getViewHeight());
        add(_nodeLayer);
        _domainLayer.setOpaque(false);
        _domainLayer.setBounds(0, 0, _env.getViewWidth(), _env.getViewHeight());
        add(_domainLayer);

        for (Domain d : _env.getDomains()) {
            Node gateway = d.getGateway();
            if (gateway == null || !d.inDomain(gateway)) {
                System.out.printf("Domain %s does not have a valid gateway.%n", d.getName());
                continue;
            }
            boolean collision = false;
            for (Domain dx : _domains.keySet()) {
                if (d.isCollision(dx)) {
                    System.out.printf("Domain %s collide with %s, not added.%n", d.getName(), dx.getName());
                    collision = true;
                    break;
                }
            }
            if (collision) {
                continue;
            }

            DomainRepresenter dr = new DomainRepresenter(d, _nodes);
            dr.addRepaintHandler(_handler);
            _domainLayer.add(dr);
            _domains.put(d, dr);
            NodeRepresenter nr = new NodeRepresenter(d.getGateway(), _env.getNodeSize(), true);
            nr.addRepaintHandler(_handler);
            _nodeLayer.add(nr);
            _nodes.put(gateway, nr);
        }
        
        for (Node n : _env.getNodes()) {
            NodeRepresenter nr = new NodeRepresenter(n, _env.getNodeSize(), false);
            nr.addRepaintHandler(_handler);
            _nodeLayer.add(nr);
            _nodes.put(n, nr);
        }

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g); //To change body of generated methods, choose Tools | Templates.
        g.setColor(Color.black);
        g.drawRect(0, 0, _env.getViewWidth() - 1, _env.getViewHeight() - 1);
    }

}
