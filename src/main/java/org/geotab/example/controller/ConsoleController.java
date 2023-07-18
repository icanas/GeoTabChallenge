package org.geotab.example.controller;

import org.geotab.example.login.LoginManager;

public class ConsoleController extends Controller {

    public ConsoleController(LoginManager loginManager){
        super(loginManager.getGeotabApi());
    }

    @Override
    public void execute() {

    }
}
