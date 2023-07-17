package org.geotab.example.main;

import com.geotab.api.Api;
import org.geotab.example.login.LoginManager;

import java.io.IOException;

public class BackUpVehicleData {
    public static void main(String[] args) throws IOException {

        Api api = new LoginManager().login();

    }
}