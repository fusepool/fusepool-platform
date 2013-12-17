package com.xerox.services;

/**
 *
 * @author aczerny
 */
public interface HubEngine {
    /**
     * We just test if Felix likes it more when services come from an interface
     */
    void register(LUPEngine p);
    
    void unregister(LUPEngine p);
}
