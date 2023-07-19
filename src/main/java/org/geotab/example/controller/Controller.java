package org.geotab.example.controller;

import com.geotab.api.GeotabApi;

public abstract class Controller {

    protected final GeotabApi geotabApi;

    public Controller(GeotabApi geotabApi) {
        this.geotabApi = geotabApi;
    }

    public abstract void execute();
}
