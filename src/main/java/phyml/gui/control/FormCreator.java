package phyml.gui.control;

import phyml.gui.model.Node;
import phyml.gui.view.InputFormPanel;
import phyml.gui.view.InputFormPanelCollapsible;
import phyml.gui.view.InputFormPanelSimple;
import phyml.gui.view.InputFormPanelTabbed;

import javax.swing.*;

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
    public FormCreator(NodeController nc, int layout) {
        this.controller = nc;
        this.layout = layout;
    }

    public FormCreator(NodeController nc) {
        this(nc, COLLAPSIBLE_LAYOUT);
    }

    public static void main(String[] args) {

        if ( args.length != 1 ) {
            System.err.println("Wrong number of arguments. Only name of controller class is allowed.");
            System.exit(1);
        }

        String controllerClass = args[0];

        NodeController controller = null;

        ClassLoader classLoader = FormCreator.class.getClassLoader();
        try {
            Class aClass;
            try {
                aClass = classLoader.loadClass(controllerClass);
            } catch (ClassNotFoundException e) {
                aClass = classLoader.loadClass("phyml.gui.control."+controllerClass);
            }

            controller = (NodeController) aClass.newInstance();

        } catch (Exception e) {
            System.err.println("Error creating controller class: "+e.getLocalizedMessage());
            System.exit(2);
        }

        FormCreator fc = new FormCreator(controller, FormCreator.COLLAPSIBLE_LAYOUT);

        fc.display();

    }

    public void display() {
        try {
            SwingUtilities.invokeAndWait(new Thread() {
                public void run() {
                    JFrame frame = new JFrame("InputForm");
                    frame.setSize(600, 400);
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
