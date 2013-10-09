package phyml.gui.view;

import phyml.gui.model.AbstractNode;

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
    public void addNode(AbstractNode node) {

        add(new JLabel(node.getName()));
        JPanel temp = assembleNodeForm(node);
        add(temp);
        add(new JSeparator());

    }


}
