package com.properties;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ApplicationProperties {

    private final static Properties props = loadProperties("application.properties");

    public static void loadApplicationProperties() {
        loadProperties("application.properties");
    }

    public static Properties loadProperties(String filepath) {
        // Carregue o arquivo de propriedades
        try (InputStream input = new FileInputStream(filepath)) {
            Properties properties = new Properties();
            properties.load(input);
            return properties;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @return Properties return the props
     */
    public static String getProp(String key) {
        return props.getProperty(key);
    }
}