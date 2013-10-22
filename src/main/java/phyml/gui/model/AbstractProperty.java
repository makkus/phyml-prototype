package phyml.gui.model;

import com.google.common.collect.Lists;
import com.google.common.base.Objects;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.util.Map;
import java.util.Set;
import java.util.LinkedList;
import java.util.List;

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

    public static final String DEFAULT_GROUP_NAME = "__default__";

    private static final Logger myLogger = LoggerFactory.getLogger(AbstractProperty.class);
    protected final String id;
    protected final String label;
    private final Node parent;
    private String value;
    private boolean active = true;
    private final Map<String, String> options = Maps.newHashMap();

    private final String group;

    public AbstractProperty(Node parent, String id, String group) {
        this(parent, id, id, group);
    }

    public AbstractProperty(Node parent, String id, String label, String group) {
        this.label = label;
        this.id = id;
        this.parent = parent;
        if (StringUtils.isBlank(group)) {
            this.group = DEFAULT_GROUP_NAME;
        } else {
            this.group = group;
        }
        this.parent.addProperty(this);
    }

    public Node getParentNode() {
        return parent;
    }

    public String commandLabel;

    abstract public JComponent getComponent();

    abstract protected void reInitialize();

    abstract protected void lockUI(boolean lock);

    /**
     * Returns the currently set value of this property.
     *
     * @return the value (as a String)
     */
    public String getValue() {
        return value;
    }

    /**
     * Enables the setting of a value for a property pragmatically. Make sure to only set 'valid' values.
     * <p/>
     * This might trigger other values to be set down the line of connections of nodes.
     * <p/>
     * * @param value the value to set the property to
     */
    public void selectValue(final String value) {
        SwingUtilities.invokeLater(new Thread() {
            public void run() {
                setValue(value);
            }
        });
    }

    abstract protected void setValue(String value);

    /**
     * Returns all 'valid' keys for options that can be set for this property.
     *
     * @return the keys
     */
    abstract public Set<String> getOptionKeys();

    public int hashCode() {
        return Objects.hashCode(getId());
    }

    public boolean equals(Object obj) {

        if (obj == this) return true;
        if (obj == null) return false;

        if (obj instanceof AbstractProperty) {
            final AbstractProperty otherImage = (AbstractProperty) obj;

            return Objects.equal(getId(), otherImage.getId());
        }

        return false;
    }

    public void setActive(final boolean active) {

        this.active = active;

        SwingUtilities.invokeLater(new Thread() {
            public void run() {
                lockUI(!active);
            }
        });

    }

    /**
     * Sets an option for this property. This will be reflected in the UI (e.g. different choices for comboboxes,
     * or different lables for radio buttons.
     *
     * @param key   the key of the option (use {@link #getOptionKeys()} to get a list of valid options)
     * @param value the value for the option
     */
    public void setOption(String key, String value) {
        if (!getOptionKeys().contains(key)) {
            throw new RuntimeException("Can't set key '" + key + "' for property: " + this.getClass().getName());
        }
        options.put(key, value);

        SwingUtilities.invokeLater(new Thread() {
            public void run() {
                reInitialize();
            }
        });
    }

    /**
     * The label for this property. This needs to be unique across the whole application.
     *
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * Returns the option that is currently set for this key.
     *
     * @param key the key
     * @return the option value
     */
    protected String getOption(String key) {
        return options.get(key);
    }

    protected void valueChanged(String oldValue, String newValue) {

        this.value = newValue;

        if (this.active) {

            PropertyChangeEvent event = new PropertyChangeEvent(this, "value", oldValue, newValue);
            myLogger.debug("Changed property for {}: {} [old -> {}] | [new -> {}]", new Object[]{getId(), event.getPropertyName(), event.getOldValue(), event.getNewValue()});

            parent.valueChanged(event);
        }
    }

    public String toString() {
        return getId();
    }

    public String getGroup() {
        return group;
    }

    public String getId() {
        return id;
    }
}
