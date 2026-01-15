
##  Backend README (collision-detection-backend)


# Collision Detection Backend (Spring Boot + MySQL)

A simple student-style backend API for a vehicle collision-detection admin platform.

It provides REST endpoints for:
- User login & registration
- Admin management of users and families
- Sensor data ingestion from IoT devices
- Admin query of sensor records (with filters + pagination)

---

## Tech Stack

- Java 17
- Spring Boot 3.2.x
- Spring Web (REST API)
- Spring Data JPA + Hibernate
- JdbcTemplate (dynamic SQL for sensor querying)
- MySQL driver
- BCrypt encoder (spring-security-crypto)
- Validation (jakarta validation)
- JUnit tests (spring-boot-starter-test)

---

## Project Structure (Main)

- `controller/`
  - `AuthController` — `/api/login`, `/api/register`
  - `AdminUserController` — `/api/admin/users...`
  - `AdminFamilyController` — `/api/admin/families...`
  - `AdminSensorController` — `/api/admin/sensor-data`
  - `AdminVehicleController` — `/api/admin/vehicles...`
  - `IotIngestController` — `/api/iot/sensor-data`
- `model/` — JPA entities: `user_login`, `user_info`, `family`, `sensor_data`, `vehicle`
- `repo/` — repositories and projections
- `bootstrap/DataInitializer` — auto-create admin account on first run
- `config/WebConfig` — CORS + `PasswordEncoder`

---

## Configuration

### `src/main/resources/application.yml`

Default configuration:

- Server port: `8080`
- MySQL database: `detection_car`
- `spring.jpa.hibernate.ddl-auto: validate` (IMPORTANT)

Example snippet:
```yml
server:
  port: 8080

spring:
  datasource:
    url: jdbc:mysql://localhost:3306/detection_car?useUnicode=true&characterEncoding=utf8&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
    username: root
    password: 123456
  jpa:
    hibernate:
      ddl-auto: validate
````

### Important: Database tables must exist

Because `ddl-auto` is set to `validate`, Hibernate will NOT create tables.
You must create them manually, or change to:

* `update` (auto-create/update tables in dev)
* `create` (recreate tables each startup; NOT for production)

---

## Database Schema (Derived from Entities)

Tables used (names come from `@Table(name="...")`):

### `user_login`

* `id` (PK, auto increment)
* `user_id` (unique, not null)
* `username` (not null)
* `password` (not null)

### `user_info`

* `id` (PK, auto increment)
* `user_id` (not null)
* `name`
* `car_name`
* `tel`
* `email`
* `state` (0/1)
* `family_id` (nullable)

### `family`

* `id` (PK, auto increment)
* `family_id` (not null)  *(logical family ID used by the app)*
* `name`

### `sensor_data`

* `id` (PK, auto increment)
* `user_id` (not null)
* `car_name`
* `sensor_type`
* `ax`, `ay`, `az` (float)
* `pressure_value` (float)
* `raw_data` (json string)
* `created_at` (managed by DB; entity marks `insertable=false, updatable=false`)

### `vehicle`

* `id` (PK, auto increment)
* `family_id` (not null)
* `name` (not null)
* `model`
* `sensor_id`
* `status`
* `last_update` (datetime)

> Tip: For `sensor_data.created_at`, you should define a DEFAULT timestamp in MySQL (e.g., `DEFAULT CURRENT_TIMESTAMP`).

---

## Admin Account (Auto Created)

On first startup, if the admin username does not exist, the backend creates:

* username: `admin`
* password: `admin123`

Configurable via:

```yml
app:
  admin:
    username: admin
    password: admin123
```

---

## Build & Run

### Requirements

* Java 17
* Maven
* MySQL running + database `detection_car` created

### Build

```bash
mvn clean install
```

### Run

```bash
mvn spring-boot:run
```

Backend starts on:

* `http://localhost:8080`

---

## REST API

### Auth (`/api`)

#### `POST /api/login`

Body:

```json
{ "username": "admin", "password": "admin123" }
```

Response:

```json
{
  "token": "...",
  "username": "admin",
  "userId": 1,
  "familyId": null,
  "message": "ok"
}
```

#### `POST /api/register`

Creates a new user record in `user_login` + `user_info`.

---

### Admin (`/api/admin`)

#### Users

* `GET /api/admin/users` — list users (projection)
* `POST /api/admin/users` — create user (password is BCrypt encoded)
* `PUT /api/admin/users/{userId}` — update user_info fields
* `PUT /api/admin/users/{userId}/state` — enable/disable (0/1)
* `PUT /api/admin/users/{userId}/password` — reset password (BCrypt)

#### Families

* `GET /api/admin/families` — list families
* `POST /api/admin/families` — create family (requires unique `familyId`)
* `GET /api/admin/families/{familyId}/members` — list `user_info` in that family

#### Sensor Data (query with pagination)

* `GET /api/admin/sensor-data`

Query parameters:

* `userId` (optional)
* `familyId` (optional)
* `startTime` (optional, string)
* `endTime` (optional, string)
* `page` (default 1)
* `size` (default 20)
* `order` (`asc`/`desc`, default `desc`)

Response:

```json
{
  "total": 123,
  "list": [ ... ]
}
```

#### Vehicles

* `GET /api/admin/vehicles?familyId=1001`
* `POST /api/admin/vehicles`
* `PUT /api/admin/vehicles/{id}/status?status=online`

---

### IoT Ingestion (`/api/iot`)

#### `POST /api/iot/sensor-data`

Body example:

```json
{
  "userId": 1,
  "carName": "MyCar",
  "sensorType": "impact",
  "pressureValue": 100.0,
  "ax": 0.1,
  "ay": 0.2,
  "az": 9.7,
  "rawData": { "any": "json" }
}
```

Header (optional):

* `X-IOT-KEY: ...`

> Note: the controller reads `X-IOT-KEY` but currently does not enforce it.

---

## CI / GitHub Actions

The backend contains a CI workflow that:

* runs unit tests on Java 17 and 21
* runs SpotBugs static analysis
* runs gitleaks secret scanning
* builds release JAR on tag push
* generates and deploys Javadoc on `master` branch

---

## Notes / Limitations

* Token generation exists (`TokenStore`), and the frontend sends `Authorization: Bearer ...`,
  but endpoints do not currently enforce token validation.
* AuthController login uses plain string comparison, while admin-created users use BCrypt.
  For consistency, login should verify BCrypt for all users.
* `ddl-auto: validate` requires database tables to be created beforehand.

---

## Quick Start Checklist

1. Start MySQL and create database `detection_car`
2. Create required tables (or change `ddl-auto` to `update`)
3. Run backend on `8080`
4. Run frontend with `VITE_API_BASE_URL=http://localhost:8080/api`
5. Login with `admin / admin123`

