package org.geotab.example.utils;

import com.geotab.model.login.Credentials;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;

public class ConnectionUtils {

    /**
     * Reads credentials from a YAML file
     *
     * @param fileName The name of the YAML file to read credentials from
     * @return Credentials object containing database, userName, and password
     */
    public static Credentials readCredentialsFromYaml(String fileName) {
        try (InputStream inputStream = ConnectionUtils.class.getResourceAsStream("/" + fileName)) {
            Yaml yaml = new Yaml();
            CredentialsWrapper wrapper = yaml.loadAs(inputStream, CredentialsWrapper.class);
            return wrapper.toCredentials();
        } catch (IOException e) {
            System.err.println("Error while reading from YAML file: " + e.getMessage());
        }
        return null;
    }

    /**
     * Inner class to hold the credentials read from the YAML file
     */
    private static class CredentialsWrapper {
        public String database;
        public String userName;
        public String password;

        /**
         * Converts the credentials from the wrapper to Credentials
         *
         * @return Credentials containing database, userName, and password
         */
        public Credentials toCredentials() {
            return Credentials.builder()
                    .database(database)
                    .userName(userName)
                    .password(password)
                    .build();
        }
    }
}
