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

public class TsTv extends AbstractNode 
{


    public TsTv(String name) {
        super(name);
    }
    
    public void connectedNodeChanged(AbstractNode node, AbstractProperty property, PropertyChangeEvent event) 
    {
        AbstractProperty currTsTvVal   = getProperty("Ts/tv value");
        AbstractProperty currTsTvYesNo = getProperty("Estimated/fixed");

        if ("Data type node".equals(node.getName()))
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
                        currTsTvYesNo.setValue("estimated");
                        
                        state = false;
                        currTsTvVal.setActive(state);
                    }
            }

        if("TsTv node".equals(node.getName()))
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
        
        if("Substitution models node".equals(node.getName()))
            {
                if(("K80".equals(property.getValue()) || 
                    "F84".equals(property.getValue())  ||
                    "HKY85".equals(property.getValue())  ||
                    "TN93".equals(property.getValue())))
                    {
                        Boolean state = true;
                        currTsTvYesNo.setActive(state);
                        currTsTvYesNo.setValue("estimated");
                    }
                else
                    {
                        Boolean state = false;
                        currTsTvVal.setActive(state);                
                        currTsTvYesNo.setActive(state);
                    }
            }

        
    }
}
