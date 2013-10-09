package phyml.gui.view;

import com.google.common.collect.ImmutableSortedSet;
import phyml.gui.model.AbstractNode;
import phyml.gui.model.AbstractProperty;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Set;

/**
 * Property that displays several pre-set options to the user via a combobox.
 * <p/>
 * Written by: Markus Binsteiner
 * Date: 4/10/13
 * Time: 5:43 PM
 */
public class ComboBoxProperty extends AbstractProperty {

    public static final String OPTION_CHOICES = "CHOICES";
    public static final Set<String> OPTION_KEYS = ImmutableSortedSet.<String>of(OPTION_CHOICES);

    private String currentValue = null;

    private JComboBox comboBox;
    private DefaultComboBoxModel comboBoxModel = new DefaultComboBoxModel();

    public ComboBoxProperty(AbstractNode parent, String label) {
        super(parent, label);
    }

    @Override
    public JComponent getComponent() {
        return getComboBox();
    }

    @Override
    protected void reInitialize() {

        comboBoxModel.removeAllElements();

        for ( String option : getOption(OPTION_CHOICES).split(";")) {
            comboBoxModel.addElement(option);
        }

    }

    @Override
    protected void lockUI(boolean lock) {
        getComboBox().setEnabled(!lock);
    }

    @Override
    public void setValue(String value) {
        comboBoxModel.setSelectedItem(value);
    }

    @Override
    public Set<String> getOptionKeys() {
        return OPTION_KEYS;
    }

    private JComboBox getComboBox() {
        if ( comboBox == null ) {
            comboBox = new JComboBox(comboBoxModel);
            comboBox.setEditable(false);
            comboBox.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if ( ItemEvent.DESELECTED == e.getStateChange() ) {
                        return;
                    }

                    String old = currentValue;
                    currentValue = (String)comboBoxModel.getSelectedItem();

                    valueChanged(old, currentValue);
                }
            });
            currentValue = (String)comboBoxModel.getSelectedItem();
        }
        return comboBox;
    }
}
