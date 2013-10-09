package phyml.gui.view;

import phyml.gui.model.AbstractNode;

import javax.swing.*;

/**
 * Project: grisu
 * <p/>
 * Written by: Markus Binsteiner
 * Date: 10/10/13
 * Time: 9:12 AM
 */
public interface InputFormPanel {

    public void addNode(AbstractNode node);
    public JPanel getPanel();
}
