package phyml.gui.view;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.*;
import phyml.gui.model.AbstractProperty;
import phyml.gui.model.Node;

import javax.swing.*;

/**
 * Project: grisu
 * <p/>
 * Written by: Markus Binsteiner
 * Date: 10/10/13
 * Time: 9:14 AM
 */
public abstract class AbstractInputForm extends JPanel implements InputFormPanel {

    private static JPanel assemblePropertyForm(AbstractProperty p) {

        JPanel panel = new JPanel();
//        panel.setMinimumSize(new Dimension(400, 32));
//        panel.setMaximumSize(new Dimension(400, 32));
//        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
//        panel.setLayout(new HorizontalLayout());
//        HorizontalLayout layout = new HorizontalLayout();
//        layout.setGap(10);

        ColumnSpec[] cs = new ColumnSpec[]{
                FormSpecs.RELATED_GAP_COLSPEC,
                //ColumnSpec.decode("right:pref"),
                ColumnSpec.decode("right:100px"),
                FormSpecs.RELATED_GAP_COLSPEC,
                ColumnSpec.decode("pref:grow"),
                FormSpecs.RELATED_GAP_COLSPEC
        };
        RowSpec[] rs = new RowSpec[] {
                FormSpecs.RELATED_GAP_ROWSPEC,
                RowSpec.decode("pref"),
                FormSpecs.RELATED_GAP_ROWSPEC
        };
//
        FormLayout layout = new FormLayout(cs,rs);

        PanelBuilder builder = new PanelBuilder(layout);

        panel.setLayout(layout);

        CellConstraints cc = new CellConstraints();

//        JLabel label = new JLabel(p.getLabel());
//        panel.add(label);

        panel.add(new JLabel(p.getLabel()), "2, 2, fill, fill");
        panel.add(p.getComponent(), "4, 2");
//        panel.add(p.getComponent());

        return panel;
    }


    protected static JPanel assembleNodeForm(Node node) {
        JPanel panel = new JPanel();
//        panel.setLayout(new VerticalLayout());
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JSeparator sep = null;
        for (String name : node.getProperties().keySet()) {
            JPanel temp = assemblePropertyForm(node.getProperties().get(name));
            panel.add(temp);
            sep = new JSeparator();
            panel.add(sep);
        }
        // remove last separator
        panel.remove(sep);

        return panel;
    }

    public AbstractInputForm() {
        super();
    }

    @Override
    public JPanel getPanel() {
        return this;
    }


}
