package phyml.gui.model;

import java.beans.PropertyChangeEvent;

/**
 * Project: grisu
 * <p/>
 * Written by: Markus Binsteiner
 * Date: 7/10/13
 * Time: 3:44 PM
 */
public class ExampleNode2 extends AbstractNode {

    public ExampleNode2(String name) {
        super(name);
    }

    @Override
    public void connectedNodeChanged(AbstractNode node, AbstractProperty property, PropertyChangeEvent event) {

        AbstractProperty radioButton21 = getProperty("label21");
        AbstractProperty radioButton22 = getProperty("label22");

        if ( "label3".equals(property.getLabel()) &&
                "JTT".equals(property.getValue()) ) {
            radioButton22.setActive(false);
            myLogger.debug("Disabled property: "+property.getLabel());

        } else {
            radioButton22.setActive(true);
            myLogger.debug("Enabled property: "+property.getLabel());
        }


    }


}
