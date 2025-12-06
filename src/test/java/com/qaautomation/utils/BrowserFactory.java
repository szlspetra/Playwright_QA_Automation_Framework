package com.qaautomation.utils;

import com.microsoft.playwright.*;

import java.util.List;

/**
 * Browser factory for creating Playwright browser instances.
 * Supports chromium, firefox, and webkit browsers with headless mode configuration.
 * 
 * @author QA Framework Team
 * @version 1.0
 */
public class BrowserFactory {
    
    private static final LoggerUtil logger = LoggerUtil.getLogger(BrowserFactory.class);
    
    /**
     * Create browser instance based on specified type
     * @param playwright Playwright instance
     * @param browserType Type of browser (chromium, firefox, webkit)
     * @return Browser instance
     */
    public Browser createBrowser(Playwright playwright, String browserType) {
        logger.info("Creating browser of type: " + browserType);
        
        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions();
        
        // Set headless mode
        ConfigReader config = new ConfigReader();
        launchOptions.setHeadless(config.isHeadless());
        logger.debug("Browser headless mode: " + config.isHeadless());
        
        // Set additional browser options
        launchOptions.setSlowMo(100); // Slow down actions for visibility during debugging
        launchOptions.setArgs(List.of(
                "--disable-blink-features=AutomationControlled"
        ));
        
        Browser browser;
        
        switch (browserType.toLowerCase()) {
            case "firefox":
                browser = playwright.firefox().launch(launchOptions);
                logger.info("Firefox browser launched successfully");
                break;
            case "webkit":
                browser = playwright.webkit().launch(launchOptions);
                logger.info("WebKit browser launched successfully");
                break;
            case "chromium":
            default:
                browser = playwright.chromium().launch(launchOptions);
                logger.info("Chromium browser launched successfully");
                break;
        }
        
        return browser;
    }
    
    /**
     * Create browser context with viewport settings
     * @param browser Browser instance
     * @return BrowserContext with configured viewport
     */
    public BrowserContext createBrowserContext(Browser browser) {
        logger.info("Creating browser context");
        
        Browser.NewContextOptions contextOptions = new Browser.NewContextOptions();
        contextOptions.setViewportSize(1920, 1080);
        
        BrowserContext context = browser.newContext(contextOptions);
        logger.info("Browser context created with viewport: 1920x1080");
        
        return context;
    }
}
