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

public class RateClasses extends AbstractNode 
{

    public RateClasses(String name) {
        super(name);
    }
    
    public void connectedNodeChanged(AbstractNode node, AbstractProperty property, PropertyChangeEvent event) 
    {
    }
}
