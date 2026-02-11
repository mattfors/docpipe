# platform.prompt.md — Implementation Stack & Repo Conventions

## Non-negotiable Stack

- **Language**: Java (Spring Boot)
- **Database**: PostgreSQL
- **Persistence**: Spring Data JPA / Hibernate (do not use MyBatis)
- **Migrations**: Liquibase (do not use Flyway)
- **Integration/Routing**: Apache Camel
- **Server-side UI**: Thymeleaf (for simple admin screens; no SPA frameworks)

---

## Package Conventions

- **Base package**: `com.fors`
- **Suggested packages**:
  - `com.fors.app` — Spring Boot main + config
  - `com.fors.web` — Controllers; admin UI endpoints
  - `com.fors.routes` — Camel route builders
  - `com.fors.service` — Orchestration/services
  - `com.fors.persistence` — JPA repositories
  - `com.fors.persistence.entity` — JPA @Entity classes
  - `com.fors.domain` — Domain types, enums
  - `com.fors.liquibase` — Custom changelog wiring if needed

---

## JPA Conventions

- Use Spring Data JPA repositories
- Use explicit table names matching the schema (`def_*`, `data_*`)
- Avoid "clever" ORM mappings: keep entities straightforward and queryable
- Prefer `@Column` with explicit names
- Prefer `Instant` for timestamps
- Use composite keys where schema defines them

---

## Liquibase Conventions

- Changelogs are the source of truth for schema
- Keep changes small and incremental
- Use consistent naming for changesets
- Place changesets in `src/main/resources/db/changelog/`
- Include seed data migrations for demo tenant and Part Material example (see examples.prompt.md)

---

## Docker + Dev Container Expectations

- Repo must include a simple command to start PostgreSQL locally using Docker
- App must be configurable via environment variables for DB connection
- Provide a Dev Container configuration that includes:
  - JDK (Java 17 or later)
  - Docker-in-Docker support
  - `docker compose up` works inside the container

---

## Non-Goals (v1)

- No Temporal / workflow engines
- No Kafka/event bus
- No domain mapping or validation engines unless explicitly requested
- No complex orchestration frameworks
- No SPA frameworks (React, Angular, Vue)
