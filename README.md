# Java Project Repository

## Overview
This repository contains multiple exercises aimed at exploring the integration of NoSQL databases and working with microservices architecture in a Java project using Quarkus. The exercises are designed as part of the coursework for EPITA's engineering program, focused on hands-on learning and experimentation with real-world scenarios.

### Project Structure
The repository is divided into the following key sections:

- **Boutique_en_Ligne/**
  - **td1**: Introduction to microservices architecture using Quarkus, development of REST APIs, and inter-service communication using Maven modules.
  - **td2**: Integration of MongoDB for product and purchase data storage, and Redis as a message queue for handling purchase events, with an introduction to the CQRS pattern.
  - **td3**: Integration of ElasticSearch for receipt storage and search functionalities, and Neo4J to track relationships between customers and products for graph analysis.

- **jeu_en_ligne/**
  - Backend development of an online game (Bomberman), implemented in Java.
  - Utilizes Quarkus and Hibernate frameworks for game logic, player movement, bomb management, and REST API communication between frontend and backend.

## Technologies Used
- **Quarkus**: A Kubernetes-native Java framework tailored for building microservices.
- **MongoDB**: NoSQL database for data storage.
- **Redis**: Message queue for inter-service communication.
- **ElasticSearch**: Search engine for indexing and querying receipts.
- **Neo4J**: Graph database used to model relationships between customers and products.
- **Docker**: For containerization and service orchestration using `docker-compose`.
- **Maven**: Dependency management and project organization.

## Setup Instructions

### Prerequisites
- Java Development Kit (JDK)
- Maven
- Docker

### Steps to Run the Project
1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/Java_project.git
   cd Java_project
   ```

2. Build the Maven modules:
    ```bash
    mvn clean install
    ```

3. Start the necessary services using Docker:
    ```bash
    docker-compose up
    ```
4. Navigate to the desired project folder and run the Quarkus application:
    ```bash
    mvn quarkus:dev
    ```
5. Access the exposed REST APIs on [http://localhost:8080](http://localhost:8080)
