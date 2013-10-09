package phyml.gui.view;

import org.jdesktop.swingx.HorizontalLayout;
import phyml.gui.model.AbstractNode;
import phyml.gui.model.AbstractProperty;

import javax.swing.*;
import java.awt.*;

/**
 * Project: grisu
 * <p/>
 * Written by: Markus Binsteiner
 * Date: 10/10/13
 * Time: 9:14 AM
 */
public abstract class AbstractInputForm extends JPanel implements InputFormPanel {

    private static JPanel assemblePropertyForm(AbstractProperty p) {

        JPanel panel = new JPanel();
        panel.setMinimumSize(new Dimension(400,32));
        panel.setMaximumSize(new Dimension(400,32));
        //panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setLayout(new HorizontalLayout());
        JLabel label = new JLabel(p.getLabel());
        panel.add(label);

        panel.add(p.getComponent());

        return panel;
    }

    protected static JPanel assembleNodeForm(AbstractNode node) {
        JPanel panel = new JPanel();
//        panel.setLayout(new VerticalLayout());
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        for ( String name : node.getProperties().keySet() ) {
            JPanel temp = assemblePropertyForm(node.getProperties().get(name));
            panel.add(temp);
        }
        return panel;
    }

    public AbstractInputForm() {
        super();
    }

    @Override
    public JPanel getPanel() {
        return this;
    }



}
