
# Scalable Tracking Number Generator API

## Objective
The Scalable Tracking Number Generator API is designed to generate **unique tracking numbers for parcels**. The system is built to be **scalable**, **efficient**, and **concurrent**, meeting the demands of high-performance parcel tracking systems.

---

## Features
- **RESTful API** to generate unique tracking numbers.
- **Concurrency-safe** with mechanisms to handle high-volume requests.
- **Unique tracking number generation** adhering to the regex pattern: `^[A-Z0-9]{1,16}$`.
- **Scalable architecture** designed for horizontal scaling.
- Built with **Spring Boot** for a modular, production-ready backend.

---

## API Specification

### Endpoint
- **URL**: `GET /api/v1/tracking/next-tracking-number`
- **Method**: `GET`
- **Query Parameters**:
  - `origin_country_id` (String, Required): ISO 3166-1 alpha-2 country code of the origin (e.g., `"US"`).
  - `destination_country_id` (String, Required): ISO 3166-1 alpha-2 country code of the destination (e.g., `"CA"`).
  - `weight` (String, Required): Order weight in kilograms, up to three decimal places (e.g., `"1.234"`).
  - `created_at` (String, Optional): RFC 3339 format timestamp (e.g., `"2024-11-16T12:00:00Z"`).
  - `customer_id` (String, Optional): Customer's UUID (e.g., `"de619854-b59b-425e-9db4-943979e1bd49"`).
  - `customer_name` (String, Optional): Customer's full name (e.g., `"Acme Corp"`).
  - `customer_slug` (String, Required): Customer's slug-case name (e.g., `"acme-corp"`).

### Response
- **Status**: `200 OK`
- **Content-Type**: `application/json`
- **Body**:
  ```json
  {
    "tracking_number": "USCA1234ACME9ABCD",
    "created_at": "2024-11-16T12:00:00Z"
  }
  ```

---

## Constraints
1. **Regex Compliance**: The tracking number must match the regex pattern `^[A-Z0-9]{1,16}$`.
2. **Uniqueness**: No duplicate tracking numbers will be generated.
3. **Efficiency**: The system is optimized for high-performance tracking number generation.
4. **Concurrency**: Supports multiple concurrent requests without degradation.
5. **Scalability**: Designed to scale horizontally for high traffic.
6. **Maintainability**: Codebase is modular and well-documented for easy maintenance and upgrades.
7. **Reliability**: Includes fault tolerance mechanisms and error handling to ensure high availability.

---

## Prerequisites
- **Java 17** or later.
- **Spring Boot: v3.x**
- **Maven 3.8+**.
- **PostgreSQL 14+** or equivalent relational database.
- **Redis** (for distributed locking).
- **Additional Technologies:**
  - **Swagger Open API for API documentation**
  - **Spring AOP for centralized logging**
  - **Spring Actuator for monitoring and management**


---

## Setup Instructions

### 1. Clone the Repository
```bash
git clone https://github.com/javasaroj/scalable-tracking-api.git
cd scalable-tracking-api
```

### 2. Configure Database
1. Update `application.yml` to configure PostgreSQL:
    ```yaml
    spring:
      datasource:
        url: jdbc:postgresql://localhost:5432/tracking_db
        username: username
        password: password
      jpa:
        hibernate:
          ddl-auto: update
    ```
2. Run the PostgreSQL database locally or in a Docker container:
    ```bash
    docker run --name tracking-db -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=yourpassword -e POSTGRES_DB=tracking_db -p 5432:5432 -d postgres
    ```

### 3. Install Dependencies
```bash
mvn clean install
```

### 4. Run the Application
```bash
mvn spring-boot:run
```

---

## Deployment Instructions
### 1. Docker Deployment
Build the Docker image:
```bash
docker build -t tracking-number-generator .
```

Run the container:
```bash
docker run -p 9191:8080 --env-file .env tracking-number-generator
```

### 2. Cloud Deployment
Deploy the application to any cloud platform (e.g., AWS Elastic Beanstalk, Google App Engine).

---

## Testing

### 1. Unit Tests
Run all unit tests:
```bash
mvn test
```

### 2. API Testing
Use Postman or curl:
```bash
curl -X GET "http://localhost:9191/api/v1/tracking/next-tracking-number" \
     -G --data-urlencode "origin_country_id=IN" \
        --data-urlencode "destination_country_id=UK" \
        --data-urlencode "weight=1.234" \
        --data-urlencode "customer_slug=acme-corp"
```

---

## Design Considerations

### Scalability
- **Horizontal Scaling**: Can scale across multiple service instances.
- **Redis Locking**: Optional Redis-based locking for distributed environments.

### Concurrency
- **Database Uniqueness Constraint**: Ensures no duplicate tracking numbers.
- **Optimistic Locking**: Handles retries on unique constraint violations.

### Flexibility
- Can extend functionality by processing additional fields dynamically.

---

## Future Enhancements
1. **Asynchronous Generation**: Use message queues (e.g., Kafka) for asynchronous tracking number generation.
2. **Advanced Validation**: Add more rules for parameter-specific constraints.
3. **Logging and Monitoring**: Integrate tools like ELK stack or Prometheus for observability.
4. **Advanced Metadata Storage**: Store additional unused fields for analytics or reporting.

---

## Access URLs
1. **[Swagger UI]**: http://localhost:9191/swagger-ui/index.html
2. **[Spring Doc API]**: http://localhost:9191/tracking-number-generator
3. **[PgAdmin]**: http://localhost:7070/browser/
4. **[Prometheus]**: http://localhost:9191/actuator/prometheus

---

## Documentation
1. **API Documentation**: Access the Swagger UI for comprehensive API documentation.
2. **Spring Actuator**: Provides monitoring and management features. Check the Spring Doc URL for details.
## Source Code
**Repository**: (https://github.com/javasaroj/scalable-tracking-api)"# scalable-tracking-api" 
