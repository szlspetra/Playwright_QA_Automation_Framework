package com.qaautomation.api.payloads;

/**
 * Request payload for Employee API operations
 * Represents user data structure for API requests
 * 
 * @author Petra Széles
 * @version 1.0
 */
public class CreateEmployeePayload {
    
    private String name;
    private String salary;
    private String age;
    
    /**
     * Constructor
     */
    public CreateEmployeePayload() {
    }
    
    /**
     * Constructor with parameters
     */
    public CreateEmployeePayload(String name, String salary, String age) {
        this.name = name;
        this.salary = salary;
        this.age = age;
    }
    
    // Getters and Setters
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }


    @Override
    public String toString() {
        return "{" +
                "\"name\":\"" + name + '\"' +
                ",\"salary\":\"" + salary + '\"' +
                ",\"age\":\"" + age + '\"' +
                "}";
    }
}
