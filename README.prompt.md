# README.prompt.md — Agent Instructions

## For AI Agents Working on This Repository

When you check out this repository, follow these steps to understand the system and begin implementation:

### 1. Read the Prompts Directory

**All system specifications are in the `prompts/` directory.** These files define the complete architecture, schema, and requirements:

- Start with `prompts/README.md` for an overview
- Read `prompts/architecture.prompt.md` to understand the design philosophy
- Study `prompts/schema.prompt.md` for the database schema
- Review `prompts/domain.prompt.md` for domain concepts
- Check `prompts/workflows.prompt.md` for operational flows
- Reference `prompts/platform.prompt.md` for technology stack (Java/Spring Boot/PostgreSQL/Camel)
- Use `prompts/examples.prompt.md` for concrete seed data examples
- Consult `prompts/ui.prompt.md` when implementing the admin interface

### 2. Understand the Context

This is a **deliberate recreation** of late 1990s/early 2000s B2B integration system design patterns:
- Database-centric configuration
- Fixed-width field storage (mainframe heritage)
- Metadata-driven parsing
- Multi-tenant architecture with composite keys

**Do not modernize these patterns**—they are intentional design choices.

### 3. Technology Stack (Non-Negotiable)

- **Java** with Spring Boot
- **PostgreSQL** database
- **Spring Data JPA** / Hibernate (not MyBatis)
- **Liquibase** for migrations (not Flyway)
- **Apache Camel** for routing/integration
- **Thymeleaf** for server-side UI (no SPA frameworks)

See `prompts/platform.prompt.md` for detailed conventions.

### 4. Implementation Priorities

When asked to implement features:

1. **Schema first**: Use Liquibase migrations matching `prompts/schema.prompt.md`
2. **Seed data**: Include the Part Material example from `prompts/examples.prompt.md`
3. **JPA entities**: Create entities with explicit table/column names and composite keys
4. **Camel routes**: Build dynamic HTTP routes from `def_adapter_route` configuration
5. **Segment packing**: Implement fixed-width packing/unpacking logic per `prompts/domain.prompt.md`
6. **Admin UI**: Use Thymeleaf following `prompts/ui.prompt.md` specifications

### 5. Key Implementation Notes

- **String IDs everywhere**: All `_id` columns are strings, not numbers
- **Composite keys**: All tables include `tenant_id` in their primary key
- **Root segment**: Must have `name = NULL` (doesn't appear in XML)
- **Field packing**: Right-pad strings with spaces to fixed length
- **No validation**: Keep v1 simple—just parse, pack, and store
- **No hot-reload**: Routes load at startup only

### 6. When Creating Files

Use the package structure from `prompts/platform.prompt.md`:
- `com.fors.app` — Spring Boot main + config
- `com.fors.web` — Controllers
- `com.fors.routes` — Camel route builders
- `com.fors.service` — Services
- `com.fors.persistence.entity` — JPA entities
- `com.fors.persistence` — Repositories

### 7. Testing Your Implementation

After implementation:
1. Start PostgreSQL via Docker Compose
2. Run Liquibase migrations (should create schema + seed data)
3. Start Spring Boot app
4. Test endpoint: `POST http://localhost:8080/ingest/partmaterial` with XML from `prompts/examples.prompt.md`
5. Access admin UI: `http://localhost:8080/admin`
6. Verify data in `data_doc_hdr` and `data_doc_segment` tables

### 8. What NOT to Do

- Don't use SPA frameworks (React, Vue, Angular)
- Don't add Kafka, event buses, or workflow engines
- Don't "modernize" the fixed-width storage pattern
- Don't add validation logic beyond basic parsing
- Don't implement EDI parsers yet (marked as future in prompts)
- Don't use MyBatis or Flyway

---

## Summary

**Read `prompts/` directory first, follow the spec exactly, use the mandated tech stack, and preserve the era-appropriate design patterns.**
