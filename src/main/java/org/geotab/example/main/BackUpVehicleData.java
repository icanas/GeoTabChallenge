package org.geotab.example.main;

import org.geotab.example.controller.ConsoleController;
import org.geotab.example.login.LoginManager;

public class BackUpVehicleData {

    public static void main(String[] args) {
        LoginManager loginManager = new LoginManager();
        ConsoleController consoleController = new ConsoleController(loginManager);
        consoleController.execute();
    }
}