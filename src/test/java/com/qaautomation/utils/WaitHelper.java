package com.qaautomation.utils;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.PlaywrightException;

/**
 * Wait helper utility for handling explicit waits in Playwright.
 * Provides methods for waiting on elements, conditions, and transitions.
 * 
 * @author QA Framework Team
 * @version 1.0
 */
public class WaitHelper {
    
    private static final LoggerUtil logger = LoggerUtil.getLogger(WaitHelper.class);
    private static final int DEFAULT_TIMEOUT = 30000; // 30 seconds in milliseconds
    
    /**
     * Wait for an element to be visible
     * @param page Playwright page instance
     * @param selector CSS selector of element
     */
    public static void waitForElementVisible(Page page, String selector) {
        logger.info("Waiting for element to be visible: " + selector);
        try {
            page.waitForSelector(selector, new Page.WaitForSelectorOptions().setTimeout(DEFAULT_TIMEOUT));
            logger.debug("Element is visible: " + selector);
        } catch (PlaywrightException e) {
            logger.error("Element not visible within timeout: " + selector, e);
            throw e;
        }
    }
    
    /**
     * Wait for an element to be hidden
     * @param page Playwright page instance
     * @param selector CSS selector of element
     */
    public static void waitForElementHidden(Page page, String selector) {
        logger.info("Waiting for element to be hidden: " + selector);
        try {
            page.waitForSelector(selector + " >> nth=0 >> visible=false", 
                    new Page.WaitForSelectorOptions().setTimeout(DEFAULT_TIMEOUT));
            logger.debug("Element is hidden: " + selector);
        } catch (PlaywrightException e) {
            logger.error("Element did not hide within timeout: " + selector, e);
            throw e;
        }
    }
    
    /**
     * Wait for navigation to complete
     * @param page Playwright page instance
     */
    public static void waitForNavigation(Page page) {
        logger.info("Waiting for page navigation to complete");
        try {
            page.waitForLoadState();
            logger.debug("Page navigation completed");
        } catch (PlaywrightException e) {
            logger.error("Navigation did not complete within timeout", e);
            throw e;
        }
    }
    
    /**
     * Wait for specific number of milliseconds
     * @param milliseconds Time to wait
     */
    public static void waitForMilliseconds(long milliseconds) {
        logger.debug("Waiting for " + milliseconds + " milliseconds");
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            logger.error("Wait interrupted", e);
            Thread.currentThread().interrupt();
        }
    }
}
