
# Health & Help Application

Health & Help is a RESTful backend application built with **Spring Boot** following **HATEOAS principles**.  
The API provides hypermedia-driven navigation where available actions depend on the user's role and authentication token.

---

## Authentication

Authentication is implemented using **JWT tokens**.

Workflow:

1. Authenticate via the `/login` endpoint
2. Receive a JWT token
3. Use the token in subsequent API requests

---

## API Entry Point

The API entry point is:
/start


The application returns **HAL JSON responses**, which include hypermedia links to available resources depending on the user's role and permissions.

---

## Running the Application

### 1. Start the database
docker compose up -d


This command will start the PostgreSQL database container.

### 2. Run the application

Start the Spring Boot application from your IDE or using Maven/Gradle.

### 3. Authenticate

Send a request to:
POST /login


Request body example:

```json
{
  "name": "name",
  "password": "password"
}
```

The response will include a JWT token required for further requests.

#### Users:
There is only 2 users with roles :
- MANAGER
- DOCTOR

Available API endpoints depend on the authenticated user's role.

API Endpoints
General

GET /start
GET /stock
POST /login
GET /item/{item-id}

DOCTOR Endpoints

GET /list-of-patients
GET /patient/{patient-id}
POST /patient

MANAGER Endpoints

GET /list-of-food
GET /food/{food-id}
POST /food

#### Technologies:
- java20
- Spring boot
- Spring Security via jwt
- Spring boot data Jpa
- PostgresDb
- Docker
- HATEOAS
- Junit5
