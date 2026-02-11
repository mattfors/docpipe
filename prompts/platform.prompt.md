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

**Implemented Migrations**:
- `001-create-def-tables.yaml` - Creates all `def_*` configuration tables
- `002-seed-demo-tenant.yaml` - Seeds demo-tenant with Part Material document configuration
- `003-create-data-tables.yaml` - Creates `data_doc_hdr` and `data_doc_segment` runtime tables

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

---

## Implemented Components

### Services (com.fors.service)

**ConfigurationTreeService**
- Loads complete configuration hierarchy from database
- Builds tenant → adapters/routes and documents → segments → fields tree
- Provides nested node classes (TenantNode, AdapterNode, RouteNode, DocumentNode, SegmentNode, FieldNode)
- Used by configuration management UI

**DocumentDataService**
- Loads processed document instances with unpacked segment data
- Unpacks fixed-width segment_data byte arrays into individual field values
- Provides nested node classes (DocumentInstanceNode, SegmentInstanceNode, FieldValueNode)
- Implements unpacking logic: offset calculation, byte extraction, UTF-8 conversion
- Used by document data viewing UI

### Controllers (com.fors.web)

**AdminController**
- `/admin` - Configuration management page
- `/admin/data` - Document data viewing page with tenant filter
- Injects ConfigurationTreeService and DocumentDataService
- Passes data to Thymeleaf templates

**HealthController**
- `/api/keepalive` - Returns JSON status for availability checking

### Repositories (com.fors.persistence)

**Definition Tables**:
- DefTenantRepository
- DefAdapterRepository
- DefAdapterRouteRepository
- DefDocRepository
- DefDocSegmentRepository
- DefDocFieldRepository

**Runtime Data Tables**:
- DataDocHdrRepository - Document instances with tenant filtering
- DataDocSegmentRepository - Segment instances with ordering by seq_no

All repositories use composite key types with @IdClass pattern.

### Entities (com.fors.persistence.entity)

**Definition Entities**:
- DefTenant, DefAdapter, DefAdapterRoute
- DefDoc, DefDocSegment, DefDocField
- All use composite keys via nested IdClass implementations

**Runtime Data Entities**:
- DataDocHdr - Document header with timestamp
- DataDocSegment - Segment with byte[] segment_data

All entities use explicit @Table and @Column annotations matching database schema.
