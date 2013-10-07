package phyml.gui.model;

import org.apache.commons.lang.StringUtils;
import phyml.gui.view.ComboBoxProperty;

import java.beans.PropertyChangeEvent;

/**
 * Project: grisu
 * <p/>
 * Written by: Markus Binsteiner
 * Date: 8/10/13
 * Time: 10:19 AM
 */

public class SubstModels extends AbstractNode {


        public SubstModels(String name) {
            super(name);
        }

        public void connectedNodeChanged(AbstractNode node, AbstractProperty
                property, PropertyChangeEvent event) {

            AbstractProperty currSubstModels = getProperty("Substitution models");

            if ("Data type node".equals(node.getName()) && "Amino-acids".equals(property.getLabel())) {
                String[] choicesSubstModelsAA = new String[]{"Dayhoff", "LG", "WAG", "JTT"};
                AbstractProperty propSubstModels = new ComboBoxProperty(currSubstModels.getParentNode(), "Substitution models");
                propSubstModels.setOption(ComboBoxProperty.OPTION_CHOICES, StringUtils.join(choicesSubstModelsAA, ";"));
                myLogger.debug("Changed Subst model property: "+currSubstModels.getParentNode().getName()+" value:"+currSubstModels.getLabel());
            } else {
                String[] choicesSubstModelsNT = new String[]{"JC69", "K80", "F81", "F84", "HKY85", "TN93", "GTR"};
                AbstractProperty propSubstModels = new ComboBoxProperty(currSubstModels.getParentNode(), "Substitution models");
                propSubstModels.setOption(ComboBoxProperty.OPTION_CHOICES, StringUtils.join(choicesSubstModelsNT, ";"));
                myLogger.debug("Changed Subst model property: "+currSubstModels.getParentNode().getName()+" value:"+currSubstModels.getLabel());
            }
        }
}
