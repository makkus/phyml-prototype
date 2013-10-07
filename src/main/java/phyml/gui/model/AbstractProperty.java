package phyml.gui.model;

import com.google.common.base.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.beans.PropertyChangeEvent;

/**
 * A class extending AbstractProperty is a value a user can change via the UI.
 * <p/>
 * It'll propagate every state change to it's parent node, who'll notify all its connected nodes.
 * <p/>
 * Written by: Markus Binsteiner
 * Date: 2/10/13
 * Time: 4:38 PM
 */
public abstract class AbstractProperty<T> {

    private static final Logger myLogger = LoggerFactory.getLogger(AbstractProperty.class);
    protected final String label;
    private final AbstractNode parent;
    private T value;
    private boolean active = true;

    public AbstractProperty(AbstractNode parent, String label) {
        this.label = label;
        this.parent = parent;
        this.parent.addProperty(this);
    }

    public AbstractNode getParentNode() {
        return parent;
    }

    abstract public JComponent getComponent();

    abstract protected void lockUI(boolean lock);

    public T getValue() {
        return value;
    }

    abstract public void setValue(T value);

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

    public String getLabel() {
        return label;
    }

    protected void valueChanged(T oldValue, T newValue) {

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
