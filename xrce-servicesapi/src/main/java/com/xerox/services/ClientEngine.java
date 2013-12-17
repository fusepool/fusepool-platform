package com.xerox.services;

/**
 *
 * @author aczerny
 */
public interface ClientEngine {
    /**
     * We just test if Felix likes it more when services come from an interface
     */
    public RestEngine getPull();
    
    public RestEngine getPush();
}
