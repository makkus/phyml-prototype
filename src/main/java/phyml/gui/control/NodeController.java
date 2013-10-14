package phyml.gui.control;

import com.google.common.collect.Lists;
import com.jgoodies.common.base.SystemUtils;
import com.jgoodies.looks.Options;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import phyml.gui.model.AbstractProperty;
import phyml.gui.model.Node;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.util.LinkedList;
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


    private static void setLookAndFeel() {
        		try {
			myLogger.debug("Setting look and feel.");

			UIManager.put(Options.USE_SYSTEM_FONTS_APP_KEY, Boolean.TRUE);
			Options.setDefaultIconSize(new Dimension(18, 18));

			String lafName = null;
			if (SystemUtils.IS_OS_WINDOWS) {
				lafName = Options.JGOODIES_WINDOWS_NAME;
			} else {
				lafName = UIManager.getSystemLookAndFeelClassName();
			}

			try {
				myLogger.debug("Look and feel name:" + lafName);
				UIManager.setLookAndFeel(lafName);
			} catch (Exception e) {
				System.err.println("Can't set look & feel:" + e);
			}

			// UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (final Exception e) {
			myLogger.error(e.getLocalizedMessage(), e);
		}
    }

    final private List<Node> nodes;

    /**
     * Holds the current commandline.
     */
    final protected LinkedList<String> commandline = Lists.newLinkedList();

    public NodeController(){
        this(true);
    }

    public NodeController(boolean useSystemLookAndFeel) {
        if ( useSystemLookAndFeel ) {
            setLookAndFeel();
        }
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
