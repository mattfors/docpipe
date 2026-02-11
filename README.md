# 📄🚰  docpipe

A B2B document ingestion engine that models late 1990s/early 2000s enterprise integration system design patterns—when EDI platforms evolved to support XML and HTTP.

## What It Does

Accepts structured XML documents via HTTP, decomposes them into configured segments and fields, and stores them as fixed-width packed records in a canonical PostgreSQL schema. Schema-driven, database-centric, multi-tenant.

## Design Philosophy

Intentionally recreates the architectural patterns of that era (database-centric configuration, fixed-width storage, metadata-driven parsing) using modern Java/Spring Boot tooling.

## Documentation

Complete specifications are in the `prompts/` directory:

- [schema.prompt.md](prompts/schema.prompt.md) — Database schema
- [architecture.prompt.md](prompts/architecture.prompt.md) — Design patterns
- [domain.prompt.md](prompts/domain.prompt.md) — Domain model
- [workflows.prompt.md](prompts/workflows.prompt.md) — Workflows
- [constraints.prompt.md](prompts/constraints.prompt.md) — Design constraints
- [ui.prompt.md](prompts/ui.prompt.md) — Admin UI spec
- [platform.prompt.md](prompts/platform.prompt.md) — Tech stack & conventions
- [examples.prompt.md](prompts/examples.prompt.md) — Seed data & examples

## Status

🚧 **Specification complete. Implementation in progress.**

