# Playwright Java QA Automation Framework

A comprehensive, production-ready test automation framework combining **Playwright** and **Java** for UI and API testing. This framework includes best practices, comprehensive logging, CI/CD integration and documentation.

## Framework Features

### Core Capabilities
- **UI Testing**: Playwright-based browser automation with Page Object Model (POM) pattern
- **API Testing**: REST-Assured for RESTful API testing
- **Cross-Browser Support**: Chromium, Firefox, and WebKit
- **Comprehensive Logging**: SLF4J + Logback with rolling file configuration
- **CI/CD Integration**: GitLab CI/CD pipeline with Docker support
- **Test Reporting**: Allure reporting framework
- **Configuration Management**: Externalized configuration via properties files
- **Error Handling**: Robust error handling and wait strategies

### Architecture
```
├── Base Classes: Common setup/teardown and utility inheritance
├── Page Object Model: Encapsulated UI elements and interactions
├── API Specifications: Organized API endpoint definitions
├── Utilities: Reusable helper functions
├── Listeners: Test execution listeners for enhanced reporting
└── CI/CD: Automated test execution pipeline
```

## Prerequisites

- **Java 11** or higher
- **Maven 3.9.0** or higher
- **Git**
- **Docker** (optional, for containerized execution)

## Getting Started

### 1. Clone Repository
```bash
git clone https://github.com/szlspetra/Playwright_QA_Automation_Framework.git
cd playwright_pilot
```

### 2. Install Dependencies
```bash
mvn clean install
```

This command will:
- Download all Maven dependencies
- Install Playwright browsers
- Configure the project structure

### 3. Configuration

Edit `src/test/resources/config.properties`:

```properties
# Application Under Test
app.base.url
api.base.url

# Browser Configuration
browser.type              # chromium, firefox, webkit
browser.headless          # Set to false for headed execution

# Timeouts
wait.timeout              # milliseconds

# Environment
environment               # dev, staging, production
```

### 4. Run Tests

**Run all tests:**
```bash
mvn clean test
```


## Project Structure

```
src/test/java/
├── com/qaautomation/
│   ├── base/
│   │   ├── BaseTest.java              # Abstract base class with lifecycle management
│   │   │
│   ├── ui/
│   │   ├── pages/
│   │   │   ├── SubmitPage.java         # Submit page functionality
│   │   │
│   │   └── tests/
│   │       ├── SubmitTest.java         # Submit test cases
│   │
│   ├── api/
│   │   ├── payloads/
│   │   │   ├── CreateEmployeePayload.java       # Employee request payload
│   │   │
│   │   └── tests/
│   │       ├── EmployeeApiTest.java             # Employee API test cases
│   │
│   └── utils/
│       ├── ConfigReader.java          # Configuration management
│       ├── LoggerUtil.java            # Custom logging wrapper
│       ├── BrowserFactory.java        # Browser instantiation
│       ├── ApiClient.java             # REST-Assured client wrapper
│       └── WaitHelper.java            # Explicit wait utilities
│
src/test/resources/
├── config.properties                  # Configuration file
├── testng.xml                         # TestNG suite configuration
│
├── .gitlab-ci.yml                     # CI/CD pipeline
├── Dockerfile                         # Docker configuration
└── pom.xml                            # Maven dependencies
```



## Logging

The framework uses **SLF4J with Logback** for comprehensive logging:

### Log Levels
- **INFO**: High-level test steps and important operations
- **DEBUG**: Detailed diagnostic information
- **ERROR**: Error messages and stack traces
- **WARN**: Warning messages

### Log Output
- Console output for real-time monitoring
- Rolling file logs in `logs/` directory
- Separate error log file for quick debugging

### Example Log Output
```
2024-01-15 10:30:45.123 [main] INFO  LoginTest - Starting test: testSuccessfulLogin
2024-01-15 10:30:45.456 [main] DEBUG LoginPage - Username entered successfully
2024-01-15 10:30:46.789 [main] INFO  LoginPage - Login completed
```

## CI/CD Pipeline

The framework includes a complete **GitLab CI/CD pipeline** (`.gitlab-ci.yml`):

### Pipeline Stages
1. **build**: Compiles the project with Maven
2. **test**: Executes UI and API tests in Docker container
3. **report**: Generates Allure test reports

### Running in Docker

**Build Docker image:**
```bash
docker build -t playwright-qa:latest .
```

**Run tests in container:**
```bash
docker run --rm -v $(pwd)/results:/app/allure-results playwright-qa:latest
```

### GitLab CI Integration
Push changes to trigger automatic test execution:
```bash
git push origin feature/new-tests
```

Pipeline will automatically:
- Build the project
- Run all tests
- Generate reports
- Publish results

## Test Reporting

### Allure Report

Generate and view Allure report:

```bash
# Generate report
mvn allure:report

# Serve report locally
mvn allure:serve
```

Report includes:
- Test execution summary
- Pass/fail statistics
- Detailed test results
- Logs and attachments
- Trend analysis

### TestNG Reports

TestNG generates XML reports in `target/surefire-reports/`

## Utilities

### ConfigReader
Manages application configuration:
```java
ConfigReader config = new ConfigReader();
String baseUrl = config.getBaseUrl();
int timeout = config.getWaitTimeout();
```

### LoggerUtil
Consistent logging across framework.


### BrowserFactory
Creates browser instances with configuration:
```java
BrowserFactory factory = new BrowserFactory();
Browser browser = factory.createBrowser(playwright, "chromium");
```

### ApiClient
Encapsulates REST-Assured functionality:
```java
ApiClient client = new ApiClient("https://api.example.com");
Response response = client.get("/users/1");
Response response = client.post("/users", payload);
```

### WaitHelper
Explicit waits for elements.

## Best Practices Implemented

1. **Page Object Model**: Centralized element management
2. **Single Responsibility**: Each class has one purpose
3. **DRY Principle**: Reusable utilities and base classes
4. **Logging**: Comprehensive logging for debugging
5. **Error Handling**: Graceful error handling with meaningful messages
6. **Configuration Management**: Externalized configuration
7. **CI/CD Ready**: Docker and GitLab CI integration
8. **Documentation**: Extensive JavaDoc comments

## Troubleshooting

### Browser Not Found
```bash
# Install Playwright browsers
mvn exec:java -Dexec.mainClass="com.microsoft.playwright.CLI" -Dexec.args="install"
```

### Timeout Errors
Increase timeout in `config.properties`:
```properties
wait.timeout=10000  # 10 seconds
```

### Authentication Issues
Add authorization header in `ApiClient`:
```java
public Response getWithAuth(String endpoint, String authToken) {
    // Implementation with Bearer token
}
```

## Dependencies

Key dependencies:
- **Playwright**: Modern browser automation
- **TestNG**: Test framework
- **REST-Assured**: API testing library
- **SLF4J + Logback**: Logging framework
- **Gson**: JSON processing

## Contributing

1. Create feature branch: `git checkout -b feature/new-test`
2. Write tests with descriptive names
3. Follow code style conventions
4. Add logging for test steps
5. Create merge request with description


**Framework Version**: 1.0.0  
**Last Updated**: Dec 2025
**Maintained By**: Széles Petra
