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

        // alternatively, you can use different layouts
        //FormCreator fc = new FormCreator(exampleController, FormCreator.SIMPLE_LAYOUT);
        //FormCreator fc = new FormCreator(exampleController, FormCreator.TABBED_LAYOUT);

        // display the gui
        fc.display();

    }



    public static final String NODE_1 = "node1";
    public static final String NODE_2 = "node2";
    public static final String NODE_3 = "node3";

    public static final String PROPERTY_1 = "label1";
    public static final String PROPERTY_2 = "label2";
    public static final String PROPERTY_3 = "label3";
    public static final String PROPERTY_4 = "label4";
    public static final String PROPERTY_5 = "label5";

    // create node1
    private final Node node1 = new Node(NODE_1);
    // adding property for node1 and setting default value
    private final AbstractProperty prop1 = new TextFieldProperty(node1, PROPERTY_1);
    //creating node2
    private final Node node2 = new Node(NODE_2);
    // creating 2 properties and setting default value for node 2
    private final AbstractProperty prop2 = new RadioButtonProperty(node2, PROPERTY_2);
    private final AbstractProperty prop3 = new RadioButtonProperty(node2, PROPERTY_3);
    // creating node 3
    private final Node node3 = new Node(NODE_3);
    // creating property for node 3
    private final String choices = "Dayhoff;LG;WAG;JTT";
    private final AbstractProperty prop4 = new ComboBoxProperty(node3, PROPERTY_4);
    private final AbstractProperty prop5 = new FilePathProperty(node3, PROPERTY_5);

    @Override
    protected List<Node> createNodes() {

        // adding all nodes to list for creation
        List<Node> nodes = Lists.newArrayList();
        nodes.add(node1);
        nodes.add(node2);
        nodes.add(node3);

        return nodes;
    }

    @Override
    public void setInitialValues() {

        prop1.selectValue("exampleDefaultValue");


        prop2.setOption(RadioButtonProperty.OPTION_1, "button1");
        prop2.setOption(RadioButtonProperty.OPTION_2, "button2");
        prop2.selectValue("button2");

        prop3.setOption(RadioButtonProperty.OPTION_1, "button3");
        prop3.setOption(RadioButtonProperty.OPTION_2, "button4");
        prop3.selectValue("button3");

        prop4.setOption(ComboBoxProperty.OPTION_CHOICES, choices);

    }

    @Override
    protected void nodeChanged(Node node, AbstractProperty property, PropertyChangeEvent event) {

        String labelThatChanged = property.getLabel();

        // disable property 1 if 'JTT' is selected in property 4
        if (PROPERTY_4.equals(labelThatChanged)) {

            AbstractProperty prop = getProperty(PROPERTY_1);

            if ("JTT".equals(event.getNewValue())) {
                prop.setActive(false);
            } else {
                prop.setActive(true);
            }
        } else {
            myLogger.debug("Doing nothing.");
        }


    }
}
