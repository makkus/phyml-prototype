package phyml.gui.view;

import phyml.gui.model.AbstractNode;
import phyml.gui.model.AbstractProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Project: grisu
 * <p/>
 * Written by: Markus Binsteiner
 * Date: 4/10/13
 * Time: 3:25 PM
 */
public class TextFieldProperty<T> extends AbstractProperty<T> {

    private static final Logger myLogger = LoggerFactory.getLogger(TextFieldProperty.class);

    private JTextField textField;

    private String currentText = "";

    public TextFieldProperty(AbstractNode parent, String label){
        super(parent, label);
    }

    @Override
    public JComponent getComponent() {
        return getTextField();
    }

    @Override
    protected void lockUI(boolean lock) {
        textField.setEnabled(!lock);
    }

    public JTextField getTextField() {
        if ( textField == null ) {
            textField = new JTextField();
            textField.setColumns(10);
            textField.addKeyListener(new KeyAdapter() {


                @Override
                public void keyReleased(KeyEvent e) {
                    String old = currentText;
                    currentText = textField.getText();
                    valueChanged((T)old, (T)currentText);
                }

            });
        }
        return textField;
    }

    @Override
    public void setValue(T value) {
        if ( value instanceof String ) {
            getTextField().setText((String)value);
            String old = currentText;
            currentText = (String)value;
            valueChanged((T)old, value);
        } else {
            myLogger.error("Need value of type String for TextField: {}", value);
        }
    }

    public Object getUserInput() {
        return textField.getText();
    }



}
