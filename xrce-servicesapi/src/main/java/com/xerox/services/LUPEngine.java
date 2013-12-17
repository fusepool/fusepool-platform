package com.xerox.services;

import org.apache.clerezza.rdf.core.event.FilterTriple;
import org.apache.clerezza.rdf.core.event.GraphListener;

/**
 *
 * @author aczerny
 */
public interface LUPEngine {
    
    String PROBE_NAME = "com.xerox.probe";
    
    /**
     * @return the name of the class implementing this interface.
     */
    public String getName();
    
    /**
     * @return a short description of the LUP module so that it can be displayed.
     */
    public String getDescription();
    
    /**
     * Basically used in the OSGi prediction component to plug a listener to the 
     * Annostore.
     * @return the reference of the engine's graphListener implementation.
     */
    public GraphListener getListener();
    
    /**
     * @return the filter that will be used when plugging the listener to the
     * Annostore.
     */
    public FilterTriple getFilter();
    
    /**
     * @return the listener's delay, which represents the period it remains
     * inactive.
     */
    public long getDelay();
    
    /**
     * Method used to communicate with learning servers (basically openXerox).
     */
    public void updateModels();
    
    /**
     * These methods should be used at activation and deactivation respectively
     * to ensure the consistence of the learning model when platform reboot.
     */
    public void save();
    public void load();
    
    /**
     * Prediction method.
     */
    public String predict(String param);
}
