package phyml.gui.model;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.Map;

/**
 * An AbstractNode object can contain {@link AbstractProperty}s, as well as connections to other AbstractNodes.
 * <p/>
 *
 * Written by: Markus Binsteiner
 * Date: 2/10/13
 * Time: 4:37 PM
 */
public abstract class AbstractNode implements PropertyChangeListener {


    protected final String name;
    final private Map<String, AbstractProperty> properties = Maps.newLinkedHashMap();
    private List<AbstractNode> connections = Lists.newLinkedList();
    public AbstractNode(String name) {
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
            final AbstractNode other = (AbstractNode) obj;
            return Objects.equal(getName(), other.getName());
        }

        return false;
    }

    public List<AbstractNode> getConnections() {
        return connections;
    }

    public void addProperty(AbstractProperty prop) {
        properties.put(prop.getLabel(), prop);
    }

    public void addConnection(AbstractNode connection) {
        connections.add(connection);
    }

    public AbstractProperty getProperty(String label) {
        return properties.get(label);
    }

    public Map<String, AbstractProperty> getProperties() {
        return properties;
    }

    public void valueChanged(PropertyChangeEvent evt) {
        for ( AbstractNode con : connections ) {
            con.propertyChange(evt);
        }
    }

    public String toString() {
        return getName();
    }

    public void propertyChange(PropertyChangeEvent evt) {
        AbstractProperty property = (AbstractProperty) evt.getSource();
        connectedNodeChanged(property.getParentNode(), property, evt);
    }

    abstract public void connectedNodeChanged(AbstractNode node, AbstractProperty property, PropertyChangeEvent event);

}
