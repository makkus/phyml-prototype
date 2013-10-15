package phyml.gui.view;

import com.google.common.collect.ImmutableSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import phyml.gui.model.AbstractProperty;
import phyml.gui.model.Node;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Set;

/**
 * Project: grisu
 * <p/>
 * Written by: Markus Binsteiner
 * Date: 4/10/13
 * Time: 5:20 PM
 */
public class RadioButtonProperty extends AbstractProperty {

    public static final String OPTION_1 = "OPTION_1";
    public static final String OPTION_2 = "OPTION_2";
    public static final Set<String> OPTION_KEYS = ImmutableSet.<String>of(OPTION_1, OPTION_2);

    private static final Logger myLogger = LoggerFactory.getLogger(RadioButtonProperty.class);

    private JRadioButton button1;
    private JRadioButton button2;

    private ButtonGroup group;
    private final JPanel panel = new JPanel(new GridLayout(1, 0));

    public RadioButtonProperty(Node parent, String id) {
        this(parent, id, null);
    }

    public RadioButtonProperty(Node parent, String id, String group) {
        this(parent, id, id, group);
    }
    public RadioButtonProperty(Node parent, String id, String label, String group) {
        super(parent, id, label, group);
        getButtonGroup();
        panel.add(getButton1());
        panel.add(getButton2());

    }

    private ButtonGroup getButtonGroup() {
        if (group == null) {
            group = new ButtonGroup();
            group.add(getButton1());
            group.add(getButton2());
        }
        return group;
    }

    private JRadioButton getButton1() {
        if (button1 == null) {
            button1 = new JRadioButton("n/a");
            button1.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if (ItemEvent.SELECTED == e.getStateChange()) {
                        valueChanged(button2.getText(), button1.getText());
                    }
                }
            });
        }
        return button1;
    }

    private JRadioButton getButton2() {
        if (button2 == null) {
            button2 = new JRadioButton("n/a");
            button2.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if (ItemEvent.SELECTED == e.getStateChange()) {
                        valueChanged(button1.getText(), button2.getText());
                    }
                }
            });
        }
        return button2;
    }

    @Override
    public JComponent getComponent() {
        return panel;
    }

    @Override
    protected void reInitialize() {
        getButton1().setText(getOption(OPTION_1));
        getButton2().setText(getOption(OPTION_2));
    }

    @Override
    protected void lockUI(boolean lock) {
        getButton1().setEnabled(!lock);
        getButton2().setEnabled(!lock);
    }

    @Override
    public void setValue(String value) {

//        if ( value instanceof String ) {

        if (getButton1().getText().equalsIgnoreCase((String) value)) {
            getButton1().setSelected(true);
        } else if (getButton2().getText().equalsIgnoreCase((String) value)) {
            getButton2().setSelected(true);
        } else {
            myLogger.error("Not a valid value for RadioButton, must be either '{}' or '{}': {}", new Object[]{getButton1().getText(), getButton2().getText(), value});
        }
//        } else {
//            myLogger.error("Wrong type, need String to change value of RadioButton: {}", value);
//        }
    }

    @Override
    public Set<String> getOptionKeys() {
        return OPTION_KEYS;
    }


}
