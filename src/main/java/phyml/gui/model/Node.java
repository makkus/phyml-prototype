package phyml.gui.model;

import com.google.common.base.Objects;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import phyml.gui.control.NodeController;

import java.beans.PropertyChangeEvent;
import java.util.Map;

/**
 * An AbstractNode object can contain {@link AbstractProperty}s, as well as connections to other AbstractNodes.
 * <p/>
 * <p/>
 * Written by: Markus Binsteiner
 * Date: 2/10/13
 * Time: 4:37 PM
 */
public class Node {

    protected static final Logger myLogger = LoggerFactory.getLogger(Node.class);

    protected final String name;
    final private Map<String, AbstractProperty> properties = Maps.newLinkedHashMap();
    private NodeController controller;

    public Node(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int hashCode() {
        return Objects.hashCode(getName());
    }

    public boolean equals(Object obj) {

        if (obj == this) return true;
        if (obj == null) return false;

        if (obj instanceof AbstractProperty) {
            final Node other = (Node) obj;
            return Objects.equal(getName(), other.getName());
        }

        return false;
    }

    public void addProperty(AbstractProperty prop) {
        properties.put(prop.getLabel(), prop);
    }

    public AbstractProperty getProperty(String label) {
        return properties.get(label);
    }

    public Map<String, AbstractProperty> getProperties() {
        return properties;
    }

    public void valueChanged(PropertyChangeEvent evt) {
        AbstractProperty property = (AbstractProperty) evt.getSource();
        controller.nodeChanged(property.getParentNode(), property, evt);
    }

    public String toString() {
        return getName();
    }

    public void setController(NodeController nodeController) {
        this.controller = nodeController;
    }
}
