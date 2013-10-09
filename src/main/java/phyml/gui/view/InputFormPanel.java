package phyml.gui.view;

import phyml.gui.model.Node;

import javax.swing.*;

/**
 * Project: grisu
 * <p/>
 * Written by: Markus Binsteiner
 * Date: 10/10/13
 * Time: 9:12 AM
 */
public interface InputFormPanel {

    public void addNode(Node node);

    public JPanel getPanel();
}
