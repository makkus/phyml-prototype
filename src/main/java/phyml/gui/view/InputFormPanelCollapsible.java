package phyml.gui.view;

import org.jdesktop.swingx.JXCollapsiblePane;
import org.jdesktop.swingx.VerticalLayout;
import phyml.gui.model.Node;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Project: grisu
 * <p/>
 * Written by: Markus Binsteiner
 * Date: 4/10/13
 * Time: 3:46 PM
 */
public class InputFormPanelCollapsible extends AbstractInputForm {


    public InputFormPanelCollapsible() {
        super();
        setLayout(new VerticalLayout());
    }


    @Override
    public void addNode(Node node) {

        String name = node.getName();
        JPanel temp = assembleNodeForm(node);

        final JXCollapsiblePane cp = new JXCollapsiblePane();
        JButton collapse = new JButton(node.getName());
        collapse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cp.isCollapsed()) {
                    cp.setCollapsed(false);
                } else {
                    cp.setCollapsed(true);
                }
            }
        });
        cp.setLayout(new BorderLayout());
        cp.add(temp);
        add(collapse);
        add(cp);
        cp.setCollapsed(false);
    }


}
