package com.qaautomation.api.tests;

import com.qaautomation.api.payloads.CreateEmployeePayload;
import com.qaautomation.utils.ApiClient;
import com.qaautomation.utils.ConfigReader;
import com.qaautomation.utils.LoggerUtil;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.apache.http.conn.params.ConnManagerParams.setTimeout;
import static org.testng.Assert.*;

/**
 * API Test class for Employee endpoints
 * Tests CRUD operations on Employee API
 * 
 * @author QA Framework Team
 * @version 1.0
 */
public class EmployeeApiTest {
    
    private static final LoggerUtil logger = LoggerUtil.getLogger(EmployeeApiTest.class);
    private ApiClient apiClient;
    private ConfigReader configReader;
    private String userId;

    private String name1 = "Test User";
    private String salary1 = "1001";
    private String age1 = "99";

    private String name2 = "Modified User";
    private String salary2 = "1002";
    private String age2 = "100";

    /**
     * Setup method before test class execution
     */
    @BeforeTest(groups = {"smoke"})
    public void setUp() {
        logger.info("========== API TEST SETUP START ==========");
        configReader = new ConfigReader();
        apiClient = new ApiClient(configReader.getApiBaseUrl());
        logger.info("API Client initialized with base URL: " + configReader.getApiBaseUrl());
        logger.info("========== API TEST SETUP END ==========");
    }
    
    /**
     * Test creating a new user via API
     */
    @Test(description = "Test creating a new user via API",
            groups = { "smoke" })
    public void testCreateEmployee() {
        logger.info("Starting test: testCreateEmployee");
        
        // Create user payload
        CreateEmployeePayload userPayload = new CreateEmployeePayload(
                name1,
                salary1,
                age1
        );
        
        logger.info("Employee payload created: " + userPayload.toString());
        
        // Send POST request
        Response response = apiClient.post("/api/v1/create", userPayload);
        
        // Assertions
        assertEquals(response.getStatusCode(), 200, "Status code should be 200 for successful user creation");
        assertTrue(response.getBody().asString().contains("success"),
                "Response should contain 'success'");
        
        // Extract userId for later use
        userId = response.jsonPath().getString("data.id");
        logger.info("Employee created with ID: " + userId);
        
        logger.info("Test passed: testCreateEmployee");
    }
    
    /**
     * Test retrieving user details via API
     */
    @Test(description = "Test retrieving user details via API", dependsOnMethods = "testCreateEmployee",
            groups = { "smoke" })
    public void testGetEmployee() throws InterruptedException {
        logger.info("Starting test: testGetEmployee");
        logger.info("Retrieving user with ID: " + userId);
        logger.info("60 seconds delay due to 429 Too Many Requests error");
        Thread.sleep(60000);
        // Send GET request
        Response response = apiClient.get("/api/v1/employee/" + userId);

        // Assertions
        assertEquals(response.getStatusCode(), 200, "Status code should be 200 for successful retrieval");
        /*
        assertEquals(response.jsonPath().getString("data.employee_name"), name1,
                "Employee name should match the created name; Excepted: " + name1 + "; Result: " + response.jsonPath().getString("data.employee_name"));
        assertEquals(response.jsonPath().getString("data.employee_salary"), salary1,
                "Salary should match; Excepted: " + salary1 + "; Result: " + response.jsonPath().getString("data.employee_salary"));
        assertEquals(response.jsonPath().getString("data.employee_age"), age1,
                "Salary should match; Excepted: " + age1 + "; Result: " + response.jsonPath().getString("data.employee_age"));
        */
        logger.info("Test passed: testGetEmployee");
    }
    
    /**
     * Test updating user details via API
     */
    @Test(description = "Test updating user details via API", dependsOnMethods = "testGetEmployee",
            groups = { "smoke" })
    public void testUpdateEmployee() throws InterruptedException {
        logger.info("Starting test: testUpdateEmployee");
        logger.info("60 seconds delay due to 429 Too Many Requests error");
        Thread.sleep(60000);
        // Create updated payload
        CreateEmployeePayload updatedPayload = new CreateEmployeePayload();
        updatedPayload.setName(name2);
        updatedPayload.setSalary(salary2);
        updatedPayload.setAge(age2);
        
        logger.info("Updated payload created: " + updatedPayload.toString());
        
        // Send PUT request
        Response response = apiClient.put("/api/v1/update/" + userId, updatedPayload);
        
        // Assertions
        assertEquals(response.getStatusCode(), 200, "Status code should be 200 for successful retrieval");
        assertEquals(response.jsonPath().getString("data.name"), name2,
                "Employee name should match the created name; Excepted: " + name2 + "; Result: " + response.jsonPath().getString("data.name"));
        assertEquals(response.jsonPath().getString("data.salary"), salary2,
                "Salary should match; Excepted: " + salary2 + "; Result: " + response.jsonPath().getString("data.salary"));
        assertEquals(response.jsonPath().getString("data.age"), age2,
                "Salary should match; Excepted: " + age2 + "; Result: " + response.jsonPath().getString("data.age"));

        logger.info("Test passed: testUpdateEmployee");
    }

    /**
     * Test deleting a user via API
     */
    @Test(description = "Test deleting a user via API", dependsOnMethods = "testUpdateEmployee",
            groups = { "smoke" })
    public void testDeleteEmployee() throws InterruptedException {
        logger.info("Starting test: testDeleteEmployee");
        logger.info("Deleting user with ID: " + userId);
        logger.info("60 seconds delay due to 429 Too Many Requests error");
        Thread.sleep(60000);
        // Send DELETE request
        Response response = apiClient.delete("/api/v1/delete/" + userId);
        
        // Assertions
        assertEquals(response.getStatusCode(), 200, "Status code should be 200 for successful deletion");
        
        logger.info("Test passed: testDeleteEmployee");
    }
    
    /**
     * Test error handling - get non-existent user
     */
    @Test(description = "Test error handling for non-existent user")
    public void testGetNonExistentEmployee() {
        logger.info("Starting test: testGetNonExistentEmployee");
        
        // Send GET request for non-existent user
        Response response = apiClient.get("/api/users/999999");
        
        // Assertions
        assertEquals(response.getStatusCode(), 404, "Status code should be 404 for non-existent user");
        assertTrue(response.getBody().asString().contains("not found") || 
                response.getBody().asString().contains("Not Found"), 
                "Response should indicate user not found");
        
        logger.info("Test passed: testGetNonExistentEmployee");
    }
}
