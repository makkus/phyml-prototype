package phyml.gui.control;

import com.google.common.collect.Lists;
import phyml.gui.model.AbstractProperty;
import phyml.gui.model.Node;
import phyml.gui.view.ComboBoxProperty;
import phyml.gui.view.FilePathProperty;
import phyml.gui.view.RadioButtonProperty;
import phyml.gui.view.TextFieldProperty;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.util.List;

/**
 * Example controller to demonstrate how to create and control a gui.
 *
 * <p/>
 * Written by: Markus Binsteiner
 * Date: 10/10/13
 * Time: 10:51 AM
 */
public class ExampleController extends NodeController {

    public static void main(String[] args) {

        // create a controller
        ExampleController exampleController = new ExampleController();

        // create the GUI
        FormCreator fc = new FormCreator(exampleController);

        fc.setDisplayDebug(true);

        // alternatively, you can use different layouts
        //FormCreator fc = new FormCreator(exampleController, FormCreator.SIMPLE_LAYOUT);
        //FormCreator fc = new FormCreator(exampleController, FormCreator.TABBED_LAYOUT);

        // display the gui
        fc.display();

    }

    public static final String NODE_TEXTFIELD = "Node Textfield";
    public static final String NODE_RADIOBUTTON = "Node Groups horizontal/Properties vertical";
    public static final String NODE_COMBOBOX = "Node Properties horizontal";

    public static final String NODE_GROUPS = "Node Groups vertical/Properties horizontal";

    public static final String PROPERTY_TEXTFIELD = "Textfield";
    public static final String PROPERTY_RADIOBUTTON_1 = "Radiobutton 1";
    public static final String PROPERTY_RADIOBUTTON_2 = "RadioButton 2";
    public static final String PROPERTY_COMBOBOX = "Combobox";
    public static final String PROPERTY_FILEPATH = "Filepath";
    public static final String PROPERTY_SPINNER = "Spinner";

    public static final String PROPERTY_GROUP_TEXTFIELD_1 = "Textfield 1";
    public static final String PROPERTY_GROUP_TEXTFIELD_2 = "Textfield 2";
    public static final String PROPERTY_GROUP_RADIOBUTTON_1 = "Group radio 1";
    public static final String PROPERTY_GROUP_RADIOBUTTON_2 = "Group radio 2";

    // create node_textfield
    private final Node node_textfield = new Node(NODE_TEXTFIELD);
    // adding property for node_textfield and setting default value
    private final AbstractProperty prop_textfield = new TextFieldProperty(node_textfield, PROPERTY_TEXTFIELD);
    //creating node_radiobutton
    private final Node node_radiobutton = new Node(NODE_RADIOBUTTON);

    // creating 2 properties and setting default value for node 2
    private final AbstractProperty prop_radiobutton_1 = new RadioButtonProperty(node_radiobutton, PROPERTY_RADIOBUTTON_1, "Radiobuttons");
    private final AbstractProperty prop_radiobutton_2 = new RadioButtonProperty(node_radiobutton, PROPERTY_RADIOBUTTON_2, "Radiobuttons");
    private final AbstractProperty prop_spinner = new SpinnerProperty(node_radiobutton, PROPERTY_SPINNER, "Spinner");
    // creating node 3
    private final Node node_combobox = new Node(NODE_COMBOBOX);
    // creating property for node 3
    private final String choices = "Dayhoff;LG;WAG;JTT";
    private final AbstractProperty prop_combobox = new ComboBoxProperty(node_combobox, PROPERTY_COMBOBOX);
    // creating node filepath
    private final AbstractProperty prop_filepath = new FilePathProperty(node_combobox, PROPERTY_FILEPATH);

    private final Node node_groups = new Node(NODE_GROUPS);
    private final AbstractProperty prop_group_text_1 = new TextFieldProperty(node_groups, PROPERTY_GROUP_TEXTFIELD_1,  PROPERTY_GROUP_TEXTFIELD_1, "_group1_");
    private final AbstractProperty prop_group_radio_1 = new RadioButtonProperty(node_groups, PROPERTY_GROUP_RADIOBUTTON_1, null, "_group1_");
    private final AbstractProperty prop_group_text_2 = new TextFieldProperty(node_groups, PROPERTY_GROUP_TEXTFIELD_2,  PROPERTY_GROUP_TEXTFIELD_2, "_group2_");
    private final AbstractProperty prop_group_radio_2 = new RadioButtonProperty(node_groups, PROPERTY_GROUP_RADIOBUTTON_2, null, "_group2_");

    public ExampleController() {
        // if you want to change the default label witdth, you can do that here:
        Node.DEFAULT_LABEL_WIDTH = 200;
        // otherwise, change it by calling the method on the Node object
    }

    @Override
    protected List<Node> createNodes() {

        // 2 groups
        node_radiobutton.setLayoutGroups(BoxLayout.X_AXIS);

        node_groups.setLayoutProperties(BoxLayout.X_AXIS);

        node_groups.setLayoutGroups(BoxLayout.Y_AXIS);

        // adding all nodes to list for creation
        List<Node> nodes = Lists.newArrayList();
        nodes.add(node_textfield);
        nodes.add(node_radiobutton);
        nodes.add(node_combobox);
        nodes.add(node_groups);

        return nodes;
    }

    @Override
    protected void setInitialValues() {

        prop_textfield.selectValue("exampleDefaultValue");

        prop_radiobutton_1.setOption(RadioButtonProperty.OPTION_1, "Option 1");
        prop_radiobutton_1.setOption(RadioButtonProperty.OPTION_2, "Option 2");
        prop_radiobutton_1.selectValue("Option 2");

        prop_radiobutton_2.setOption(RadioButtonProperty.OPTION_1, "Option 3");
        prop_radiobutton_2.setOption(RadioButtonProperty.OPTION_2, "Option 4");
        prop_radiobutton_2.selectValue("Option 3");

        prop_combobox.setOption(ComboBoxProperty.OPTION_CHOICES, choices);

        prop_spinner.setOption(SpinnerProperty.OPTION_MIN, "4");
        prop_spinner.setOption(SpinnerProperty.OPTION_MAX, "10");
        prop_spinner.setOption(SpinnerProperty.OPTION_STEP, "2");
        prop_spinner.selectValue("8");

        prop_group_text_1.selectValue("This is an example");
        prop_group_text_2.selectValue("This is another example");

        prop_group_radio_1.setOption(RadioButtonProperty.OPTION_1, "Yes");
        prop_group_radio_1.setOption(RadioButtonProperty.OPTION_2, "No");
        prop_group_radio_2.setOption(RadioButtonProperty.OPTION_1, "Maybe");
        prop_group_radio_2.setOption(RadioButtonProperty.OPTION_2, "Maybe not");

    }

    @Override
    protected void nodeChanged(Node node, AbstractProperty property, PropertyChangeEvent event) {

        String labelThatChanged = property.getLabel();

        // disable property 1 if 'JTT' is selected in property 4
        if (PROPERTY_COMBOBOX.equals(labelThatChanged)) {

            if ("JTT".equals(event.getNewValue())) {
                prop_textfield.setActive(false);
            } else {
                prop_textfield.setActive(true);
            }

            // the commandline changes here dont make any sense
            commandline.clear();
            commandline.addAll(Lists.newArrayList("--test " + prop_spinner.getValue()));
        } else {
            myLogger.debug("Doing nothing.");
            // the commandline changes here dont make any sense
            commandline.add("--"+event.getPropertyName()+event.getNewValue());
        }


    }
}
