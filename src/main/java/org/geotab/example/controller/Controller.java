package org.geotab.example.controller;

import com.geotab.api.GeotabApi;

/**
 * An abstract class representing a controller
 */
public abstract class Controller {

    protected final GeotabApi geotabApi;

    /**
     * Constructs a Controller instance with the specified GeotabApi.
     *
     * @param geotabApi The GeotabApi instance used for executing tasks.
     */
    public Controller(GeotabApi geotabApi) {
        this.geotabApi = geotabApi;
    }

    /**
     * The abstract method to be implemented by subclasses.
     * It defines the task that the controller will execute.
     */
    public abstract void execute();
}
