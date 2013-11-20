package phyml.gui.control;

import com.google.common.collect.Lists;
import com.google.common.eventbus.EventBus;
import com.jgoodies.common.base.SystemUtils;
import com.jgoodies.looks.Options;
import grisu.jcommons.processes.ExternalCommand;
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

    public static EventBus eventBus = new EventBus();

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

    private List<Node> nodes;

    /**
     * Holds the current commandLine.
     */
    final protected LinkedList<String> commandLine = Lists.newLinkedList();
//    public String commandLine;

    private boolean nodesCreated = false;

    public NodeController(){
        this(false);
    }

    public NodeController(boolean useSystemLookAndFeel) {
        if ( useSystemLookAndFeel ) {
            setLookAndFeel();
        }
    }

    /**
     * Returns the node with the specified id.
     *
     * @param id the id of the node
     * @return the node or null if no node with the id exists
     */
    public Node getNode(String id) {

        if (StringUtils.isBlank(id)) {
            return null;
        }

        for (Node node : nodes) {
            if (id.equalsIgnoreCase(node.getId())) {
                return node;
            }
        }
        return null;
    }

    /**
     * Returns all the nodes that this controller has access to.
     *
     * Dont set any values there yet, this is done in the setInitialValues() method
     *
     * @return all nodes
     */
    abstract protected List<Node> createNodes();

    /**
     * Gets the title of this controller, usually the name of the application.
     *
     * @return the title
     */
    abstract public String getTitle();

    public void initializeNodes() {

        this.nodes = createNodes();

        this.nodesCreated = true;

        for ( Node n : this.nodes ) {
            n.setController(this);
        }
        setInitialValues();
    }

    /**
     * Sets the inital values in the model.
     */
    abstract protected void setInitialValues();

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
    abstract protected void nodeChanged(Node node, AbstractProperty property, PropertyChangeEvent event);

    public void nodeValueChanged(Node node, AbstractProperty property, PropertyChangeEvent event) {
        if ( nodesCreated ) {
            nodeChanged(node, property, event);
        }
        eventBus.post(commandLine);
    }

    /**
     * Returns the property with the specified id.
     *
     * @param id the id
     * @return the property or null if no property with that id exists
     */
    public AbstractProperty getProperty(String id) {

        if (StringUtils.isBlank(id)) {
            return null;
        }
        for (Node node : nodes) {
            for (AbstractProperty prop : node.getProperties().values()) {
                if (id.equalsIgnoreCase(prop.getId())) {
                    return prop;
                }
            }
        }
        return null;
    }


    public void executeCommand() {
        List<String> commandlineTemp = Lists.newArrayList("echo", "hello world");
        //List<String> commandlineTemp = Lists.newArrayList("sleep", "10");
        final ExternalCommand ec = new ExternalCommand(commandlineTemp);
        //final ExternalCommand ec = new ExternalCommand(commandLine);

        eventBus.post(ec);

        Thread executeThread = new Thread(){
            public void run() {
                ec.execute();
                System.out.println(StringUtils.join(ec.getStdout(), "\n"));
            }
        };
        executeThread.start();

    }

}
