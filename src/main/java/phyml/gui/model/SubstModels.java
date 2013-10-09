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

public class SubstModels extends AbstractNode 
{


    public SubstModels(String name) {
        super(name);
    }
    
    public void connectedNodeChanged(AbstractNode node, AbstractProperty property, PropertyChangeEvent event) {
        
        AbstractProperty currSubstModels = getProperty("Substitution models");
        
        if ("Data type node".equals(node.getName()) && "Amino-acids".equals(property.getValue())) 
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
}
