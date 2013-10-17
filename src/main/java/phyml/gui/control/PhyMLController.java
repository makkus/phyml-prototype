package phyml.gui.control;

import javax.swing.*;

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

//        fc.setDisplayDebug(true);

        // alternatively, you can use different layouts
        // FormCreator fc = new FormCreator(phymlController, FormCreator.SIMPLE_LAYOUT);
        // FormCreator fc = new FormCreator(phymlController, FormCreator.TABBED_LAYOUT);

        // display the gui
        fc.display();

    }

    private final Node nodeInput = new Node("Input data");
    private final AbstractProperty propAlignment = new FilePathProperty(nodeInput, "Alignment file");
    private final AbstractProperty propAlignmentYesNo = new RadioButtonProperty(nodeInput, "Source");
    private final AbstractProperty propDataType = new RadioButtonProperty(nodeInput, "Data type");
    private final AbstractProperty propFormat = new RadioButtonProperty(nodeInput, "Format");

    private final Node nodeModel = new Node("Substitution model");
    private final String choicesSubstModels = "Dayhoff;LG;WAG;JTT";
    private final AbstractProperty propSubstModels = new ComboBoxProperty(nodeModel, "Substitution models");
    private final AbstractProperty propTsTvVal = new TextFieldProperty(nodeModel,"Ts/tv value","Ts/Tv");
    private final AbstractProperty propTsTvYesNo = new RadioButtonProperty(nodeModel,"TsTvEstFix","Estimated/Fixed","Ts/Tv");

    private final AbstractProperty propRASnclasses = new SpinnerProperty(nodeModel, "Number of rate classes","Rate across sites");
    private final AbstractProperty propRASmodel = new RadioButtonProperty(nodeModel, "Rate variation model","Rate across sites");
    private final AbstractProperty propGammaVal = new TextFieldProperty(nodeModel, "Gamma shape parameter","Rate across sites");
    private final AbstractProperty propGammaYesNo = new RadioButtonProperty(nodeModel,"GammaEstFix","Estimated/Fixed","Rate across sites");
    private final AbstractProperty propInvVal = new TextFieldProperty(nodeModel, "Proportion of invariants","Rate across sites");
    private final AbstractProperty propInvYesNo = new RadioButtonProperty(nodeModel,"InvEstFix","Estimated/Fixed","Rate across sites");

    private final Node nodeTree = new Node("Tree searching");
    private final AbstractProperty propStartingTree = new FilePathProperty(nodeTree, "Starting tree");
    private final AbstractProperty propStartingTreeYesNo = new ComboBoxProperty(nodeTree, "Source");
    private final AbstractProperty propTreeSearch = new ComboBoxProperty(nodeTree, "Tree search method");
    private final AbstractProperty propRandomStarts = new TextFieldProperty(nodeTree, "Number of random starting trees");
    private final AbstractProperty propOptTree = new RadioButtonProperty(nodeTree, "Optimize tree");
    private final AbstractProperty propOptLens = new RadioButtonProperty(nodeTree, "Optimize edge lengths");


    
    private final Node nodeEdgeSupport = new Node("Branch support");
    private final AbstractProperty propBootstrapYesNo = new RadioButtonProperty(nodeEdgeSupport, "Bootstrap");
    private final AbstractProperty propBootstrapRepeats = new TextFieldProperty(nodeEdgeSupport, "Number of bootstrap iterations");
    private final AbstractProperty propFastSupport = new ComboBoxProperty(nodeEdgeSupport, "Fast branch support method");


    @Override
    protected List<Node> createNodes() {

        // adding all nodes to list for creation
        List<Node> nodes = Lists.newArrayList();
        nodes.add(nodeInput);
        nodes.add(nodeModel);
        nodes.add(nodeTree);
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

        System.out.println("propDataType:"+propDataType.getValue());

        //Format
        propFormat.setOption(RadioButtonProperty.OPTION_1, "Interleaved");
        propFormat.setOption(RadioButtonProperty.OPTION_2, "Sequential");
        propFormat.selectValue("Interleaved");


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
        state = true;
        propTsTvYesNo.setActive(state);

        // RAS model
        propRASnclasses.setOption(SpinnerProperty.OPTION_MIN, "1");
        propRASnclasses.setOption(SpinnerProperty.OPTION_MAX, "10");
        propRASnclasses.setOption(SpinnerProperty.OPTION_STEP, "1");
        propRASnclasses.selectValue("4");

        propRASmodel.setOption(RadioButtonProperty.OPTION_1, "Discrete Gamma");
        propRASmodel.setOption(RadioButtonProperty.OPTION_2, "Free Rates");
        propRASmodel.selectValue("Free Rates");

        // Discrete Gamma model
        propGammaVal.selectValue("1.0");
        state = false;
        propGammaVal.setActive(state);
        propGammaYesNo.setOption(RadioButtonProperty.OPTION_1, "estimated");
        propGammaYesNo.setOption(RadioButtonProperty.OPTION_2, "fixed");
        propGammaYesNo.selectValue("estimated");
        state = false;
        propGammaYesNo.setActive(state);

        propInvVal.selectValue("0.0");
        state = true;
        propInvVal.setActive(state);
        propInvYesNo.setOption(RadioButtonProperty.OPTION_1, "estimated");
        propInvYesNo.setOption(RadioButtonProperty.OPTION_2, "fixed");
        propInvYesNo.selectValue("fixed");
        state = true;
        propInvYesNo.setActive(state);



        //Starting tree
        propStartingTreeYesNo.setOption(ComboBoxProperty.OPTION_CHOICES, "BioNJ;Parsimony;File");
        propStartingTreeYesNo.selectValue("BioNJ");
        state = false;
        propStartingTree.setActive(state);

        //Search algo
        propTreeSearch.setOption(ComboBoxProperty.OPTION_CHOICES,"NNI;SPR;NNI+SPR");
        propTreeSearch.selectValue("SPR");
        state = true;
        propTreeSearch.setActive(state);


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

        String idThatChanged = property.getId();

        // Input sequence file
        if("Source".equals(idThatChanged))
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

        // AbstractProperty currSubstModels = getProperty("Substitution models");

        if("Data type".equals(idThatChanged))
            {

                if ("Amino-acids".equals(property.getValue()))
                    {
                        String choicesSubstModelsAA = "Dayhoff;LG;WAG;JTT";
                        propSubstModels.setOption(ComboBoxProperty.OPTION_CHOICES,choicesSubstModelsAA);
                        propSubstModels.selectValue("LG");
                    }
                else
                    {
                        String choicesSubstModelsNT = "JC69;K80;F81;F84;HKY85;TN93;GTR";
                        propSubstModels.setOption(ComboBoxProperty.OPTION_CHOICES,choicesSubstModelsNT);
                        propSubstModels.selectValue("HKY85");
                    }
            }



        // TsTv ratio

        // AbstractProperty currTsTvVal   = getProperty("Ts/tv value");
        // AbstractProperty currTsTvYesNo = getProperty("TstvEstFix");


        if ("Data type".equals(idThatChanged))
            {
                if("Amino-acids".equals(property.getValue()))
                    {
                        Boolean state = false;
                        propTsTvVal.setActive(state);
                        propTsTvYesNo.setActive(state);
                    }
                else
                    {
                        Boolean state = true;
                        propTsTvYesNo.setActive(state);
                        propTsTvYesNo.selectValue("estimated");

                        state = false;
                        propTsTvVal.setActive(state);
                    }
            }


        if("TsTvEstFix".equals(idThatChanged))
            {
                if("fixed".equals(propTsTvYesNo.getValue()))
                    {
                        Boolean state = true;
                        propTsTvVal.setActive(state);
                    }
                else
                    {
                        Boolean state = false;
                        propTsTvVal.setActive(state);
                    }
            }

        if("Substitution models".equals(idThatChanged))
            {
                if(("K80".equals(property.getValue()) ||
                    "F84".equals(property.getValue())  ||
                    "HKY85".equals(property.getValue())  ||
                    "TN93".equals(property.getValue())))
                    {
                        Boolean state = true;
                        propTsTvYesNo.setActive(state);
                        propTsTvYesNo.selectValue("estimated");
                    }
                else
                    {
                        Boolean state = false;
                        propTsTvVal.setActive(state);
                        propTsTvYesNo.setActive(state);
                    }
            }



        // Rate  across sites

        System.out.println(node.getId()+" <> "+property.getValue()+" <> "+propRASnclasses.getValue());

        if("Substitution model".equals(node.getId()))
            {
                if("Discrete Gamma".equals(property.getValue()))
                    {

                        if("1".equals(propRASnclasses.getValue()))
                            {
                                Boolean state = false;
                                propGammaYesNo.setActive(state);
                                propGammaYesNo.selectValue("estimated");
                                propGammaVal.setActive(state);
                            }
                        else
                            {
                                Boolean state = true;
                                propGammaYesNo.setActive(state);
                                propGammaYesNo.selectValue("estimated");
                                state = false;
                                propGammaVal.setActive(state);
                            }
                    }
                else if("Free Rates".equals(property.getValue()))
                    {
                        Boolean state = false;
                        propGammaVal.setActive(state);
                        propGammaYesNo.setActive(state);
                    }

                if("Number of rate classes".equals(property.getLabel()))
                    {
                        if("1".equals(property.getValue()))
                            {
                                Boolean state = false;
                                propGammaYesNo.setActive(state);
                                propGammaVal.setActive(state);
                            }
                        else
                            {

                                AbstractProperty currRASmodel = node.getProperty("Rate variation model");

                                if("Discrete Gamma".equals(propRASmodel.getValue()))
                                    {
                                        Boolean state = true;
                                        propGammaYesNo.setActive(state);

                                        if("fixed".equals(propGammaYesNo.getValue()))
                                            {
                                                propGammaVal.setActive(state);
                                            }
                                        else
                                            {
                                                state = false;
                                                propGammaVal.setActive(state);
                                            }
                                    }
                            }
                    }

            }

        if("GammaEstFix".equals(idThatChanged))
            {
                if("estimated".equals(property.getValue()))
                    {
                        Boolean state = false;
                        propGammaVal.setActive(state);
                    }
                else
                    {
                        Boolean state = true;
                        propGammaVal.setActive(state);
                    }
            }


        // Starting tree
        if("Tree searching".equals(node.getId()) && "Source".equals(property.getLabel()))
            {
                if("File".equals(property.getValue()))
                    {
                        Boolean state = true;
                        propStartingTree.setActive(state);
                    }
                else
                    {
                        Boolean state = false;
                        propStartingTree.setActive(state);
                    }
            }

        // Random starting trees
        if("Tree searching".equals(property.getLabel()))
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
        if("Branch support".equals(node.getId()))
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
