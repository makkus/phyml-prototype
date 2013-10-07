package phyml.gui.view;

import phyml.gui.model.AbstractNode;
import phyml.gui.model.AbstractProperty;

import javax.swing.*;
import java.awt.*;

/**
 * Project: grisu
 * <p/>
 * Written by: Markus Binsteiner
 * Date: 4/10/13
 * Time: 3:46 PM
 */
public class InputFormPanel extends JPanel {

    private static JPanel assemblePropertyForm(AbstractProperty p) {

        JPanel panel = new JPanel();
        panel.setMinimumSize(new Dimension(400,32));
        panel.setMaximumSize(new Dimension(400,32));
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        JLabel label = new JLabel(p.getLabel());
        panel.add(label);

        panel.add(p.getComponent());

        return panel;
    }


    public InputFormPanel() {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    public void addNode(AbstractNode node) {

        add(new JLabel(node.getName()));
        for ( String name : node.getProperties().keySet() ) {
            JPanel temp = assemblePropertyForm(node.getProperties().get(name));
            add(temp);
        }
        add(new JSeparator());

    }


}
