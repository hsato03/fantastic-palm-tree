package br.com.fantasticpalmtree.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {
    private static ConfigLoader instance;
    private Properties properties;

    private ConfigLoader() {
        properties = new Properties();
        try (InputStream input = new FileInputStream(PropertyConstants.FILE_PATH)) {
            properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static synchronized ConfigLoader getInstance() {
        if (instance == null) {
            instance = new ConfigLoader();
        }
        return instance;
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
