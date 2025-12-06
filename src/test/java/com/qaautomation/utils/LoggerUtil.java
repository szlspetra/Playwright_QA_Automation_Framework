package com.qaautomation.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Custom logging utility wrapper around SLF4J for consistent logging throughout the framework.
 * Provides convenient logging methods with automatic class context.
 * 
 * @author QA Framework Team
 * @version 1.0
 */
public class LoggerUtil {
    
    private final Logger logger;
    
    /**
     * Constructor - private to enforce singleton pattern per class
     * @param clazz The class for which logger is created
     */
    private LoggerUtil(Class<?> clazz) {
        this.logger = LoggerFactory.getLogger(clazz);
    }
    
    /**
     * Get logger instance for a specific class
     * @param clazz The class to get logger for
     * @return LoggerUtil instance
     */
    public static LoggerUtil getLogger(Class<?> clazz) {
        return new LoggerUtil(clazz);
    }
    
    /**
     * Log info level message
     * @param message The message to log
     */
    public void info(String message) {
        logger.info(message);
    }
    
    /**
     * Log debug level message
     * @param message The message to log
     */
    public void debug(String message) {
        logger.debug(message);
    }
    
    /**
     * Log warning level message
     * @param message The message to log
     */
    public void warn(String message) {
        logger.warn(message);
    }
    
    /**
     * Log error level message with exception
     * @param message The message to log
     * @param throwable The exception to log
     */
    public void error(String message, Throwable throwable) {
        logger.error(message, throwable);
    }
    
    /**
     * Log error level message
     * @param message The message to log
     */
    public void error(String message) {
        logger.error(message);
    }
    
    /**
     * Log trace level message
     * @param message The message to log
     */
    public void trace(String message) {
        logger.trace(message);
    }
}
