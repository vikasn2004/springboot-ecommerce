# Spring Boot E-Commerce REST API

A backend REST API for an e-commerce platform built with **Java 17**, **Spring Boot 3.3.5**, **Spring Security + JWT**, **MySQL**, and **Apache Kafka**.

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 3.3.5 |
| Security | Spring Security + JWT (jjwt 0.12.3) |
| Database | MySQL 8 |
| ORM | Spring Data JPA / Hibernate |
| Messaging | Apache Kafka |
| API Docs | Swagger UI (SpringDoc OpenAPI) |
| Build | Maven |

---

## Features

- User registration and login with JWT authentication
- Role-based access control: `ADMIN` and `USER`
- Product CRUD (admin only for write operations)
- Order placement, retrieval, and cancellation
- Ownership-based authorization (users can only access their own orders)
- Kafka event publishing on order placement
- Global exception handling with structured error responses
- Unit tests for Product and Order services

---

## Project Structure

```
src/main/java/com/vikas/ecommerce/
├── config/          # Security, Swagger, Kafka topic, AppConfig beans
├── controller/      # REST controllers (User, Product, Order)
├── DTO/             # Request and Response data transfer objects
├── entities/        # JPA entities (User, Product, Order, OrderItem)
├── exceptions/      # Custom exceptions + GlobalHandler
├── filters/         # JWT request filter
├── kafka/           # Kafka producer and consumer
├── mapper/          # Order entity → DTO mapper
├── repository/      # Spring Data JPA repositories
└── service/         # Service interfaces and implementations
```

---

## API Endpoints

### Auth (Public)
| Method | Endpoint | Description |
|---|---|---|
| POST | `/ecommerce/user/register` | Register a new user |
| POST | `/ecommerce/user/login` | Login and get JWT token |

### Admin
| Method | Endpoint | Description |
|---|---|---|
| PUT | `/ecommerce/admin/promote/{email}` | Promote a user to ADMIN |

### Products
| Method | Endpoint | Who |
|---|---|---|
| POST | `/ecommerce/products` | ADMIN |
| GET | `/ecommerce/products` | ADMIN, USER |
| GET | `/ecommerce/products/{id}` | ADMIN, USER |
| PUT | `/ecommerce/products/{id}` | ADMIN |
| DELETE | `/ecommerce/products/{id}` | ADMIN |

### Orders
| Method | Endpoint | Who |
|---|---|---|
| POST | `/ecommerce/orders` | ADMIN or order owner |
| GET | `/ecommerce/orders/all` | ADMIN |
| GET | `/ecommerce/orders/{userId}` | ADMIN or that user |
| DELETE | `/ecommerce/orders/{orderId}` | ADMIN or order owner |

---

## Running Locally

### Option 1: Docker Compose (recommended — runs everything)

```bash
# 1. Build the jar first
./mvnw clean package -DskipTests

# 2. Start all services (app + MySQL + Kafka + Zookeeper)
docker-compose up --build
```

The API will be available at `http://localhost:8080`

### Option 2: Manual (requires MySQL and Kafka running locally)

1. Create a MySQL database named `ecommerce`
2. Set environment variables (or edit `application.properties` for local dev):

```bash
export DB_URL=jdbc:mysql://localhost:3306/ecommerce
export DB_USERNAME=root
export DB_PASSWORD=yourpassword
export JWT_SECRET=your-secret-key-min-32-chars-long
export KAFKA_BOOTSTRAP_SERVERS=localhost:9092
```

3. Run the app:
```bash
./mvnw spring-boot:run
```

---

## Swagger UI

Once the app is running, visit:
```
http://localhost:8080/swagger-ui.html
```

Click **Authorize** and enter your JWT token (from login/register) as:
```
Bearer <your_token>
```

---

## Running Tests

```bash
./mvnw test
```

---

## Environment Variables

| Variable | Description | Default (dev only) |
|---|---|---|
| `DB_URL` | MySQL JDBC URL | `jdbc:mysql://localhost:3306/ecommerce` |
| `DB_USERNAME` | MySQL username | `root` |
| `DB_PASSWORD` | MySQL password | `root` |
| `JWT_SECRET` | JWT signing secret (min 32 chars) | insecure default |
| `KAFKA_BOOTSTRAP_SERVERS` | Kafka broker address | `localhost:9092` |

> **Never commit real secrets to git.** Use environment variables or a `.env` file (already excluded by `.gitignore`).
