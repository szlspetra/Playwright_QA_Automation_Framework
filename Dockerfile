# Multi-stage Dockerfile for Playwright Java QA Framework

# Build stage
FROM maven:3.9.4-eclipse-temurin-11 AS builder

WORKDIR /app

# Copy project files
COPY . .

# Build the project
RUN mvn clean package -DskipTests

# Runtime stage
FROM mcr.microsoft.com/playwright/java:v1.45.0

WORKDIR /app

# Install additional tools
RUN apt-get update && \
    apt-get install -y \
    maven \
    git \
    curl \
    && rm -rf /var/lib/apt/lists/*

# Copy built artifacts from builder stage
COPY --from=builder /app/target ./target
COPY --from=builder /app/pom.xml ./pom.xml
COPY --from=builder /app/src ./src

# Create logs directory
RUN mkdir -p logs allure-results

# Set environment variables
ENV MAVEN_HOME=/usr/share/maven
ENV PATH=$MAVEN_HOME/bin:$PATH
ENV PLAYWRIGHT_JAVA_BIN_PATH=/ms-playwright

# Health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=5s --retries=3 \
    CMD mvn --version

# Default command - run tests
CMD ["mvn", "clean", "test", "-DsuiteXmlFile=src/test/java/resources/testng.xml"]
