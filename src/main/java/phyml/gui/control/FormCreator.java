package phyml.gui.control;

import com.google.common.collect.Lists;
import phyml.gui.model.AbstractNode;
import phyml.gui.model.AbstractProperty;
import phyml.gui.model.ExampleNode;
import phyml.gui.model.ExampleNode2;
import phyml.gui.view.ComboBoxProperty;
import phyml.gui.view.InputFormPanel;
import phyml.gui.view.RadioButtonProperty;
import phyml.gui.view.TextFieldProperty;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;

/**
 * This class assembles the Form according to the node list.
 * <p/>
 * Later on we can make it easier to configure (for example with a text file), but for now configuration
 * is done programatically.
 * <p/>
 * Written by: Markus Binsteiner
 * Date: 4/10/13
 * Time: 3:38 PM
 */
public class FormCreator {

    public static void main(String[] args) {

        // create node1
        AbstractNode node1 = new ExampleNode("node1");
        // adding property for node1 and setting default value
        AbstractProperty<String> prop1 = new TextFieldProperty<String>(node1, "label1");
        prop1.setValue("exampleDefaultValue");

        //creating node2
        AbstractNode node2 = new ExampleNode2("node2");
        // creating 2 properties and setting default value for node 2
        AbstractProperty<String> prop21 = new RadioButtonProperty<String>(node2, "label21", "button1", "button2");
        prop21.setValue("button2");
        AbstractProperty<String> prop22 = new RadioButtonProperty<String>(node2, "label22", "button3", "button4");
        prop22.setValue("button3");

        // creating node 3
        AbstractNode node3 = new ExampleNode("node3");
        // creating property for node 3
        String[] choices = new String[]{"choice1", "choice2", "choice3"};
        AbstractProperty<String> prop3 = new ComboBoxProperty<String>(node3, "label3", choices);

        // setting connections
        node2.addConnection(node3);
        node3.addConnection(node2);

        // adding all nodes to list for creation
        List<AbstractNode> nodes = Lists.newArrayList();
        nodes.add(node1);
        nodes.add(node2);
        nodes.add(node3);


        final FormCreator fc = new FormCreator(nodes);

        try {
            SwingUtilities.invokeAndWait(new Thread() {
                public void run() {
                    JFrame frame = new JFrame("InputForm");
                    frame.setSize(600, 400);
                    frame.setContentPane(fc.getForm());
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //        frame.pack();
                    frame.setVisible(true);
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InvocationTargetException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


    }

    private final Collection<AbstractNode> nodes;

    private InputFormPanel inputFormPanel;

    public FormCreator(Collection<AbstractNode> nodes) {
        this.nodes = nodes;
    }

    public InputFormPanel getForm() {

        if ( inputFormPanel == null ) {
            inputFormPanel = new InputFormPanel();
            for ( AbstractNode n : nodes ) {
                inputFormPanel.addNode(n);
            }
        }

        return inputFormPanel;
    }

    private JPanel assembleFormField(AbstractNode node) {
        return null;
    }

}
