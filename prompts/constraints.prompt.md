# constraints.prompt.md — System Constraints and Design Philosophy

## Era-Appropriate Design

This system models B2B integration engines from the late 1990s/early 2000s, initially focused on XML over HTTP with architectural patterns that could later accommodate EDI and legacy transports.

---
a
## Design Philosophy

- **Database-centric**: Schema and configuration drive behavior
- **Performance over flexibility**: Fixed-width packing trades usability for speed/density
- **Metadata-driven**: Minimize code changes for new document types
- **Extensible**: Designed to accommodate future parsers (EDI, etc.) without core schema changes

---

## Technical Constraints

- All data scoped to tenant (multi-tenant isolation)
- String identifiers for all IDs
- Composite primary keys include tenant_id
- Fixed-width fields (no variable-length storage)
- Hierarchy via metadata, not foreign keys
- Sequence numbers preserve document order
- Single `segment_data` column per segment occurrence

---

## Non-Functional Requirements

- Sub-second ingestion for typical documents
- Support 100K+ documents/day
- Route configuration loaded at startup
- Service restart required for configuration changes
