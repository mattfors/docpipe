# architecture.prompt.md — System Architecture

## System Purpose

B2B document ingestion engine supporting multiple transport protocols and XML business documents. Designed to accommodate future EDI parsers (X12/EDIFACT) using the same canonical storage model.

---

## Core Design Patterns

### Multi-Tenancy

- All definitions and data scoped to tenant
- Tenant isolation at database level
- Composite keys include tenant_id

### Adapter Pattern

- Pluggable transport adapters (HTTP, SFTP, MQ)
- Route configuration stored in database
- Routes loaded at startup from enabled configuration

### Canonical Data Model

- All inbound documents decomposed into segments/fields
- Segments stored as fixed-width packed records
- Document structure defined via metadata, not runtime data
- Hierarchy is defined in def_doc_segment and respected during parsing; runtime storage preserves ordering via seq_no and does not store parent pointers

### Schema-Driven Processing

- Document definitions drive parsing behavior
- Field definitions control packing/unpacking logic
- No hardcoded document types in application code

---

## Processing Pipeline

```
Inbound → Routing → Parsing → Packing → Persistence → Acknowledgment
```

---

## Key Architectural Decisions

- **Multi-tenant isolation**: Tenant ID in all composite keys ensures complete data isolation
- **String identifiers**: All IDs are strings for flexibility and external system integration
- **Composite keys**: Hierarchical key structure (tenant → doc → segment → field)
- **Fixed-width storage**: Optimized for storage density and performance
- **Metadata separation**: Definitions vs runtime data
- **Sequence-based ordering**: Maintains document order without storing parent pointers
- **Transport agnostic**: Core engine independent of adapter implementation
