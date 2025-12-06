package com.qaautomation.ui.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.qaautomation.utils.LoggerUtil;
import com.qaautomation.utils.WaitHelper;

import java.util.List;

/**
 * Page Object Model for Login Page
 * Encapsulates all elements and interactions for the submit page
 * 
 * @author QA Framework Team
 * @version 1.0
 */
public class SubmitPage {
    
    private static final LoggerUtil logger = LoggerUtil.getLogger(SubmitPage.class);
    private final Page page;
    
    // Locators
    private static final String USERNAME_INPUT = "input[id*='name']";
    private static final String EMAIL_INPUT = "input[id*='email']";
    private static final String COMMENT_INPUT = "textarea[id*='comment']";
    private static final String SUBMIT_BUTTON = "button:has-text('Submit')";
    private static final String EMAIL_ERROR_MESSAGE = "span[id*='email-error-message']";
    private static final String ERROR_MESSAGE = "span[id*='error-message']";
    private static final String SUCCESS_MESSAGE = ".success-message";
    
    /**
     * Constructor
     * @param page Playwright page instance
     */
    public SubmitPage(Page page) {
        this.page = page;
        logger.info("SubmitPage initialized");
    }
    
    /**
     * Enter username in username input field
     * @param username Username to enter
     */
    public void enterUsername(String username) {
        logger.info("Entering username: " + username);
        WaitHelper.waitForElementVisible(page, USERNAME_INPUT);
        page.fill(USERNAME_INPUT, username);
        logger.debug("Username entered successfully");
    }
    
    /**
     * Enter email in email input field
     * @param email Email to enter
     */
    public void enterEmailAdress(String email) {
        logger.info("Entering email");
        WaitHelper.waitForElementVisible(page, EMAIL_INPUT);
        page.fill(EMAIL_INPUT, email);
        logger.debug("Email entered successfully");
    }

    /**
     * Enter comment in comment input field
     * @param comment Email to enter
     */
    public void enterComment(String comment) {
        logger.info("Entering comment");
       // page.locator(COMMENT_INPUT).scrollIntoViewIfNeeded();
        WaitHelper.waitForElementVisible(page, COMMENT_INPUT);
        page.fill(COMMENT_INPUT, comment);
        logger.debug("Comment entered successfully");
    }
    
    /**
     * Click submit button
     */
    public void clickSubmitButton() {
        logger.info("Clicking submit button");
        WaitHelper.waitForElementVisible(page, SUBMIT_BUTTON);
        page.click(SUBMIT_BUTTON);
        logger.debug("Submit button clicked");
    }
    /**
     * Perform submit with credentials
     * @param username Username
     * @param email Email
     */
    public void fillRequiredFields(String username, String email, String comment) {
        logger.info("Performing fill the required fields");
        enterUsername(username);
        enterEmailAdress(email);
        enterComment(comment);
        logger.info("Fill completed");
    }
    /**
     * Perform submit with credentials
     * @param username Username
     * @param email Email
     */
    public void submit(String username, String email, String comment) {
        logger.info("Performing submit");
        fillRequiredFields(username,email,comment);
        clickSubmitButton();
        WaitHelper.waitForNavigation(page);
        logger.info("Submit completed");
    }
    
    /**
     * Get error message text
     * @return Error message text
     */
    public String getErrorMessage() {
        logger.info("Getting error message");
        String errorText = page.textContent(EMAIL_ERROR_MESSAGE);
        logger.debug("Error message: " + errorText);
        return errorText;
    }
    
    /**
     * Check if error message is displayed
     * @return true if error message is visible, false otherwise
     */
    public boolean isErrorMessageDisplayed() {
        logger.info("Checking if error message is displayed");
        try {
            WaitHelper.waitForElementVisible(page, EMAIL_ERROR_MESSAGE);
            logger.debug("Error message is displayed");
            return true;
        } catch (Exception e) {
            logger.debug("Error message is not displayed");
            return false;
        }
    }

    public boolean isAllErrorMessageHidden(){
        List<Locator> errorMessages = page.locator(ERROR_MESSAGE).all();
        for (Locator message : errorMessages) {
            if(message.isVisible()){
                return false;
            }
        }
        return true;
    }

}
