package com.qaautomation.ui.tests;

import com.qaautomation.base.BaseTest;
import com.qaautomation.ui.pages.SubmitPage;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Test class for Submit functionality
 * Tests various submit scenarios including positive and negative cases
 * 
 * @author Petra Széles
 * @version 1.0
 */
public class SubmitTest extends BaseTest {
    
    /**
     * Test successful submit with valid credentials
     */
    @Test(description = "Test successful submit with valid credentials",
            groups = { "smoke" })
    public void testSuccessfulSubmit() {
        logger.info("Starting test: testSuccessfulSubmit");
        navigateTo(configReader.getBaseUrl());
        // Create SubmitPage object
        SubmitPage submitPage = new SubmitPage(page);
        
        // Perform submit
        submitPage.submit("Test User","testuser@example.com","Test comment");
        
        // Assert - verify all error messages are hidden
        assertTrue(submitPage.isAllErrorMessageHidden(), "Not all error messages are hidden");
        
        logger.info("Test passed: testSuccessfulSubmit");
    }
    
    /**
     * Test submit with invalid email address
     */
    @Test(description = "Test submit with invalid email address",
            groups = { "smoke" })
    public void testSubmitWithInvalidEmail() {
        String expErrorMsg = "Please enter a valid email address";
        logger.info("Starting test: testSubmitWithInvalidEmail");

        navigateTo(configReader.getBaseUrl());

        // Create SubmitPage object
        SubmitPage submitPage = new SubmitPage(page);
        
        // Perform submit with invalid credentials
        submitPage.fillRequiredFields("Test User","testuser@com","Test comment");

        // Assert - verify error message is displayed
        assertTrue(submitPage.isErrorMessageDisplayed(), "Error message should be displayed for invalid credentials");
        String errorMessage = submitPage.getErrorMessage();
        logger.info("Error message displayed: " + errorMessage);
        assertTrue(errorMessage.contains(expErrorMsg), "Expected error message not found: " + expErrorMsg);
        
        logger.info("Test passed: testSubmitWithInvalidEmail");
    }
}
