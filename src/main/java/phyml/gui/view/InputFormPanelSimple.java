package phyml.gui.view;

import phyml.gui.model.Node;

import javax.swing.*;

/**
 * Project: grisu
 * <p/>
 * Written by: Markus Binsteiner
 * Date: 4/10/13
 * Time: 3:46 PM
 */
public class InputFormPanelSimple extends AbstractInputForm {

    public InputFormPanelSimple() {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    @Override
    public void addNode(Node node) {

        add(new JLabel(node.getName()));
        JPanel temp = assembleNode(node);
        add(temp);
        add(new JSeparator());

    }


}
