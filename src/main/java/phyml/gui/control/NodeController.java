package phyml.gui.control;

import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import phyml.gui.model.AbstractProperty;
import phyml.gui.model.Node;

import java.beans.PropertyChangeEvent;
import java.util.List;

/**
 * Project: grisu
 * <p/>
 * Written by: Markus Binsteiner
 * Date: 10/10/13
 * Time: 10:35 AM
 */
abstract public class NodeController {

    protected static final Logger myLogger = LoggerFactory.getLogger(NodeController.class);

    final private List<Node> nodes;

    /**
     * Holds the current commandline.
     */
    final protected List<String> commandline = Lists.newLinkedList();

    public NodeController() {
        this.nodes = initNodes();
        for (Node node : this.nodes) {
            node.setController(this);
        }
    }

    /**
     * Returns the node with the specified name.
     *
     * @param name the name of the node
     * @return the node or null if no node with the name exists
     */
    public Node getNode(String name) {

        if (StringUtils.isBlank(name)) {
            return null;
        }

        for (Node node : nodes) {
            if (name.equalsIgnoreCase(node.getName())) {
                return node;
            }
        }
        return null;
    }

    /**
     * returns all the nodes that this controller has access to
     *
     * @return all nodes
     */
    abstract protected List<Node> initNodes();

    public List<Node> getNodes() {
        return nodes;
    }

    /**
     * Called whenever a property of one of this controllers nodes changes.
     *
     * @param node     the node that contained the property that changed
     * @param property the property that changed
     * @param event    the propertyChangeEvent, containing old and new values and such
     */
    abstract public void nodeChanged(Node node, AbstractProperty property, PropertyChangeEvent event);

    /**
     * Returns the property with the specified label.
     *
     * @param label the label
     * @return the property or null if no property with that label exists
     */
    public AbstractProperty getProperty(String label) {

        if (StringUtils.isBlank(label)) {
            return null;
        }
        for (Node node : nodes) {
            for (AbstractProperty prop : node.getProperties().values()) {
                if (label.equalsIgnoreCase(prop.getLabel())) {
                    return prop;
                }
            }
        }
        return null;
    }

}
