package dev.asjordi;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Props {

    private static Props INSTANCE;
    private Properties properties;
    private static final String PROPERTIES_FILE = "api.properties";

    private Props() {
        properties = new Properties();
        loadProperties();
    }

    public static Props getInstance() {
        if (INSTANCE == null) INSTANCE = new Props();
        return INSTANCE;
    }

    private void loadProperties() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
            if (input != null) {
                properties.load(input);
            } else {
                System.err.println("Properties file not found in resources: " + PROPERTIES_FILE);
            }
        } catch (IOException e) {
            System.err.println("Error loading properties file: " + e.getMessage());
        }
    }

    public Properties getProperties() {
        return properties;
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

}
