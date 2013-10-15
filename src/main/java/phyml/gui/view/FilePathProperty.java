package phyml.gui.view;

import com.google.common.collect.ImmutableSet;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import phyml.gui.model.AbstractProperty;
import phyml.gui.model.Node;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Set;

/**
 * Project: grisu
 * <p/>
 * Written by: Markus Binsteiner
 * Date: 4/10/13
 * Time: 3:25 PM
 */
public class FilePathProperty extends AbstractProperty {

    public static final String OPTION_TEXT = "TEXT";
    public static final Set<String> OPTION_KEYS = ImmutableSet.<String>of(OPTION_TEXT);
    private static final Logger myLogger = LoggerFactory.getLogger(FilePathProperty.class);
    private JTextField textField;
    private JButton browseButton;
    private String currentPath = "";

    private JPanel panel;

    public FilePathProperty(Node parent, String id) {
        this(parent, id, null);
    }

    public FilePathProperty(Node parent, String id, String group) {
        this(parent, id, id, group);
    }

    public FilePathProperty(Node parent, String id, String label, String group) {
        super(parent, label, group);

        panel = new JPanel();

        ColumnSpec[] cs = new ColumnSpec[]{
                FormSpecs.RELATED_GAP_COLSPEC,
                //ColumnSpec.decode("right:pref"),
                ColumnSpec.decode("pref:grow"),
                FormSpecs.RELATED_GAP_COLSPEC,
                ColumnSpec.decode("pref"),
                FormSpecs.RELATED_GAP_COLSPEC
        };
        RowSpec[] rs = new RowSpec[] {
                RowSpec.decode("pref")
        };
//
        FormLayout layout = new FormLayout(cs,rs);
        panel.setLayout(layout);

        panel.add(getTextField(), "2, 1");
        panel.add(getBrowseButton(), "4, 1");
    }

    @Override
    public JComponent getComponent() {
        return panel;
    }

    @Override
    protected void reInitialize() {
        String text = getOption(OPTION_TEXT);

        getTextField().setText(text);
    }

    @Override
    protected void lockUI(boolean lock) {
        getTextField().setEnabled(!lock);
        getBrowseButton().setEnabled(!lock);
    }

    public JTextField getTextField() {
        if (textField == null) {
            textField = new JTextField();
            textField.setColumns(10);
            textField.addKeyListener(new KeyAdapter() {


                @Override
                public void keyReleased(KeyEvent e) {
                    String old = currentPath;
                    currentPath = textField.getText();
                    valueChanged(old, currentPath);
                }

            });
        }
        return textField;
    }

    public JButton getBrowseButton() {

        if ( browseButton == null ) {
            browseButton = new JButton("Browse");
            browseButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    JFileChooser fc = new JFileChooser();
                    fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
                    int returnVal = fc.showOpenDialog(getComponent());
                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        String old = getTextField().getText();
                        currentPath = fc.getSelectedFile().getAbsolutePath();
                        getTextField().setText(currentPath);
                        valueChanged(old, currentPath);
                    }
                }
            });
        }
        return browseButton;
    }

    @Override
    public void setValue(String value) {
//        if ( value instanceof String ) {
        getTextField().setText((String) value);
        String old = currentPath;
        currentPath = (String) value;
        valueChanged(old, value);
//        } else {
//            myLogger.error("Need value of type String for TextField: {}", value);
//        }
    }

    @Override
    public Set<String> getOptionKeys() {
        return OPTION_KEYS;
    }

    public Object getUserInput() {
        return textField.getText();
    }


}
