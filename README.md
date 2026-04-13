# TaskFlow

A task management REST API built with Java 17 + Spring Boot, PostgreSQL, and Docker.

> Backend built with Java + Spring Boot instead of Go, as it is my primary language.

---

## Tech Stack

- Java 17 + Spring Boot 3.2.5
- PostgreSQL 15
- Flyway (database migrations)
- Spring Security + JWT (authentication)
- Docker + Docker Compose

---

## Architecture Decisions

I structured the project into feature packages (auth, project, task, user, config) rather than a layered architecture. Each package owns its controller, service, repository, and DTOs. This makes it easier to navigate and scale individual features.

I used Flyway for migrations instead of Hibernate auto-DDL because the spec required explicit schema management. Every schema change is versioned and reversible.

JWT is stateless — no session storage needed. The token contains user_id and email, expires in 24 hours, and is verified on every request via a filter.

I intentionally left out pagination and the stats endpoint due to time constraints. These would be straightforward to add given the existing query structure.

---

## Running Locally

Prerequisites: Docker Desktop

```bash
git clone https://github.com/your-username/taskflow-yourname
cd taskflow-yourname
cp .env.example .env
docker compose up --build
```

API available at: http://localhost:8080

---

## Running Migrations

Migrations run automatically on container start via Flyway. No manual steps needed.

---

## Test Credentials
Email:    test@example.com
Password: password123

---

## API Reference

### Auth

**POST /auth/register**
```json
// Request
{ "name": "Jane Doe", "email": "jane@example.com", "password": "secret123" }

// Response 201
{ "token": "<jwt>", "userId": "uuid", "email": "jane@example.com", "name": "Jane Doe" }
```

**POST /auth/login**
```json
// Request
{ "email": "jane@example.com", "password": "secret123" }

// Response 200
{ "token": "<jwt>", "userId": "uuid", "email": "jane@example.com", "name": "Jane Doe" }
```

### Projects (all require Authorization: Bearer <token>)

| Method | Endpoint | Description |
|---|---|---|
| GET | /projects | List projects for current user |
| POST | /projects | Create a project |
| GET | /projects/:id | Get project details |
| PATCH | /projects/:id | Update project (owner only) |
| DELETE | /projects/:id | Delete project (owner only) |

### Tasks

| Method | Endpoint | Description |
|---|---|---|
| GET | /projects/:id/tasks | List tasks (supports ?status= and ?assigneeId=) |
| POST | /projects/:id/tasks | Create a task |
| PATCH | /tasks/:id | Update a task |
| DELETE | /tasks/:id | Delete a task |

---

## What I'd Do With More Time

- Add pagination to list endpoints
- Add GET /projects/:id/stats endpoint
- Write integration tests for auth and task endpoints
- Add proper error handling for invalid UUID formats
- Add request logging middleware
- Remove the Lombok dependency — I removed it mid-build due to annotation processing issues and replaced with manual constructors, which is actually cleaner