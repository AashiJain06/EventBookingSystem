# Event Ticket Booking System

A production-style backend application built using **Java 21**, **Spring Boot 3**, and **Microservices Architecture**. The system allows users to browse events, book tickets, and manage bookings while demonstrating modern backend development practices such as service discovery, centralized configuration, distributed caching, authentication, and containerization.

## Architecture

The application follows a Microservices Architecture and consists of the following services:

* **API Gateway** – Central entry point for all client requests.
* **Auth Service** – Handles user authentication and JWT token generation.
* **User Service** – Manages user profiles and user-related information.
* **Event Service** – Manages events and event details.
* **Booking Service** – Handles ticket booking operations and seat management.
* **Eureka Server** – Service discovery and registration.
* **Config Server** – Centralized configuration management.

## Features

### User Management

* Create, update, retrieve, and delete users
* Input validation and exception handling
* Pagination support

### Authentication & Authorization

* JWT-based authentication
* Role-based access control (RBAC)
* Secure API access

### Event Management

* Create and manage events
* Retrieve event details
* Event availability tracking

### Booking Management

* Ticket booking functionality
* Booking validation
* Booking history management

### Redis Integration

* Fast data retrieval using Redis caching
* Improved performance for frequently accessed data

### API Gateway

* Centralized request routing
* Simplified client interaction with microservices

### Service Discovery

* Dynamic service registration using Eureka Server
* Simplified inter-service communication

### Centralized Configuration

* Externalized configuration using Spring Cloud Config Server
* Environment-specific configuration support

### Docker Support

* Dockerized microservices
* Docker Compose orchestration
* Simplified deployment across environments

## Tech Stack

### Backend

* Java 21
* Spring Boot 3
* Spring Security
* Spring Data JPA
* Hibernate
* Spring Cloud

### Microservices

* Eureka Server
* Spring Cloud Config Server
* API Gateway

### Database

* MySQL

### Caching

* Redis

### Security

* JWT Authentication

### Documentation

* Swagger / OpenAPI

### Build Tools

* Maven

### Version Control

* Git
* GitHub

### Containerization

* Docker
* Docker Compose

## Project Structure

```text
event-ticket-booking-system
│
├── api-gateway
├── auth-service
├── booking-service
├── config-server
├── eureka-server
├── event-service
├── user-service
└── docker-setup
```

## Key Concepts Demonstrated

* Microservices Architecture
* Service Discovery
* Centralized Configuration
* JWT Authentication
* RESTful API Development
* Redis Caching
* Docker Containerization
* API Gateway Pattern
* Layered Architecture
* Exception Handling
* DTO Pattern
* Validation
* Pagination

## Future Enhancements

* Kafka-based Event Driven Communication
* Email Notification Service
* Payment Gateway Integration
* Distributed Tracing
* Monitoring with Prometheus and Grafana
* Kubernetes Deployment
* CI/CD Pipeline Integration

## Learning Outcomes

This project helped in gaining hands-on experience with:

* Designing scalable backend systems
* Building microservices using Spring Boot
* Managing service communication
* Implementing authentication and authorization
* Working with Redis caching
* Dockerizing applications
* Understanding distributed system fundamentals

## Author

Aashi Jain

LinkedIn: https://www.linkedin.com/in/aashi-jain-866783278/

GitHub: https://github.com/AashiJain06
