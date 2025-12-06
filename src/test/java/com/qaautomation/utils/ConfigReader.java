package com.qaautomation.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Configuration reader to manage application and test environment properties.
 * Reads from config.properties file and provides getter methods for configuration values.
 * 
 * @author QA Framework Team
 * @version 1.0
 */
public class ConfigReader {
    
    private static final LoggerUtil logger = LoggerUtil.getLogger(ConfigReader.class);
    private Properties properties;
    private static final String CONFIG_PATH = "src/test/java/resources/config.properties";
    
    /**
     * Constructor - loads configuration from properties file
     */
    public ConfigReader() {
        properties = new Properties();
        loadProperties();
    }
    
    /**
     * Load properties from config file
     */
    private void loadProperties() {
        try {
            FileInputStream fileInputStream = new FileInputStream(CONFIG_PATH);
            properties.load(fileInputStream);
            fileInputStream.close();
            logger.info("Configuration properties loaded from: " + CONFIG_PATH);
        } catch (IOException e) {
            logger.error("Error loading configuration file: " + CONFIG_PATH, e);
            throw new RuntimeException("Failed to load configuration properties", e);
        }
    }
    
    /**
     * Get browser type from configuration
     * @return Browser type (chromium, firefox, webkit)
     */
    public String getBrowserType() {
        String browserType = properties.getProperty("browser.type", "chromium");
        logger.debug("Browser type: " + browserType);
        return browserType;
    }
    
    /**
     * Get base URL for application under test
     * @return Base URL
     */
    public String getBaseUrl() {
        String baseUrl = properties.getProperty("app.base.url");
        logger.debug("Base URL: " + baseUrl);
        return baseUrl;
    }
    
    /**
     * Get API base URL
     * @return API base URL
     */
    public String getApiBaseUrl() {
        String apiUrl = properties.getProperty("api.base.url");
        logger.info("API Base URL: " + apiUrl);
        return apiUrl;
    }
    
    /**
     * Check if headless mode is enabled
     * @return true if headless, false otherwise
     */
    public boolean isHeadless() {
        String headless = properties.getProperty("browser.headless", "true");
        logger.debug("Headless mode: " + headless);
        return Boolean.parseBoolean(headless);
    }
    
    /**
     * Get default wait timeout in milliseconds
     * @return Timeout in milliseconds
     */
    public int getWaitTimeout() {
        String timeout = properties.getProperty("wait.timeout", "5000");
        logger.debug("Wait timeout: " + timeout + "ms");
        return Integer.parseInt(timeout);
    }
    
    /**
     * Get environment name
     * @return Environment (dev, staging, production)
     */
    public String getEnvironment() {
        String env = properties.getProperty("environment", "staging");
        logger.debug("Environment: " + env);
        return env;
    }
    
    /**
     * Get property by key with default value
     * @param key Property key
     * @param defaultValue Default value if key not found
     * @return Property value or default
     */
    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
}
