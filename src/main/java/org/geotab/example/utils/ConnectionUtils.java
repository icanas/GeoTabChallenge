package org.geotab.example.utils;

import com.geotab.model.login.Credentials;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;

public class ConnectionUtils {

    public static Credentials readCredentialsFromYaml(String fileName) throws IOException {
        try (InputStream inputStream = ConnectionUtils.class.getResourceAsStream("/" + fileName)) {
            Yaml yaml = new Yaml();
            CredentialsWrapper wrapper = yaml.loadAs(inputStream, CredentialsWrapper.class);
            return wrapper.getCredentials();
        }
    }

    private static class CredentialsWrapper {
        public String database;
        public String userName;
        public String password;

        public Credentials getCredentials() {
            return Credentials.builder()
                    .database(database)
                    .userName(userName)
                    .password(password)
                    .build();
        }
    }

}
