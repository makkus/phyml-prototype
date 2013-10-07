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

        AbstractProperty radioButton22 = getProperty("label22");

        if ( "node3".equals(node.getName()) &&
                "label3".equals(property.getLabel()) &&
                "choice3".equals(property.getValue()) ) {

            radioButton22.setActive(false);
            myLogger.debug("Disabled property: "+property.getLabel());
        } else {
            radioButton22.setActive(true);
            myLogger.debug("Enabled property: "+property.getLabel());
        }


    }


}
