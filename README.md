# Building and Running Restaurant Decider Application

## Prerequisites

- Java Development Kit (JDK) installed on your system
- Maven build tool installed on your system
- MySQL Database installed and configured ```(update MySQL properties in application.properties file as required)```

## Build Instructions

1. Clone the Restaurant Decider application repository from GitHub:

   ```bash
   git clone <repository_url>

2. Navigate to the project directory:

    ```bash
    cd restaurant.decider

3. Build the project using Maven:

    ```bash
    mvn clean package
   ```
   (to build without running test cases)
    ```bash
    mvn clean package -DskipTests
   ```

---
## Running the Application

1. After building the project, navigate to the target directory:

    ```bash
     cd target

2. Run the application JAR file using the java -jar command:

    ```bash
    java -jar restaurant-decider.jar

This command will start the Restaurant Decider application. Make sure you have configured the database connection properly in the application.properties file.

---

# Restaurant Decider API Guide

## Overview

The Restaurant Decider application provides a set of APIs to manage sessions, users, and restaurant choices.

## Base URL

`http://localhost:8080`

## Authentication

No authentication required. All endpoints are publicly accessible.

## API Endpoints

### User Management

- **GET /users**: Retrieve all users.
- **GET /users/{id}**: Retrieve a user by ID.
- **POST /users**: Add a new user.

### Session Management

- **POST /sessions/initiate**: Initiate a new session.
- **POST /sessions/{id}/join**: Join a user for the session.
- **POST /sessions/{id}/submit-restaurant**: Submit a restaurant choices for the session.
- **POST /sessions/{id}/end**: End the session and randomly pick a restaurant.

### Restaurant Management

- **POST /restaurants/initiate**: Add a new restaurant.
- **GET /restaurants**: Retrieve all restaurants.
- **GET /restaurants/{id}**: Retrieve a restaurant by ID.

## Testing Instructions

1. Use tools like Postman or cURL to send requests to the API endpoints.
2. Ensure that the application is running locally on `http://localhost:8080`.
3. Send requests to the respective endpoints with appropriate request bodies if required.
4. Validate the responses to ensure they match the expected behavior.
5. Test various scenarios such as adding users, initiating sessions, submitting restaurant choices, and ending sessions
   to cover all functionalities.

## Sample Requests

### Add a User
```http
POST /users
Content-Type: application/json

{
    "username": "john_doe"
}
```

### Add a Restaurant
```http
POST /restaurants
Content-Type: application/json

{
    "name": "Street Burger"
}
```

### Initiate a Session
```http
POST /sessions/initiate
Content-Type: application/json

{
    "initiatorId": 1,
    "participantIds": [2, 3]
}
```

### Submit a Restaurant Choice
```http
POST /sessions/{session_id}/submit-restaurant
Content-Type: application/json

{
    "id": 1
}
```

### End a Session
```htt
POST /sessions/{session_id}/end
```

## Other Associated Endpoints

### Retrieve a User
```http
GET /users/{id}
```

### Retrieve All Users
```http
GET /users
```

### Retrieve a Restaurant
```http
GET /restaurants/{id}
```

### Retrieve All Restaurant
```http
GET /restaurants
```

### Retrieve a Session
```http
GET /sessions/{id}
```

#### Refer this postman collection for All the APIs :
[restaurant_decider.postman_collection.json](restaurant_decider.postman_collection.json)
