package phyml.gui.view;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimap;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import phyml.gui.model.AbstractProperty;
import phyml.gui.model.Node;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.util.Collection;

/**
 * Project: grisu
 * <p/>
 * Written by: Markus Binsteiner
 * Date: 10/10/13
 * Time: 9:14 AM
 */
public abstract class AbstractInputForm extends JPanel implements InputFormPanel {

    public AbstractInputForm() {
        super();
    }

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
        RowSpec[] rs = new RowSpec[]{
                FormSpecs.RELATED_GAP_ROWSPEC,
                RowSpec.decode("pref"),
                FormSpecs.RELATED_GAP_ROWSPEC
        };
//
        FormLayout layout = new FormLayout(cs, rs);
        panel.setLayout(layout);

        panel.add(new JLabel(p.getLabel()), "2, 2");
        panel.add(p.getComponent(), "4, 2");
//        panel.add(p.getComponent());

        return panel;
    }

    protected static JPanel assembleNodeForm(Node node) {

        Multimap<String, AbstractProperty> groups = getPropertyGroups(node.getProperties().values());
        JPanel panel = new JPanel();

        // dont split into groups if only one group exists
        if (groups.keySet().size() == 0) {
            throw new RuntimeException("No groups/properties, can't assemble node.");
//        } else if (groups.keySet().size() == 1) {
//
//            // create the panel that contains all properties using BoxLayout
//            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
//            JSeparator sep = null;
//
//            for (String name : node.getProperties().keySet()) {
//                JPanel temp = assemblePropertyForm(node.getProperties().get(name));
//                panel.add(temp);
//                sep = new JSeparator();
//                panel.add(sep);
//            }
//            // remove last separator
//            panel.remove(sep);
        } else {

            // create the panel that contains all properties using BoxLayout
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

            for (String group : groups.keySet()) {

                JPanel panel_tmp = new JPanel();
                if ( ! AbstractProperty.DEFAULT_GROUP_NAME.equals(group) ) {
                    panel_tmp.setBorder(new TitledBorder(group));
                }
                panel_tmp.setLayout(new BoxLayout(panel_tmp, BoxLayout.Y_AXIS));

                for (AbstractProperty prop : groups.get(group)) {
                    JPanel temp = assemblePropertyForm(prop);
                    panel_tmp.add(temp);
                }

                panel.add(panel_tmp);

            }

        }

        return panel;
    }

    protected static Multimap<String, AbstractProperty> getPropertyGroups(Collection<AbstractProperty> properties) {

        ListMultimap<String, AbstractProperty> groups = LinkedListMultimap.create();

        for (AbstractProperty prop : properties) {
            groups.put(prop.getGroup(), prop);
        }

        return groups;
    }

    @Override
    public JPanel getPanel() {
        return this;
    }


}
