# Rodeo Event Registration API
A REST API built with Spring to simulate a rodeo event logging system. This project demonstrates the implementation of the Observer Design Pattern using Spring's `ApplicationEvent`, `ApplicationEventPublisher` and `EventListener`. The main goal is to provide communication between services, reducing coupling and preventing circular dependencies.


## Table of Contents
- [Overview](#overview)
- [Installation Guide](#installation-guide)
- [Tests](#tests)
- [Documentation](#documentation)


## Overview
This API provides endpoints for managing rodeo events, financial reports, and ticket information. The Observer Design Pattern is used to decouple services and trigger actions upon specific events. When a new `RodeoEvent` is registered, an event is published, which is then handled by the `TicketService` and `FinancialReportService`. These services create a list of tickets (One-to-Many relationship) and a financial report (One-to-One relationship), respectively.

Additionally, when a ticket is updated, an event is triggered and handled by the `FinancialReportService`, which updates its records based on the latest ticket information. 

## Installation Guide

### Prerequisites
- [Java 21](https://www.oracle.com/br/java/technologies/downloads/#java21)
- [Apache Maven 3.9.8 or later](https://maven.apache.org/install.html)

### Running the Application with Maven

1. **Build the Application**
  
In the project root directory, run the following command to start building the project:
```bash
mvn clean install -DskipTests
```

2. **Run the Application**

To start the application, use the command:
```bash
mvn spring-boot:run
```

## Tests

Run the following commands in the terminal, from the application root directory:

- For unit tests:
```bash
mvn test
```
- For integration tests:
```bash
mvn verify -Pfailsafe
```

## Documentation

### API Endpoints Preview
- Financial Report
```text
GET /api/v1/financialReports - Retrieve financial reports by rodeo event ID.
```
- Rodeo Event
```text
GET /api/v1/rodeoEvents - Retrieve a page of rodeo events containing a case-insensitive name.
POST /api/v1/rodeoEvents - Create a new rodeo event.
```
- Ticket
```text
GET /api/v1/tickets - Retrieve tickets by rodeo event ID.
PUT /api/v1/tickets/{id} - Update ticket information by ID.
```

### OpenAPI Documentation
- To view the full API documentation, including endpoints and data schemas, open the Swagger UI at:
  `/swagger-ui/index.html`

- For API documentation in JSON format suitable for tools like Postman, Insomnia, and other API clients, go to: `/v3/api-docs`.