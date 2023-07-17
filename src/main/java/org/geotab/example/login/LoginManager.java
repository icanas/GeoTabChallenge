package org.geotab.example.login;

import com.geotab.api.Api;
import com.geotab.api.GeotabApi;
import com.geotab.model.login.Credentials;
import org.geotab.example.constants.ConnectionConstants;
import org.geotab.example.utils.ConnectionUtils;

import java.io.IOException;

import static com.geotab.http.invoker.ServerInvoker.DEFAULT_TIMEOUT;

public class LoginManager {

    public Api login() throws IOException {
        Credentials credentials = ConnectionUtils.readCredentialsFromYaml(ConnectionConstants.CREDENTIALS_FILENAME);

        Api api = new GeotabApi(credentials, ConnectionConstants.SERVER_URL, DEFAULT_TIMEOUT);
        api.authenticate();
        return api;
    }
}
