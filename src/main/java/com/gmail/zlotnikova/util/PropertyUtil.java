package com.gmail.zlotnikova.util;

import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PropertyUtil {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private static final String CONFIG_PROPERTY_FILE_NAME = "config.properties";
    private static Properties properties = new Properties();

    private static PropertyUtil instance;

    private PropertyUtil() {
    }

    public static PropertyUtil getInstance() {
        if (instance == null) {
            instance = new PropertyUtil();
        }
        return instance;
    }

    public String getProperty(String name) {
        try (
                InputStream propertiesStream =
                        instance.getClass().getClassLoader().getResourceAsStream(CONFIG_PROPERTY_FILE_NAME)
        ) {
            if (propertiesStream != null) {
                properties.load(propertiesStream);
            } else {
                throw new IllegalArgumentException("Config property file not found");
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalArgumentException("Config property file not found");
        }
        return properties.getProperty(name);
    }

}