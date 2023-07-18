package org.geotab.example.login;

import com.geotab.api.GeotabApi;
import com.geotab.model.login.Credentials;
import org.geotab.example.constants.ConnectionConstants;
import org.geotab.example.utils.ConnectionUtils;

import java.io.IOException;

import static com.geotab.http.invoker.ServerInvoker.DEFAULT_TIMEOUT;

public class LoginManager  {

    private final GeotabApi geotabApi;

    public LoginManager() throws IOException {
        Credentials credentials = ConnectionUtils.readCredentialsFromYaml(ConnectionConstants.CREDENTIALS_FILENAME);
        GeotabApi api = new GeotabApi(credentials, ConnectionConstants.SERVER_URL, DEFAULT_TIMEOUT);
        api.authenticate();
        this.geotabApi = api;
    }

    public LoginManager(Credentials credentials, String serverUrl, int timeout) throws IOException {
        GeotabApi api = new GeotabApi(credentials, serverUrl, timeout);
        api.authenticate();
        this.geotabApi = api;
    }

    public GeotabApi getGeotabApi(){
        return this.geotabApi;
    }
}
