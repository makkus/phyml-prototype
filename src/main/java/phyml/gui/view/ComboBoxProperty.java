package phyml.gui.view;

import phyml.gui.model.AbstractNode;
import phyml.gui.model.AbstractProperty;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Project: grisu
 * <p/>
 * Written by: Markus Binsteiner
 * Date: 4/10/13
 * Time: 5:43 PM
 */
public class ComboBoxProperty<T> extends AbstractProperty<T> {

    private final T[] choices;

    private T currentValue = null;

    private JComboBox comboBox;
    private DefaultComboBoxModel comboBoxModel = new DefaultComboBoxModel();

    public ComboBoxProperty(AbstractNode parent, String label, T[] choices) {
        super(parent, label);
        this.choices = choices;
        for ( T choice : choices ) {
            comboBoxModel.addElement(choice);
        }
    }

    @Override
    public JComponent getComponent() {
        return getComboBox();
    }

    @Override
    protected void lockUI(boolean lock) {
        getComboBox().setEnabled(!lock);
    }

    @Override
    public void setValue(T value) {
        comboBoxModel.setSelectedItem(value);
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

                    T old = currentValue;
                    currentValue = (T)comboBoxModel.getSelectedItem();

                    valueChanged(old, currentValue);
                }
            });
            currentValue = (T)comboBoxModel.getSelectedItem();
        }
        return comboBox;
    }
}
