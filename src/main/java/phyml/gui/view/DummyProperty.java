package phyml.gui.view;

import com.google.common.collect.ImmutableSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import phyml.gui.model.AbstractProperty;
import phyml.gui.model.Node;

import javax.swing.*;
import java.util.Set;

/**
 * Project: grisu
 * <p/>
 * Written by: Markus Binsteiner
 * Date: 4/10/13
 * Time: 3:25 PM
 */
public class DummyProperty extends AbstractProperty {

    public static final Set<String> OPTION_KEYS = ImmutableSet.<String>of();

    private static final Logger myLogger = LoggerFactory.getLogger(DummyProperty.class);

    private JPanel panel;

    public DummyProperty(Node parent, String id, String group) {
        this(parent, id, id, group);
    }

    public DummyProperty(Node parent, String id, String label, String group) {
        super(parent, id, label, group);
    }
    public DummyProperty(Node parent, String id) {
        this(parent, id, null);
    }

    @Override
    public JComponent getComponent() {
        return getJPanel();
    }

    @Override
    protected void reInitialize() {

    }

    @Override
    protected void lockUI(boolean lock) {
    }

    protected JPanel getJPanel() {
        if ( panel == null ) {
            panel = new JPanel();
        }
        return panel;
    }

    @Override
    public void setValue(String value) {

    }

    @Override
    public Set<String> getOptionKeys() {
        return OPTION_KEYS;
    }



}
