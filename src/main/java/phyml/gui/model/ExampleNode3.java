package phyml.gui.model;

import phyml.gui.view.ComboBoxProperty;

import java.beans.PropertyChangeEvent;

/**
 * Project: grisu
 * <p/>
 * Written by: Markus Binsteiner
 * Date: 8/10/13
 * Time: 10:19 AM
 */

public class ExampleNode3 extends AbstractNode {


        public ExampleNode3(String name) {
            super(name);
        }

        public void connectedNodeChanged(AbstractNode node, AbstractProperty
                property, PropertyChangeEvent event) {

            AbstractProperty currSubstModels = getProperty("label3");

            if ("label21".equals(property.getLabel()) && "button1".equals(event.getNewValue())) {
                String choicesSubstModelsAA = "Dayhoff;LG;WAG;JTT";
                currSubstModels.setOption(ComboBoxProperty.OPTION_CHOICES, choicesSubstModelsAA);
                myLogger.debug("Changed Subst model property: "+currSubstModels.getParentNode().getName()+" value:"+currSubstModels.getLabel());
            } else if ("label21".equals(property.getLabel()) && "button2".equals(event.getNewValue())) {
                String choicesSubstModelsNT = "JC69;K80;F81;F84;HKY85;TN93;GTR";
                currSubstModels.setOption(ComboBoxProperty.OPTION_CHOICES, choicesSubstModelsNT);
                myLogger.debug("Changed Subst model property: "+currSubstModels.getParentNode().getName()+" value:"+currSubstModels.getLabel());
            } else {
                // maybe do something else? check for label22, for example...
            }

        }
}
