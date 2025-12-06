package com.qaautomation.base;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.qaautomation.utils.BrowserFactory;
import com.qaautomation.utils.ConfigReader;
import com.qaautomation.utils.LoggerUtil;
import com.qaautomation.utils.WaitHelper;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.util.regex.Pattern;

/**
 * Abstract base class for all tests providing common setup and teardown operations.
 * Manages Playwright browser lifecycle and logging configuration.
 * 
 * @author QA Framework Team
 * @version 1.0
 */
public abstract class BaseTest {
    
    protected static final LoggerUtil logger = LoggerUtil.getLogger(BaseTest.class);
    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext browserContext;
    protected Page page;
    protected ConfigReader configReader;
    protected BrowserFactory browserFactory;


    private static final String CONSENT_BUTTON = "button:has-text('Consent')";
    
    /**
     * Setup method executed before each test method.
     * Initializes Playwright, browser, and logging configuration.
     */
    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        try {
            logger.info("========== TEST SETUP START ==========");
            
            // Initialize configuration reader
            configReader = new ConfigReader();
            logger.info("Configuration loaded successfully");
            
            // Initialize browser factory
            browserFactory = new BrowserFactory();
            
            // Launch browser
            String browserType = configReader.getBrowserType();
            logger.info("Launching browser: " + browserType);
            
            playwright = Playwright.create();
            browser = browserFactory.createBrowser(playwright, browserType);
            browserContext = browser.newContext(new Browser.NewContextOptions()
                    .setLocale("en-US"));

            page = browserContext.newPage();
            
            logger.info("Browser context created successfully");
            logger.info("========== TEST SETUP END ==========");
            
        } catch (Exception e) {
            logger.error("Error during test setup", e);
            throw new RuntimeException("Failed to setup test environment", e);
        }
    }
    
    /**
     * Teardown method executed after each test method.
     * Closes browser context, browser, and Playwright instance.
     */
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        try {
            logger.info("========== TEST TEARDOWN START ==========");
            
            if (page != null) {
                page.close();
                logger.info("Page closed");
            }
            
            if (browserContext != null) {
                browserContext.close();
                logger.info("Browser context closed");
            }
            
            if (browser != null) {
                browser.close();
                logger.info("Browser closed");
            }
            
            if (playwright != null) {
                playwright.close();
                logger.info("Playwright closed");
            }
            
            logger.info("========== TEST TEARDOWN END ==========");
            
        } catch (Exception e) {
            logger.error("Error during test teardown", e);
        }
    }
    
    /**
     * Navigate to a specific URL
     * @param url The URL to navigate to
     */
    protected void navigateTo(String url) {
        logger.info("Navigating to URL: " + url);
        page.navigate(url);
        clickConsentButton(page);
        logger.info("Navigation completed");
    }

    protected void clickConsentButton(Page page){
        try {
            page.click(CONSENT_BUTTON);
            logger.info("Consent button clicked");
        } catch (Exception e) {
            logger.info("Consent button not found: " + e.toString());
        }
    }
}
