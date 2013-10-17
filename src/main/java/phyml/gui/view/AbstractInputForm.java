package phyml.gui.view;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import org.apache.commons.lang.StringUtils;
import phyml.gui.model.AbstractProperty;
import phyml.gui.model.Node;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.util.Collection;
import java.util.List;

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

    private static JPanel assembleProperty(AbstractProperty p) {
        return assembleProperty(p, Node.DEFAULT_LABEL_WIDTH);
    }

    private static JPanel assembleProperty(AbstractProperty p, int labelWidth) {

        String labelText = p.getLabel();

        JPanel panel = new JPanel();

        if (StringUtils.isEmpty(labelText)) {

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
            panel.setLayout(layout);

            JLabel label = new JLabel(p.getLabel());
            label.setToolTipText(p.getLabel());
            label.setHorizontalAlignment(SwingConstants.LEFT);
            panel.add(p.getComponent(), "2, 2");


        } else {

            ColumnSpec[] cs = new ColumnSpec[]{
                    FormSpecs.RELATED_GAP_COLSPEC,
                    ColumnSpec.decode("left:" + labelWidth + "px"),
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

            JLabel label = new JLabel(p.getLabel());
            label.setHorizontalAlignment(SwingConstants.LEFT);
            panel.add(label, "2, 2");
            panel.add(p.getComponent(), "4, 2");

        }

        return panel;
    }

    protected static JPanel assembleNode(Node node) {

        Multimap<String, AbstractProperty> groups = getPropertyGroups(node.getProperties().values());
        JPanel panel = new JPanel();

        // dont split into groups if only one group exists
        if (groups.keySet().size() == 0) {
            throw new RuntimeException("No groups/properties, can't assemble node.");
        } else {

            // create the panel that contains all properties using BoxLayout
            panel.setLayout(new BoxLayout(panel, node.getLayoutGroups()));

            for (String group : groups.keySet()) {

                JPanel panel_tmp = assembleGroup(group, groups.get(group), node.getLayoutAlignment(), node.getLabelWidth(), node.getLayoutWeights());

                panel.add(panel_tmp);

            }

        }

        return panel;
    }

    protected static JPanel assembleGroup(String group, Collection<AbstractProperty> properties, int layoutAlignment, int labelWidth, double[] layoutWeigths) {

        JPanel panel_tmp = new JPanel();
        if (!AbstractProperty.DEFAULT_GROUP_NAME.equals(group) && !group.startsWith("_")) {
            panel_tmp.setBorder(new TitledBorder(group));
        }

        if (layoutAlignment == BoxLayout.X_AXIS) {
            List<ColumnSpec> cs = Lists.newLinkedList();
            int i = 0;
            int i_max = 0;
            for (AbstractProperty prop : properties) {

                double layoutWeight = -1;
                if (layoutWeigths.length - 1 >= i) {
                    layoutWeight = layoutWeigths[i];
                }
                i = i + 1;
                if (i > i_max) {
                    i_max = i;
                }
                String layoutHint = "grow";
                if (layoutWeight > 0) {
                    layoutHint = "grow(" + layoutWeight + ")";
                }

                cs.add(FormSpecs.RELATED_GAP_COLSPEC);
                cs.add(ColumnSpec.decode("0:" + layoutHint));

            }
            cs.add(FormSpecs.RELATED_GAP_COLSPEC);

            RowSpec[] rs = new RowSpec[]{
                    FormSpecs.RELATED_GAP_ROWSPEC,
                    RowSpec.decode("pref"),
                    FormSpecs.RELATED_GAP_ROWSPEC
            };

            FormLayout layout = new FormLayout(cs.toArray(new ColumnSpec[]{}), rs);
            panel_tmp.setLayout(layout);

            int j = 2;
            for (AbstractProperty prop : properties) {
                JPanel temp = assembleProperty(prop, labelWidth);
                panel_tmp.add(temp, j + ", 2");
                j = j + 2;
            }
        } else {

            panel_tmp.setLayout(new BoxLayout(panel_tmp, BoxLayout.Y_AXIS));

            for (AbstractProperty prop : properties) {
                JPanel temp = assembleProperty(prop, labelWidth);
                panel_tmp.add(temp);
            }

        }

        return panel_tmp;
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
