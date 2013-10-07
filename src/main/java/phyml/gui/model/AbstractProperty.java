package phyml.gui.model;

import com.google.common.base.Objects;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.util.Map;
import java.util.Set;

/**
 * A class extending AbstractProperty is a value a user can change via the UI.
 * <p/>
 * It'll propagate every state change to it's parent node, who'll notify all its connected nodes.
 * <p/>
 * Written by: Markus Binsteiner
 * Date: 2/10/13
 * Time: 4:38 PM
 */
public abstract class AbstractProperty {

    private static final Logger myLogger = LoggerFactory.getLogger(AbstractProperty.class);
    protected final String label;
    private final AbstractNode parent;
    private String value;
    private boolean active = true;
    private final Map<String, String> options = Maps.newHashMap();

    public AbstractProperty(AbstractNode parent, String label) {
        this.label = label;
        this.parent = parent;
        this.parent.addProperty(this);
    }

    public AbstractNode getParentNode() {
        return parent;
    }

    abstract public JComponent getComponent();

    abstract protected void reInitialize();

    abstract protected void lockUI(boolean lock);

    public String getValue() {
        return value;
    }

    public void selectValue(final String value) {
        SwingUtilities.invokeLater(new Thread() {
            public void run() {
                setValue(value);
            }
        });
    }

    abstract protected void setValue(String value);

    abstract public Set<String> getOptionKeys();

    public int hashCode() {
        return Objects.hashCode(getLabel());
    }

    public boolean equals(Object obj) {

        if (obj == this) return true;
        if (obj == null) return false;

        if (obj instanceof AbstractProperty) {
            final AbstractProperty otherImage = (AbstractProperty) obj;

            return Objects.equal(getLabel(), otherImage.getLabel());
        }

        return false;
    }

    synchronized void setActive(final boolean active) {

        this.active = active;

        SwingUtilities.invokeLater(new Thread() {
            public void run() {
                lockUI(!active);
            }
        });

    }

    public void setOption(String key, String value) {
        if ( ! getOptionKeys().contains(key) ) {
            throw new RuntimeException("Can't set key '"+key+"' for property: "+this.getClass().getName());
        }
        options.put(key, value);

        SwingUtilities.invokeLater(new Thread() {
            public void run() {
                reInitialize();
            }
        });
    }

    public String getLabel() {
        return label;
    }

    public String getOption(String key) {
        return options.get(key);
    }

    protected void valueChanged(String oldValue, String newValue) {

        this.value = newValue;

        if (this.active) {

            PropertyChangeEvent event = new PropertyChangeEvent(this, "value", oldValue, newValue);
            myLogger.debug("Changed property for {}: {} [old -> {}] | [new -> {}]", new Object[]{getLabel(), event.getPropertyName(), event.getOldValue(), event.getNewValue()});

            parent.valueChanged(event);
        }
    }

    public String toString() {
        return getLabel();
    }

}
