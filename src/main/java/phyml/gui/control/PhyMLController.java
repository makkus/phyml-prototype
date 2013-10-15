package phyml.gui.control;

import com.google.common.collect.Lists;
import phyml.gui.model.*;
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
public class PhyMLController extends NodeController {

    public static void main(String[] args) {

        // create a controller
        PhyMLController phymlController = new PhyMLController();

        // create the GUI
        FormCreator fc = new FormCreator(phymlController);

        // alternatively, you can use different layouts
        // FormCreator fc = new FormCreator(phymlController, FormCreator.SIMPLE_LAYOUT);
        // FormCreator fc = new FormCreator(phymlController, FormCreator.TABBED_LAYOUT);

        // display the gui
        fc.display();

    }

    private final Node nodeAlignment = new Node("Alignment file node");
    private final AbstractProperty propAlignment = new FilePathProperty(nodeAlignment, "Alignment file");
    private final AbstractProperty propAlignmentYesNo = new RadioButtonProperty(nodeAlignment, "Source");
    private final Node nodeDataType = new Node("Data type node");
    private final AbstractProperty propDataType = new RadioButtonProperty(nodeDataType, "Data type");
    private final Node nodeSubstModels = new Node("Substitution models node");
    private final String choicesSubstModels = "Dayhoff;LG;WAG;JTT";
    private final AbstractProperty propSubstModels = new ComboBoxProperty(nodeSubstModels, "Substitution models");
    private final Node nodeTsTv = new Node("TsTv node");
    private final AbstractProperty propTsTvVal = new TextFieldProperty(nodeTsTv, "Ts/tv value");
    private final AbstractProperty propTsTvYesNo = new RadioButtonProperty(nodeTsTv, "Estimated/fixed");
    private final Node nodeRASmodel = new Node("RAS model node");
    private final AbstractProperty propRASnclasses = new TextFieldProperty(nodeRASmodel, "Number of rate classes");
    private final AbstractProperty propRASmodel = new RadioButtonProperty(nodeRASmodel, "Rate variation model");
    private final Node nodeGamma = new Node("Discrete Gamma node");
    private final AbstractProperty propGamma = new TextFieldProperty(nodeGamma, "Gamma shape parameter");
    private final Node nodeStartingTree = new Node("Starting tree node");
    private final AbstractProperty propStartingTree = new FilePathProperty(nodeStartingTree, "Starting tree");
    private final AbstractProperty propStartingTreeYesNo = new RadioButtonProperty(nodeStartingTree, "Source");
    private final Node nodeTreeSearch = new Node("Tree search node");
    private final AbstractProperty propTreeSearch = new ComboBoxProperty(nodeTreeSearch, "Tree search method");
    private final Node nodeRandomStarts = new Node("Random starts node");
    private final AbstractProperty propRandomStarts = new TextFieldProperty(nodeRandomStarts, "Number of random starting trees");
    private final Node nodeOptTree = new Node("Optimize tree node");
    private final AbstractProperty propOptTree = new RadioButtonProperty(nodeOptTree, "Optimize tree");
    private final Node nodeOptLens = new Node("Optimize edge lengths node");
    private final AbstractProperty propOptLens = new RadioButtonProperty(nodeOptLens, "Optimize edge lengths");
    private final Node nodeEdgeSupport = new Node("Edge support node");
    private final AbstractProperty propBootstrapYesNo = new RadioButtonProperty(nodeEdgeSupport, "Bootstrap");
    private final AbstractProperty propBootstrapRepeats = new TextFieldProperty(nodeEdgeSupport, "Number of bootstrap iterations");
    private final AbstractProperty propFastSupport = new ComboBoxProperty(nodeEdgeSupport, "Fast branch support method");


    @Override
    protected List<Node> createNodes() {

        // adding all nodes to list for creation
        List<Node> nodes = Lists.newArrayList();
        nodes.add(nodeAlignment);
        nodes.add(nodeDataType);
        nodes.add(nodeSubstModels);
        nodes.add(nodeTsTv);
        nodes.add(nodeRASmodel);
        nodes.add(nodeGamma);
        nodes.add(nodeStartingTree);
        nodes.add(nodeTreeSearch);
        nodes.add(nodeRandomStarts);
        nodes.add(nodeOptTree);
        nodes.add(nodeOptLens);
        nodes.add(nodeEdgeSupport);

        return nodes;
    }

    @Override
    protected void setInitialValues() {

        Boolean state = false;

        //Alignment file
        propAlignmentYesNo.setOption(RadioButtonProperty.OPTION_1, "File");
        propAlignmentYesNo.setOption(RadioButtonProperty.OPTION_2, "Example");
        propAlignmentYesNo.selectValue("File");

        //Data type
        propDataType.setOption(RadioButtonProperty.OPTION_1, "Amino-acids");
        propDataType.setOption(RadioButtonProperty.OPTION_2, "Nucleotides");
        propDataType.selectValue("Nucleotides");



        // Substitution model
        propSubstModels.setOption(ComboBoxProperty.OPTION_CHOICES, choicesSubstModels);
        propSubstModels.selectValue("LG");



        // Transition/transversion ratio
        propTsTvVal.selectValue("4.0");
        state = false;
        propTsTvVal.setActive(state);
        propTsTvYesNo.setOption(RadioButtonProperty.OPTION_1, "estimated");
        propTsTvYesNo.setOption(RadioButtonProperty.OPTION_2, "fixed");
        propTsTvYesNo.selectValue("estimated");


        // RAS model
        propRASnclasses.selectValue("4");
        propRASmodel.setOption(RadioButtonProperty.OPTION_1, "Discrete Gamma");
        propRASmodel.setOption(RadioButtonProperty.OPTION_2, "Free Rates");
        propRASmodel.selectValue("Free Rates");

        // Discrete Gamma model
        propGamma.selectValue("1.0");
        state = false;
        propGamma.setActive(state);
        AbstractProperty propGammaYesNo = new RadioButtonProperty(nodeGamma, "Estimated/fixed");
        propGammaYesNo.setOption(RadioButtonProperty.OPTION_1, "estimated");
        propGammaYesNo.setOption(RadioButtonProperty.OPTION_2, "fixed");
        propGammaYesNo.selectValue("estimated");
        state = false;
        propGammaYesNo.setActive(state);



        //Starting tree
        propStartingTreeYesNo.setOption(RadioButtonProperty.OPTION_1, "File");
        propStartingTreeYesNo.setOption(RadioButtonProperty.OPTION_2, "BioNJ");
        propStartingTreeYesNo.selectValue("BioNJ");


        //Starting tree
        propTreeSearch.setOption(ComboBoxProperty.OPTION_CHOICES,"NNI;SPR;NNI+SPR");
        propTreeSearch.selectValue("SPR");


        //Random starting trees
        propRandomStarts.selectValue("5");
        state = true;
        propRandomStarts.setActive(state);


        //Optimize tree
        propOptTree.setOption(RadioButtonProperty.OPTION_1, "Yes");
        propOptTree.setOption(RadioButtonProperty.OPTION_2, "No");
        propOptTree.selectValue("Yes");

        //Optimize edge lengths
        propOptLens.setOption(RadioButtonProperty.OPTION_1, "Yes");
        propOptLens.setOption(RadioButtonProperty.OPTION_2, "No");
        propOptLens.selectValue("Yes");

        //Edge support
        propBootstrapYesNo.setOption(RadioButtonProperty.OPTION_1, "Yes");
        propBootstrapYesNo.setOption(RadioButtonProperty.OPTION_2, "No");
        propBootstrapYesNo.selectValue("No");
        propBootstrapRepeats.selectValue("100");
        state = false;
        propBootstrapRepeats.setActive(state);

        propFastSupport.setOption(ComboBoxProperty.OPTION_CHOICES,"aLRT SH-like;aLRT Chi2-based;aBayes");
        propFastSupport.selectValue("aBayes");
        state = true;
        propFastSupport.setActive(state);

    }

    @Override
    protected void nodeChanged(Node node, AbstractProperty property, PropertyChangeEvent event)
    {

        String labelThatChanged = property.getLabel();

        // Input sequence file
        if("Source".equals(labelThatChanged))
            {
                if("Example".equals(property.getValue()))
                    {
                        Boolean state = false;
                        getProperty("Alignment file").setActive(state);
                    }
                else
                    {
                        Boolean state = true;
                        getProperty("Alignment file").setActive(state);
                    }
            }




        // Substitution models

        AbstractProperty currSubstModels = getProperty("Substitution models");

        if("Data type".equals(labelThatChanged))
            {

                if ("Amino-acids".equals(property.getValue()))
                    {
                        String choicesSubstModelsAA = "Dayhoff;LG;WAG;JTT";
                        currSubstModels.setOption(ComboBoxProperty.OPTION_CHOICES,choicesSubstModelsAA);
                        currSubstModels.selectValue("LG");
                    }
                else
                    {
                        String choicesSubstModelsNT = "JC69;K80;F81;F84;HKY85;TN93;GTR";
                        currSubstModels.setOption(ComboBoxProperty.OPTION_CHOICES,choicesSubstModelsNT);
                        currSubstModels.selectValue("HKY85");
                    }
            }



        // TsTv ratio

        AbstractProperty currTsTvVal   = getProperty("Ts/tv value");
        AbstractProperty currTsTvYesNo = getProperty("Estimated/fixed");

        if ("Data type".equals(labelThatChanged))
            {
                if("Amino-acids".equals(property.getValue()))
                    {
                        Boolean state = false;
                        currTsTvVal.setActive(state);
                        currTsTvYesNo.setActive(state);
                    }
                else
                    {
                        Boolean state = true;
                        currTsTvYesNo.setActive(state);
                        currTsTvYesNo.selectValue("estimated");

                        state = false;
                        currTsTvVal.setActive(state);
                    }
            }


        if("TsTv node".equals(node.getId()) && "Estimated/fixed".equals(labelThatChanged))
            {
                if("fixed".equals(property.getValue()))
                    {
                        Boolean state = true;
                        currTsTvVal.setActive(state);
                    }
                else
                    {
                        Boolean state = false;
                        currTsTvVal.setActive(state);
                    }
            }

        if("Substitution models".equals(labelThatChanged))
            {
                if(("K80".equals(property.getValue()) ||
                    "F84".equals(property.getValue())  ||
                    "HKY85".equals(property.getValue())  ||
                    "TN93".equals(property.getValue())))
                    {
                        Boolean state = true;
                        currTsTvYesNo.setActive(state);
                        currTsTvYesNo.selectValue("estimated");
                    }
                else
                    {
                        Boolean state = false;
                        currTsTvVal.setActive(state);
                        currTsTvYesNo.setActive(state);
                    }
            }



        // Rate  across sites
        AbstractProperty currGammaVal   = getNode("Discrete Gamma node").getProperty("Gamma shape parameter");
        AbstractProperty currGammaYesNo = getNode("Discrete Gamma node").getProperty("Estimated/fixed");

        if("RAS model node".equals(node.getId()))
            {
                if("Discrete Gamma".equals(property.getValue()))
                    {

                        AbstractProperty currNclasses = node.getProperty("Number of rate classes");

                        if("1".equals(currNclasses.getValue()))
                            {
                                Boolean state = false;
                                currGammaYesNo.setActive(state);
                                currGammaYesNo.selectValue("estimated");
                                currGammaVal.setActive(state);
                            }
                        else
                            {
                                Boolean state = true;
                                currGammaYesNo.setActive(state);
                                currGammaYesNo.selectValue("estimated");
                                state = false;
                                currGammaVal.setActive(state);
                            }
                    }
                else if("Free Rates".equals(property.getValue()))
                    {
                        Boolean state = false;
                        currGammaVal.setActive(state);
                        currGammaYesNo.setActive(state);
                    }

                if("Number of rate classes".equals(property.getLabel()))
                    {
                        if("1".equals(property.getValue()))
                            {
                                Boolean state = false;
                                currGammaYesNo.setActive(state);
                                currGammaVal.setActive(state);
                            }
                        else
                            {

                                AbstractProperty currRASmodel = node.getProperty("Rate variation model");

                                if("Discrete Gamma".equals(currRASmodel.getValue()))
                                    {
                                        Boolean state = true;
                                        currGammaYesNo.setActive(state);

                                        if("fixed".equals(currGammaYesNo.getValue()))
                                            {
                                                currGammaVal.setActive(state);
                                            }
                                        else
                                            {
                                                state = false;
                                                currGammaVal.setActive(state);
                                            }
                                    }
                            }
                    }

            }

        if("Discrete Gamma node".equals(node.getId()))
            {
                if("estimated".equals(property.getValue()))
                    {
                        Boolean state = false;
                        currGammaVal.setActive(state);
                    }
                else
                    {
                        Boolean state = true;
                        currGammaVal.setActive(state);
                    }
            }


        // Starting tree
        if("Starting tree node".equals(node.getId()) && "Source".equals(property.getLabel()))
            {
                if("File".equals(property.getValue()))
                    {
                        Boolean state = true;
                        getProperty("Starting tree").setActive(state);
                    }
                else
                    {
                        Boolean state = false;
                        getProperty("Starting tree").setActive(state);
                    }
            }

        // Random starting trees
        if("Tree search method".equals(property.getLabel()))
            {
                if("NNI".equals(property.getValue()))
                    {
                        Boolean state = false;
                        getProperty("Number of random starting trees").setActive(state);
                    }
                else
                    {
                        Boolean state = true;
                        getProperty("Number of random starting trees").setActive(state);
                    }
            }


        // Edge support
        if("Edge support node".equals(node.getId()))
            {
                if("Bootstrap".equals(property.getLabel()))
                    {
                        if("Yes".equals(property.getValue()))
                            {
                                Boolean state = true;
                                getProperty("Number of bootstrap iterations").setActive(state);
                                state = false;
                                getProperty("Fast branch support method").setActive(state);
                            }
                        else
                            {
                                Boolean state = false;
                                getProperty("Number of bootstrap iterations").setActive(state);
                                state = true;
                                getProperty("Fast branch support method").setActive(state);
                            }
                    }
            }


    }

}
