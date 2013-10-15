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

    /** Returns an ImageIcon, or null if the path was invalid. */
    protected static ImageIcon createImageIcon(String path,
                                           String description) {
    java.net.URL imgURL = InputFormPanelCollapsible.class.getResource(path);
    if (imgURL != null) {
        return new ImageIcon(imgURL, description);
    } else {
        System.err.println("Couldn't find file: " + path);
        return null;
    }
}

    public static final ImageIcon TOGGLE = createImageIcon("/toggle.png", "collapsed");
    public static final ImageIcon TOGGLE_EXPAND = createImageIcon("/toggle_expand.png", "expanded");


    public InputFormPanelCollapsible() {
        super();
        setLayout(new VerticalLayout());
    }


    @Override
    public void addNode(final Node node) {

        String name = node.getName();
        JPanel temp = assembleNode(node);

        final JXCollapsiblePane cp = new JXCollapsiblePane();
        final JToggleButton collapse = new JToggleButton(name, TOGGLE);
        collapse.setSelectedIcon(TOGGLE_EXPAND);
        collapse.setHorizontalAlignment(SwingConstants.LEFT);
        collapse.setSelected(true);
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
