package phyml.gui.control;

import com.google.common.collect.Lists;
import phyml.gui.model.*;
import phyml.gui.view.ComboBoxProperty;
import phyml.gui.view.InputFormPanel;
import phyml.gui.view.RadioButtonProperty;
import phyml.gui.view.TextFieldProperty;

import javax.swing.*;
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

        Boolean state = false;

        // create node1
        AbstractNode node1 = new ExampleNode("node1");
        // adding property for node1 and setting default value
        AbstractProperty prop1 = new TextFieldProperty(node1, "label1");
        prop1.selectValue("exampleDefaultValue");

        //creating node2
        AbstractNode node2 = new ExampleNode2("node2");
        // creating 2 properties and setting default value for node 2
        AbstractProperty prop21 = new RadioButtonProperty(node2, "label21");
        prop21.setOption(RadioButtonProperty.OPTION_1, "button1");
        prop21.setOption(RadioButtonProperty.OPTION_2, "button2");
        prop21.selectValue("button2");
        AbstractProperty prop22 = new RadioButtonProperty(node2, "label22");
        prop22.setOption(RadioButtonProperty.OPTION_1, "button3");
        prop22.setOption(RadioButtonProperty.OPTION_2, "button4");
        prop22.selectValue("button3");

        // creating node 3
        AbstractNode node3 = new ExampleNode3("node3");
        // creating property for node 3
        String choices = "Dayhoff;LG;WAG;JTT";
        AbstractProperty prop3 = new ComboBoxProperty(node3, "label3");
        prop3.setOption(ComboBoxProperty.OPTION_CHOICES, choices);




        //Data type
        AbstractNode nodeDataType = new DataType("Data type node");
        AbstractProperty propDataType = new RadioButtonProperty(nodeDataType, "Data type");
        propDataType.setOption(RadioButtonProperty.OPTION_1, "Amino-acids");
        propDataType.setOption(RadioButtonProperty.OPTION_2, "Nucleotides");
        propDataType.selectValue("Nucleotides");

        // Substitution model
        AbstractNode nodeSubstModels = new SubstModels("Substitution models node");
        String choicesSubstModels = "Dayhoff;LG;WAG;JTT";
        AbstractProperty propSubstModels = new ComboBoxProperty(nodeSubstModels, "Substitution models");
        propSubstModels.setOption(ComboBoxProperty.OPTION_CHOICES, choicesSubstModels);
        propSubstModels.selectValue("LG");
        
        // Transition/transversion ratio
        AbstractNode nodeTsTv = new TsTv("TsTv node");
        AbstractProperty propTsTvVal = new TextFieldProperty(nodeTsTv, "Ts/tv value");
        propTsTvVal.selectValue("4.0");
        state = false;
        propTsTvVal.setActive(state);
        AbstractProperty propTsTvYesNo = new RadioButtonProperty(nodeTsTv, "Estimated/fixed");
        propTsTvYesNo.setOption(RadioButtonProperty.OPTION_1, "estimated");
        propTsTvYesNo.setOption(RadioButtonProperty.OPTION_2, "fixed");
        propTsTvYesNo.selectValue("estimated");


        // RAS model
        AbstractNode nodeRASmodel = new TsTv("RAS model node");
        AbstractProperty propRASnclasses = new TextFieldProperty(nodeRASmodel, "Number of rate classes");
        propRASnclasses.selectValue("4");
        AbstractProperty propRASmodel = new RadioButtonProperty(nodeRASmodel, "Rate variation model");
        propRASmodel.setOption(RadioButtonProperty.OPTION_1, "Discrete Gamma");
        propRASmodel.setOption(RadioButtonProperty.OPTION_2, "Free Rates");
        propRASmodel.selectValue("Free Rates");

        // Discrete Gamma model
        AbstractNode nodeGamma = new Gamma("Discrete Gamma node");
        AbstractProperty propGamma = new TextFieldProperty(nodeGamma, "Gamma shape parameter");
        propGamma.selectValue("1.0");
        state = false;
        propGamma.setActive(state);
        AbstractProperty propGammaYesNo = new RadioButtonProperty(nodeGamma, "Estimated/fixed");
        propGammaYesNo.setOption(RadioButtonProperty.OPTION_1, "estimated");
        propGammaYesNo.setOption(RadioButtonProperty.OPTION_2, "fixed");
        propGammaYesNo.selectValue("estimated");
        state = false;
        propGammaYesNo.setActive(state);








        // setting connections
        // node1 will react when node3 changes
        node3.addConnection(node1);
        // node 3 will react when node2 changes
        node2.addConnection(node3);
        nodeDataType.addConnection(nodeSubstModels);
        nodeDataType.addConnection(nodeTsTv);
        nodeTsTv.addConnection(nodeTsTv);
        nodeSubstModels.addConnection(nodeTsTv);
        nodeRASmodel.addConnection(nodeGamma);
        nodeGamma.addConnection(nodeGamma);



        // adding all nodes to list for creation
        List<AbstractNode> nodes = Lists.newArrayList();
        nodes.add(node1);
        nodes.add(node2);
        nodes.add(node3);
        nodes.add(nodeDataType);
        nodes.add(nodeSubstModels);
        nodes.add(nodeTsTv);
        nodes.add(nodeRASmodel);
        nodes.add(nodeGamma);

        final FormCreator fc = new FormCreator(nodes);

    }

    private final Collection<AbstractNode> nodes;

    private InputFormPanel inputFormPanel;

    public FormCreator(Collection<AbstractNode> nodes) {
        this.nodes = nodes;
        try {
            SwingUtilities.invokeAndWait(new Thread() {
                public void run() {
                    JFrame frame = new JFrame("InputForm");
                    frame.setSize(600, 400);
                    frame.setContentPane(getForm());
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    //        frame.pack();
                    frame.setVisible(true);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


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
