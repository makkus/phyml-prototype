package phyml.gui.view;

import phyml.gui.model.AbstractNode;
import phyml.gui.model.AbstractProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Project: grisu
 * <p/>
 * Written by: Markus Binsteiner
 * Date: 4/10/13
 * Time: 5:20 PM
 */
public class RadioButtonProperty<T> extends AbstractProperty<T> {

    private static final Logger myLogger = LoggerFactory.getLogger(RadioButtonProperty.class);

    private JRadioButton button1;
    private JRadioButton button2;

    private final String button1Name;
    private final String button2Name;


    private ButtonGroup group;
    private final JPanel panel = new JPanel(new GridLayout(1,0));

    public RadioButtonProperty(AbstractNode parent, String label, String button1Name, String button2Name) {
        super(parent, label);
        this.button1Name = button1Name;
        this.button2Name = button2Name;
        getGroup();
        panel.add(getButton1());
        panel.add(getButton2());

    }

    private ButtonGroup getGroup() {
        if ( group == null ) {
            group = new ButtonGroup();
            group.add(getButton1());
            group.add(getButton2());
        }
        return group;
    }

    private JRadioButton getButton1() {
        if ( button1 == null ) {
            button1 = new JRadioButton(button1Name);
            button1.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if ( ItemEvent.SELECTED == e.getStateChange() ) {
                        valueChanged((T)button2Name, (T)button1Name);
                    }
                }
            });
        }
        return button1;
    }

        private JRadioButton getButton2() {
            if ( button2 == null ) {
                button2 = new JRadioButton(button2Name);
                button2.addItemListener(new ItemListener() {
                    @Override
                    public void itemStateChanged(ItemEvent e) {
                        if ( ItemEvent.SELECTED == e.getStateChange() ) {
                            valueChanged((T)button1Name, (T)button2Name);
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
    protected void lockUI(boolean lock) {
        getButton1().setEnabled(!lock);
        getButton2().setEnabled(!lock);
    }

    @Override
    public void setValue(T value) {

        if ( value instanceof String ) {
            if ( button1Name.equalsIgnoreCase((String)value) ) {
                getButton1().setSelected(true);
            } else if ( button2Name.equalsIgnoreCase((String)value)) {
                getButton2().setSelected(true);
            } else {
                myLogger.error("Not a valid value for RadioButton, must be either '{}' or '{}': {}", new Object[]{button1Name, button2Name, value});
            }
        } else {
            myLogger.error("Wrong type, need String to change value of RadioButton: {}", value);
        }
    }


}
