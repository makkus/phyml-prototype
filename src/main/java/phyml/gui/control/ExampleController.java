package phyml.gui.control;

import com.google.common.collect.Lists;
import phyml.gui.model.AbstractProperty;
import phyml.gui.model.Node;
import phyml.gui.view.ComboBoxProperty;
import phyml.gui.view.FilePathProperty;
import phyml.gui.view.RadioButtonProperty;
import phyml.gui.view.TextFieldProperty;

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
    public static final String NODE_RADIOBUTTON = "Node Radiobutton";
    public static final String NODE_COMBOBOX = "Node Combobox";
    public static final String NODE_FILEPATH = "Node Filepath";
    public static final String NODE_SPINNER = "Node spinner";

    public static final String PROPERTY_TEXTFIELD = "Textfield";
    public static final String PROPERTY_RADIOBUTTON_1 = "Radiobutton 1";
    public static final String PROPERTY_RADIOBUTTON_2 = "RadioButton 2";
    public static final String PROPERTY_COMBOBOX = "Combobox";
    public static final String PROPERTY_FILEPATH = "Filepath";
    public static final String PROPERTY_SPINNER = "Spinner";

    // create node_textfield
    private final Node node_textfield = new Node(NODE_TEXTFIELD);
    // adding property for node_textfield and setting default value
    private final AbstractProperty prop_textfield = new TextFieldProperty(node_textfield, PROPERTY_TEXTFIELD);
    //creating node_radiobutton
    private final Node node_radiobutton = new Node(NODE_RADIOBUTTON);
    // creating 2 properties and setting default value for node 2
    private final AbstractProperty prop_radiobutton_1 = new RadioButtonProperty(node_radiobutton, PROPERTY_RADIOBUTTON_1);
    private final AbstractProperty prop_radiobutton_2 = new RadioButtonProperty(node_radiobutton, PROPERTY_RADIOBUTTON_2);
    // creating node 3
    private final Node node_combobox = new Node(NODE_COMBOBOX);
    // creating property for node 3
    private final String choices = "Dayhoff;LG;WAG;JTT";
    private final AbstractProperty prop_combobox = new ComboBoxProperty(node_combobox, PROPERTY_COMBOBOX);
    // creating node filepath
    private final Node node_filepath = new Node(NODE_FILEPATH);
    private final AbstractProperty prop_filepath = new FilePathProperty(node_filepath, PROPERTY_FILEPATH);

    private final Node node_spinner = new Node(NODE_SPINNER);
    private final AbstractProperty prop_spinner = new SpinnerProperty(node_spinner, PROPERTY_SPINNER);

    @Override
    protected List<Node> createNodes() {

        // adding all nodes to list for creation
        List<Node> nodes = Lists.newArrayList();
        nodes.add(node_textfield);
        nodes.add(node_radiobutton);
        nodes.add(node_combobox);
        nodes.add(node_filepath);
        nodes.add(node_spinner);

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
