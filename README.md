# Ride Booking Service

A backend for a ride-hailing app (like Uber or Lyft), built with **Java 25** and **Spring Boot**.
Riders request trips, nearby drivers accept them, and each ride moves through a controlled lifecycle
from request to completion.

This is a learning project that covers the core parts of Spring Boot: REST APIs, JPA/Hibernate,
transactions, validation, error handling, profiles and concurrency control.

---

## Features

- **Ride lifecycle**: request → driver assigned → driver arriving → in progress → completed (or cancelled)
- **State machine**: invalid status changes are rejected (for example, a completed ride can't be restarted)
- **Driver availability**: drivers go online/offline, become busy on a trip and are freed when it ends
- **Live driver location**: drivers send their GPS position, and stale locations are ignored
- **Nearby driver search**: finds available drivers within a radius of the pickup point, nearest first
- **No double booking**: optimistic locking (`@Version`) stops one driver from being assigned to two rides
- **Validation and clear errors**: 400 for bad input, 404 for unknown resources, 409 for conflicts

---

## Tech stack

| Area | Technology |
|---|---|
| Language | Java 25 |
| Framework | Spring Boot 4 (Spring Web MVC, Spring Data JPA, Validation, Actuator) |
| Database | PostgreSQL (running in Docker) |
| ORM | Hibernate |
| Build | Maven (wrapper included) |
| Dev tools | Spring Boot DevTools |

---

## Project structure

The code is organized **by feature**, with layers inside each feature:

```
src/main/java/com/project/ridebooking/
├── RidebookingApplication.java
├── common/       # shared config, exceptions, geo utilities, dev data seeder
├── rider/        # rider entity and repository
├── driver/       # driver entity, availability, location, nearby search
└── ride/         # ride entity, state machine, service and REST API
```

---

## Getting started

### Prerequisites

- JDK 25
- Docker Desktop

### 1. Start PostgreSQL

```bash
docker run --name ridedb \
  -e POSTGRES_DB=ridebooking \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=postgres \
  -p 5432:5432 -d postgres
```

### 2. Run the application

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

The app starts on **http://localhost:8080**. With the `dev` profile, sample riders and drivers are
inserted automatically on startup.

### 3. Check it's running

```bash
curl http://localhost:8080/actuator/health
# {"status":"UP"}
```

---

## Configuration

| File | Purpose |
|---|---|
| `application.properties` | Shared settings |
| `application-dev.properties` | Local database, SQL logging, auto schema update |
| `application-prod.properties` | Production settings read from environment variables (`DB_URL`, `DB_USER`, `DB_PASSWORD`) |

---

## API

### Rides

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/rides` | Request a ride |
| `GET` | `/api/rides/{id}` | Get a ride |
| `POST` | `/api/rides/{id}/accept` | Driver accepts the ride (body: `{"driverId": 1}`) |
| `POST` | `/api/rides/{id}/arrive` | Driver is arriving at pickup |
| `POST` | `/api/rides/{id}/start` | Trip starts |
| `POST` | `/api/rides/{id}/complete` | Trip ends, and the driver becomes available again |
| `POST` | `/api/rides/{id}/cancel` | Cancel the ride, freeing the driver if one was assigned |

### Drivers

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/drivers/{id}` | Get a driver |
| `PUT` | `/api/drivers/{id}/location` | Update the driver's location (body: `{"latitude": .., "longitude": ..}`) |
| `POST` | `/api/drivers/{id}/online` | Driver goes online (AVAILABLE) |
| `POST` | `/api/drivers/{id}/offline` | Driver goes offline |
| `GET` | `/api/drivers/nearby?lat=..&lng=..&radiusKm=3` | Available drivers near a point, nearest first |

---

## Example: a full trip

```bash
# 1. Driver 1 shares their location
curl -X PUT http://localhost:8080/api/drivers/1/location \
  -H "Content-Type: application/json" \
  -d '{"latitude":37.7790,"longitude":-122.4180}'

# 2. Rider 1 requests a ride in San Francisco
curl -X POST http://localhost:8080/api/rides \
  -H "Content-Type: application/json" \
  -d '{"riderId":1,"pickupLat":37.7749,"pickupLng":-122.4194,"dropoffLat":37.8044,"dropoffLng":-122.2712}'

# 3. Driver 1 accepts, arrives, starts and completes the trip
curl -X POST http://localhost:8080/api/rides/1/accept -H "Content-Type: application/json" -d '{"driverId":1}'
curl -X POST http://localhost:8080/api/rides/1/arrive
curl -X POST http://localhost:8080/api/rides/1/start
curl -X POST http://localhost:8080/api/rides/1/complete
```

---

## Ride status flow

```
REQUESTED ──▶ DRIVER_ASSIGNED ──▶ DRIVER_ARRIVING ──▶ IN_PROGRESS ──▶ COMPLETED
    │               │                   │
    └───────────────┴───────────────────┴──▶ CANCELLED
```

## Driver status flow

```
OFFLINE ◀──▶ AVAILABLE ──(accepts ride)──▶ BUSY ──(trip completed/cancelled)──▶ AVAILABLE
```

---

## Design decisions

- **Rich domain model**: business rules live in the entities (`Ride.assignDriver()`, `Driver.markBusy()`),
  so they can't be bypassed from elsewhere in the code.
- **Optimistic locking**: `@Version` on `Ride` and `Driver` makes Hibernate check the version on every update.
  If two requests change the same row at once, one fails with **409 Conflict** instead of silently
  overwriting the other.
- **Transactions**: each ride action runs in one `@Transactional` method, so the ride and the driver
  always change together or not at all.
- **Nearby search in two steps**: the database first filters drivers inside a bounding box around the
  pickup point, then the exact distance is calculated with the **Haversine formula** and results are
  sorted by distance.
- **DTOs**: entities are never exposed directly. Requests and responses use separate classes.

---

## Roadmap

- [ ] Unit and integration tests (JUnit, `@WebMvcTest`, `@DataJpaTest`, Testcontainers)
- [ ] Flyway database migrations
- [ ] Automatic matching: offer each ride to the nearest driver, with a timeout
- [ ] Redis GEO for fast driver location lookups
- [ ] WebSocket updates for live trip tracking
- [ ] Kafka events (`RideRequested`, `RideCompleted`) and a notification service
- [ ] Fare estimation and surge pricing
- [ ] Spring Security with JWT authentication
- [ ] Docker Compose for the whole stack
