package org.geotab.example.login;

import com.geotab.api.GeotabApi;
import com.geotab.model.login.Credentials;
import org.geotab.example.constants.ConnectionConstants;
import org.geotab.example.utils.ConnectionUtils;

import java.io.IOException;

import static com.geotab.http.invoker.ServerInvoker.DEFAULT_TIMEOUT;

/*
 * The LoginManager class is responsible for handling the login process to the Geotab API.
 * It uses the credentials stored in the credentials.yml file to authenticate and establish a connection to the API server.
 */
public class LoginManager  {

    private final GeotabApi geotabApi;

    /**
     * Constructor for LoginManager class. Reads the Geotab API credentials from the credentials.yml file,
     * initializes the GeotabApi object with the specified server URL and default timeout, and authenticates the API.
     */
    public LoginManager() {
        Credentials credentials = ConnectionUtils.readCredentialsFromYaml(ConnectionConstants.CREDENTIALS_FILENAME);
        GeotabApi api = new GeotabApi(credentials, ConnectionConstants.SERVER_URL, DEFAULT_TIMEOUT);
        api.authenticate();
        this.geotabApi = api;
    }

    /**
     * Constructor for LoginManager class with custom credentials, server URL, and timeout values.
     * Initializes the GeotabApi object with the provided credentials, server URL, and timeout,
     * and authenticates the API.
     *
     * @param credentials The Geotab API credentials.
     * @param serverUrl   The Geotab API server URL.
     * @param timeout     The timeout value for API requests.
     */
    public LoginManager(Credentials credentials, String serverUrl, int timeout) {
        GeotabApi api = new GeotabApi(credentials, serverUrl, timeout);
        api.authenticate();
        this.geotabApi = api;
    }

    /**
     * Get the GeotabApi instance associated with this LoginManager.
     *
     * @return The GeotabApi instance.
     */
    public GeotabApi getGeotabApi(){
        return this.geotabApi;
    }
}
