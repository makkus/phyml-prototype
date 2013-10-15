package phyml.gui.control;

import org.apache.commons.lang.StringUtils;
import phyml.gui.model.Node;
import phyml.gui.view.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;

/**
 * This class assembles the Form according to the node list.
 * <p/>
 * Later on we can make it easier to configure (for example with a text file), but for now configuration
 * is done programatically.
 * <p/>
 * Written by: Markus Binsteiner
 * Date: 4/10/13
 * Time: 3:38 PM
 */
public class FormCreator {

    public static final int SIMPLE_LAYOUT = 1;
    public static final int TABBED_LAYOUT = 2;
    public static final int COLLAPSIBLE_LAYOUT = 3;
    private final NodeController controller;
    private final int layout;
    private InputFormPanel inputFormPanel;
    private boolean displayDebug = false;

    public FormCreator(NodeController nc) {
        this(nc, COLLAPSIBLE_LAYOUT);
    }

    public FormCreator(NodeController nc, int layout) {
        this.controller = nc;
        this.layout = layout;
        nc.initializeNodes();

    }

    public static void main(String[] args) {

        if (args.length < 1 || args.length > 2) {
            System.err.println("Wrong number of arguments. Only name of controller class (and, optionally, 'debug' string) is allowed.");
            System.exit(1);
        }

        String controllerClass = args[0];
        boolean debug = false;
        if ( args.length == 2 ) {
            if (StringUtils.equals("debug", args[1])) {
                debug = true;
            }
        }

        NodeController controller = null;

        ClassLoader classLoader = FormCreator.class.getClassLoader();
        try {
            Class aClass;
            try {
                aClass = classLoader.loadClass(controllerClass);
            } catch (ClassNotFoundException e) {
                aClass = classLoader.loadClass("phyml.gui.control." + controllerClass);
            }

            controller = (NodeController) aClass.newInstance();

        } catch (Exception e) {
            System.err.println("Error creating controller class: " + e.getLocalizedMessage());
            System.exit(2);
        }

        FormCreator fc = new FormCreator(controller, FormCreator.COLLAPSIBLE_LAYOUT);

        fc.setDisplayDebug(debug);

        fc.display();

    }

    public void setDisplayDebug(boolean displayDebug) {
        this.displayDebug = displayDebug;
    }

    public void display() {
        try {
            SwingUtilities.invokeAndWait(new Thread() {
                public void run() {
                    JFrame frame = new JFrame("InputForm");
                    frame.setSize(800, 400);
                    JScrollPane scrollPane = new JScrollPane(getForm().getPanel());
                    frame.setContentPane(scrollPane);
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    //        frame.pack();
                    frame.setVisible(true);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public InputFormPanel getForm() {

        if (inputFormPanel == null) {
            switch (layout) {
                case SIMPLE_LAYOUT:
                    inputFormPanel = new InputFormPanelSimple();
                    break;
                case TABBED_LAYOUT:
                    inputFormPanel = new InputFormPanelTabbed();
                    break;
                default:
                    inputFormPanel = new InputFormPanelCollapsible();
            }

            if (displayDebug) {
                JPanel cmd = new CommandlineDebugPanel();
                cmd.setBorder(new TitledBorder("Current commandline"));
                inputFormPanel.getPanel().add(cmd);
            }

            for (Node n : controller.getNodes()) {
                inputFormPanel.addNode(n);
            }

        }

        return inputFormPanel;
    }

    private JPanel assembleFormField(Node node) {
        return null;
    }

}
