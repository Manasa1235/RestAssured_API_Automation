# API Automation Framework

## Overview
This project is an API test automation framework built using:
- Java
- RestAssured
- Cucumber (BDD) - Both Technical and Non-Technical members can understand
- TestNG
- Extent Reports

It tests the [Restful Booker API](https://restful-booker.herokuapp.com) for bookings, authentication, updates, and deletions.

---

## Project Structure
project-root/
├── src/
│ └── test/
│ ├── java/
│ │ └── runners/
│ │ │ └── TestRunner.java
│ │ ├── stepdefs/
│ │ │ ├── AuthenticationSteps.java
│ │ │ ├── DeleteBookingSteps.java
│ │ │ ├── GetBookingSteps.java
│ │ │ ├── Hooks.java
│ │ │ └── PatchBookingSteps.java
│ │ └── utils/
│ │ │ ├── ApiClient.java
│ │ │ ├── AuthenticationHelper.java
│ │ │ ├── JsonDataReader.java
│ │ │ └── ScenarioContext.java
│ └── resources/
│ │ │ ├── features.java
│ │ │ ├── test-data.java
│ │ │ ├── extent.properties.java
│ │ │ ├── extent-config.xml.java
│ │ │ └── log4j.properties
├── reports/
├── pom.xml
└── README.md

---

## Key Components
### Step Definitions
- `GetBookingSteps.java` → Booking creation and validation.
- `PatchBookingSteps.java` → Partial booking updates.
- `AuthenticationSteps.java` → Authentication token generation.

### Utilities
- `ApiClient.java` → Base RestAssured configuration.
- `AuthenticationHelper.java` → Token generation helper.
- `JsonDataReader.java` → Loads test data from external files.
- `ScenarioContext.java` → Shared state for token, bookingId, and response.

### Reporting
- Integrated with Extent Reports to generate detailed HTML test reports.

---

## Test Execution
Run tests via Maven:
```bash
mvn clean test

Reports will be generated at:
target/extent-reports/ExtentReport.html


