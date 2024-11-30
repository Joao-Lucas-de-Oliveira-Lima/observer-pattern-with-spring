# Observer Pattern with Spring: Rodeo Event Registration API

A REST API built with **Spring** to manage rodeo events, financial reports, and ticket information. This project demonstrates the implementation of the **Observer Design Pattern**, leveraging Spring's `ApplicationEvent`, `ApplicationEventPublisher`, and `EventListener` to enable decoupled communication between services.

---

## Table of Contents
- [Overview](#overview)
- [Prerequisites](#prerequisites)
- [How to Run the Application](#how-to-run-the-application)
- [Running Tests](#running-tests)
- [API Documentation](#api-documentation)

---

## Overview

This API offers the following functionalities:

- **Rodeo Event Management:** Automatically generates related tickets (one-to-many) and financial reports (one-to-one) when a rodeo event is registered.
- **Real-Time Updates:** Automatically adjusts financial reports when ticket information changes.

> The **Observer Design Pattern** ensures efficient communication between services without direct or circular dependencies.

---

## Prerequisites

Ensure the following dependencies are installed:

- [Java 21](https://www.oracle.com/java/technologies/downloads/#java21)
- [Apache Maven 3+](https://maven.apache.org/install.html)

---

## How to Run the Application

### Step 1: Build the Application
From the project root directory, execute:
```bash
mvn clean install -DskipTests
```
### Step 2: Start the Application
Run the application with:

```bash
mvn spring-boot:run
```
The application will be available at:
`http://localhost:8080`

> To change the default port, modify the `server.port` property in the `application.properties` file.

## Running Tests
### Unit Tests
To execute unit tests:
```bash
mvn test
```
### Integration Tests
To execute integration tests:
```bash
mvn verify -Pfailsafe
```

## Api Documentation

### Main Endpoints

#### **Tickets**
- `GET /api/v1/tickets` - Retrieve tickets by event ID.
- `PUT /api/v1/tickets/{id}` - Update ticket information by ID.

#### **Financial Reports**
- `GET /api/v1/financialReports` - Retrieve financial reports by event ID.

#### **Rodeo Events**
- `GET /api/v1/rodeoEvents` - Retrieve rodeo events with case-insensitive name search.
- `POST /api/v1/rodeoEvents` - Create a new rodeo event.

### Swagger UI
- For interactive documentation, visit:  
  `/swagger-ui/index.html`

- For API JSON documentation:  
  `/v3/api-docs`

