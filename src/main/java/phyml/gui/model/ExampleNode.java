package phyml.gui.model;

import java.beans.PropertyChangeEvent;

/**
 * Project: grisu
 * <p/>
 * Written by: Markus Binsteiner
 * Date: 7/10/13
 * Time: 3:44 PM
 */
public class ExampleNode extends AbstractNode {

    public ExampleNode(String name) {
        super(name);
    }

    @Override
    public void connectedNodeChanged(AbstractNode node, AbstractProperty property, PropertyChangeEvent event) {

        System.out.println("Node '"+name+"': Property of connected node changed ("+node.toString()+"/"+property.toString()+")");

    }

}
