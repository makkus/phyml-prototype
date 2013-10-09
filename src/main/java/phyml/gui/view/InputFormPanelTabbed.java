package phyml.gui.view;

import phyml.gui.model.Node;

import javax.swing.*;
import java.awt.*;

/**
 * Project: grisu
 * <p/>
 * Written by: Markus Binsteiner
 * Date: 4/10/13
 * Time: 3:46 PM
 */
public class InputFormPanelTabbed extends AbstractInputForm {


    private final JTabbedPane tabbedPane = new JTabbedPane();

    public InputFormPanelTabbed() {
        super();
        setLayout(new BorderLayout());
        add(tabbedPane, BorderLayout.CENTER);
    }


    @Override
    public void addNode(Node node) {

        String name = node.getName();
        JPanel temp = assembleNodeForm(node);

        tabbedPane.addTab(name, temp);

    }


}
