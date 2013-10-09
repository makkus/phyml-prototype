package phyml.gui.model;

import org.apache.commons.lang.StringUtils;
import phyml.gui.view.ComboBoxProperty;

import java.beans.PropertyChangeEvent;

/**
 * Project: grisu
 * <p/>
 * Written by: Markus Binsteiner
 * Date: 8/10/13
 * Time: 10:19 AM
 */

public class Gamma extends AbstractNode 
{
    public Gamma(String name) {
        super(name);
    }
    
    public void connectedNodeChanged(AbstractNode node, AbstractProperty property, PropertyChangeEvent event) 
    {
        AbstractProperty currGammaVal   = getProperty("Gamma shape parameter");
        AbstractProperty currGammaYesNo = getProperty("Estimated/fixed");

        if("RAS model node".equals(node.getName()))
            {
                if("Discrete Gamma".equals(property.getValue())) 
                    {

                        AbstractProperty currNclasses = node.getProperty("Number of rate classes");

                       if("1".equals(currNclasses.getValue()))
                           {
                               Boolean state = false;
                               currGammaYesNo.setActive(state);
                               currGammaYesNo.selectValue("estimated");
                               currGammaVal.setActive(state);
                           }
                       else
                           {
                               Boolean state = true;
                               currGammaYesNo.setActive(state);
                               currGammaYesNo.selectValue("estimated");
                               state = false;
                               currGammaVal.setActive(state);
                           }


                    }
                else if("Free Rates".equals(property.getValue())) 
                    {
                        Boolean state = false;
                        currGammaVal.setActive(state);
                        currGammaYesNo.setActive(state);
                    }

                if("Number of rate classes".equals(property.getLabel()))
                    { 
                        if("1".equals(property.getValue()))
                            {
                                Boolean state = false;
                                currGammaYesNo.setActive(state);
                                currGammaVal.setActive(state);
                            }
                        else
                            {

                                AbstractProperty currRASmodel = node.getProperty("Rate variation model");
                                
                                if("Discrete Gamma".equals(currRASmodel.getValue()))
                                    {
                                        Boolean state = true;
                                        currGammaYesNo.setActive(state);
                                        
                                        if("fixed".equals(currGammaYesNo.getValue()))
                                            {
                                                currGammaVal.setActive(state);
                                            }
                                        else
                                            {
                                                state = false;
                                                currGammaVal.setActive(state);
                                            }
                                    }
                            }
                    }

            }

        if("Discrete Gamma node".equals(node.getName()))
            {
                if("estimated".equals(property.getValue())) 
                    {
                        Boolean state = false;
                        currGammaVal.setActive(state);
                    }
                else
                    {
                        Boolean state = true;
                        currGammaVal.setActive(state);
                    }
            }
    }
}
