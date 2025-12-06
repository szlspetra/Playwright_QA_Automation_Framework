package com.qaautomation.utils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

/**
 * API client for handling REST API requests and responses.
 * Encapsulates REST-Assured functionality for API testing.
 * 
 * @author QA Framework Team
 * @version 1.0
 */
public class ApiClient {
    
    private static final LoggerUtil logger = LoggerUtil.getLogger(ApiClient.class);
    private String baseUrl;
    
    /**
     * Constructor to initialize API client with base URL
     * @param baseUrl The base URL for API requests
     */
    public ApiClient(String baseUrl) {
        this.baseUrl = baseUrl;
        RestAssured.baseURI = baseUrl;
        logger.info("API Client initialized with base URL: " + baseUrl);
    }
    
    /**
     * Perform GET request
     * @param endpoint API endpoint
     * @return Response object
     */
    public Response get(String endpoint) {
        logger.info("Performing GET request to: " + endpoint);
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get(endpoint)
                .then()
                .log().all()
                .extract().response();
        
        logger.info("GET Response Status Code: " + response.getStatusCode());
        return response;
    }
    
    /**
     * Perform POST request with JSON body
     * @param endpoint API endpoint
     * @param payload Request body as object
     * @return Response object
     */
    public Response post(String endpoint, Object payload) {
        logger.info("Performing POST request to: " + endpoint);
        logger.debug("Request payload: " + payload.toString());
        
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post(endpoint)
                .then()
                .log().all()
                .extract().response();
        
        logger.info("POST Response Status Code: " + response.getStatusCode());
        return response;
    }
    
    /**
     * Perform PUT request with JSON body
     * @param endpoint API endpoint
     * @param payload Request body as object
     * @return Response object
     */
    public Response put(String endpoint, Object payload) {
        logger.info("Performing PUT request to: " + endpoint);
        logger.debug("Request payload: " + payload.toString());
        
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .put(endpoint)
                .then()
                .log().all()
                .extract().response();
        
        logger.info("PUT Response Status Code: " + response.getStatusCode());
        return response;
    }
    
    /**
     * Perform DELETE request
     * @param endpoint API endpoint
     * @return Response object
     */
    public Response delete(String endpoint) {
        logger.info("Performing DELETE request to: " + endpoint);
        
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .delete(endpoint)
                .then()
                .log().all()
                .extract().response();
        
        logger.info("DELETE Response Status Code: " + response.getStatusCode());
        return response;
    }
    
    /**
     * Perform GET request with specific authorization header
     * @param endpoint API endpoint
     * @param authToken Authorization token
     * @return Response object
     */
    public Response getWithAuth(String endpoint, String authToken) {
        logger.info("Performing GET request with authorization to: " + endpoint);
        
        Response response = RestAssured.given()
                .header("Authorization", "Bearer " + authToken)
                .contentType(ContentType.JSON)
                .when()
                .get(endpoint)
                .then()
                .log().all()
                .extract().response();
        
        logger.info("GET with Auth Response Status Code: " + response.getStatusCode());
        return response;
    }
}
