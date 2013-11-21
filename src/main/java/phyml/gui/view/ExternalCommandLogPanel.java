package phyml.gui.view;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import grisu.jcommons.processes.ExternalCommand;
import grisu.jcommons.utils.swing.LogPanel;
import org.apache.commons.lang.StringUtils;
import phyml.gui.control.NodeController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Project: grisu
 * <p/>
 * Written by: Markus Binsteiner
 * Date: 21/11/13
 * Time: 11:39 AM
 */
public class ExternalCommandLogPanel extends JPanel implements PropertyChangeListener {

    private LogPanel logPanel;
    private JButton cancelButton;

    private final ExternalCommand command;

    public ExternalCommandLogPanel(ExternalCommand ec) {
        super();
        this.command = ec;
        this.command.addPropertyChangeListener(this);

        ColumnSpec[] cs = new ColumnSpec[]{
                FormSpecs.RELATED_GAP_COLSPEC,
                ColumnSpec.decode("pref:grow"),
                FormSpecs.RELATED_GAP_COLSPEC
        };
        RowSpec[] rs = new RowSpec[]{
                FormSpecs.RELATED_GAP_ROWSPEC,
                RowSpec.decode("fill:pref:grow"),
                FormSpecs.RELATED_GAP_ROWSPEC,
                RowSpec.decode("pref"),
                FormSpecs.RELATED_GAP_ROWSPEC
        };
        FormLayout formLayout1 = new FormLayout(cs, rs);
        setLayout(formLayout1);
        add(getLogPanel(), "2, 2");

        JPanel buttonPanel = new JPanel();
        ColumnSpec[] cs2 = new ColumnSpec[]{
                FormSpecs.RELATED_GAP_COLSPEC,
                ColumnSpec.decode("right:pref:grow"),
                FormSpecs.RELATED_GAP_COLSPEC
        };
        RowSpec[] rs2 = new RowSpec[]{
                FormSpecs.RELATED_GAP_ROWSPEC,
                RowSpec.decode("pref"),
                FormSpecs.RELATED_GAP_ROWSPEC,
        };
        FormLayout formLayout2 = new FormLayout(cs2, rs2);
        buttonPanel.setLayout(formLayout2);
        buttonPanel.add(getCancelButton(), "2, 2");
        add(buttonPanel, "2, 4");

        getLogPanel().addMessage("Executing:\n");
        getLogPanel().addMessage(StringUtils.join(ec.getCommand(), " "));
    }

    private LogPanel getLogPanel() {
        if (logPanel == null) {
            logPanel = new LogPanel();
        }
        return logPanel;
    }

    private JButton getCancelButton() {
        if (cancelButton == null) {
            cancelButton = new JButton("Cancel");
            cancelButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if ( "Ok".equals(cancelButton.getText())) {
                        NodeController.eventBus.post(ExternalCommandLogPanel.this);
                    } else {
                        command.cancel();
                    }
                }
            });
        }
        return cancelButton;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getNewValue() == null) {
            return;
        }
        if ("lastMessage".equals(evt.getPropertyName())) {
            getLogPanel().addMessage((String) evt.getNewValue());
        } else if ("lastErrorMessage".equals(evt.getPropertyName())) {
            getLogPanel().addWarningMessage((String) evt.getNewValue());
        }
        if ("finished".equals(evt.getPropertyName())) {
            if ((Boolean) evt.getNewValue()) {
                SwingUtilities.invokeLater(new Thread() {
                    public void run() {
                        getCancelButton().setText("Ok");
                    }
                });

                }
            }
        }
}
