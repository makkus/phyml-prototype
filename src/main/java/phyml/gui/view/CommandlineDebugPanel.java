package phyml.gui.view;

import com.google.common.eventbus.Subscribe;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import org.apache.commons.lang.StringUtils;
import phyml.gui.control.NodeController;

import javax.swing.*;
import java.util.List;

/**
 * Project: grisu
 * <p/>
 * Written by: Markus Binsteiner
 * Date: 15/10/13
 * Time: 3:33 PM
 */
public class CommandlineDebugPanel extends JPanel {

    private JTextField textField;

    public CommandlineDebugPanel() {
        ColumnSpec[] cs = new ColumnSpec[]{
                FormSpecs.RELATED_GAP_COLSPEC,
                ColumnSpec.decode("pref:grow"),
                FormSpecs.RELATED_GAP_COLSPEC
        };
        RowSpec[] rs = new RowSpec[]{
                FormSpecs.RELATED_GAP_ROWSPEC,
                RowSpec.decode("pref"),
                FormSpecs.RELATED_GAP_ROWSPEC
        };
//
        FormLayout layout = new FormLayout(cs, rs);
        setLayout(layout);

        add(getTextField(), "2, 2");

        NodeController.eventBus.register(this);
    }

    private JTextField getTextField() {
        if ( textField == null ) {
            textField = new JTextField();
            textField.setEditable(false);
        }
        return textField;
    }

    @Subscribe
    public void setCommandline(List<String> cmd) {
        String cmdline = StringUtils.join(cmd, " ");
        getTextField().setText(cmdline);
    }
}
