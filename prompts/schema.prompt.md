# schema.prompt.md — Canonical Schema Definition

## Purpose

The schema supports ingestion of structured documents (initially XML) by decomposing them into configured segments and fields and storing them in canonical fixed-width segment records.

The schema separates:

- **definition tables** (how documents are parsed and how inbound routes map to document definitions)
- **runtime data tables** (stored inbound document instances and their parsed segment records)

---

## Tenant Table

### def_tenant

Defines isolated tenants for multi-tenant document processing.

**Columns**

- `tenant_id` — primary key (string)
- `name` — tenant name

**Rule**

- All document definitions and data are scoped to a tenant.

---

## Adapter Configuration Tables (`def_*`)

These tables define how documents are received (initially HTTP) and which document definition each endpoint maps to.

### def_adapter

Defines an input adapter instance (HTTP only in the initial version).

**Columns**

- `tenant_id` — FK to `def_tenant` (part of composite key)
- `def_adapter_id` — string identifier (part of composite key)
- `name` — adapter name
- `enabled` — boolean

**Primary Key**: `(tenant_id, def_adapter_id)`

---

### def_adapter_route

Defines a dynamically-created HTTP route that accepts inbound documents.

**Columns**

- `tenant_id` — FK to `def_tenant` (part of composite key)
- `def_adapter_id` — FK to `def_adapter` (part of composite key)
- `def_adapter_route_id` — string identifier (part of composite key)
- `def_doc_id` — FK to `def_doc`
- `path` — HTTP path
- `enabled` — boolean

**Primary Key**: `(tenant_id, def_adapter_id, def_adapter_route_id)`

**Foreign Keys**:
- `(tenant_id, def_adapter_id)` → `def_adapter`
- `(tenant_id, def_doc_id)` → `def_doc`

**Rules**

- On startup, the service loads **enabled** routes and materializes HTTP POST endpoints.
- Posting to a route ingests a document using the referenced `def_doc_id`.

---

## Definition Tables (`def_*`)

These tables define how documents are parsed.

### def_doc

Defines a document type.

**Columns**

- `tenant_id` — FK to `def_tenant` (part of composite key)
- `def_doc_id` — string identifier (part of composite key)
- `name` — document name/type

**Primary Key**: `(tenant_id, def_doc_id)`

**Rule**

- Each inbound document references exactly one document definition.

---

### def_doc_segment

Defines segments belonging to a document and their hierarchy.

**Columns**

- `tenant_id` — FK to `def_tenant` (part of composite key)
- `def_doc_id` — FK to `def_doc` (part of composite key)
- `def_doc_segment_id` — string identifier (part of composite key)
- `parent_def_doc_segment_id` — nullable FK to parent segment
- `name` — segment name (XML tag), nullable

**Primary Key**: `(tenant_id, def_doc_id, def_doc_segment_id)`

**Foreign Keys**:
- `(tenant_id, def_doc_id)` → `def_doc`
- `(tenant_id, def_doc_id, parent_def_doc_segment_id)` → `def_doc_segment`

**Rules**

- Segments form a hierarchy via parent reference.
- Exactly one segment per document has no parent (root segment).
- Root segment must have `name = NULL` (does not appear in XML).
- Non-root segments with `name = NULL` are containers that don't appear in XML.
- Segments with non-null `name` match XML elements by that name.

---

### def_doc_field

Defines fixed-width fields within a segment.

**Columns**

- `tenant_id` — FK to `def_tenant` (part of composite key)
- `def_doc_id` — FK to `def_doc` (part of composite key)
- `def_doc_segment_id` — FK to `def_doc_segment` (part of composite key)
- `def_doc_field_id` — string identifier (part of composite key)
- `name` — field name (XML child element or attribute)
- `field_order` — position within segment
- `length` — fixed storage width

**Primary Key**: `(tenant_id, def_doc_id, def_doc_segment_id, def_doc_field_id)`

**Foreign Keys**:
- `(tenant_id, def_doc_id, def_doc_segment_id)` → `def_doc_segment`

**Rules**

- Field order is unique within a segment.
- Field offsets are derived from cumulative field lengths in `field_order` order.

---

## Runtime Data Tables (`data_*`)

These tables store parsed inbound document data.

### data_doc_hdr

Stores inbound document instances.

**Columns**

- `tenant_id` — FK to `def_tenant` (part of composite key)
- `data_doc_hdr_id` — string identifier (part of composite key)
- `def_doc_id` — FK to `def_doc`
- `received_at` — ingestion timestamp

**Primary Key**: `(tenant_id, data_doc_hdr_id)`

**Foreign Keys**:
- `(tenant_id, def_doc_id)` → `def_doc`

**Rule**

- One row per inbound document.

---

### data_doc_segment

Stores segment occurrences for a document.

**Columns**

- `tenant_id` — FK to `def_tenant` (part of composite key)
- `data_doc_hdr_id` — FK to `data_doc_hdr` (part of composite key)
- `data_doc_segment_id` — string identifier (part of composite key)
- `def_doc_id` — FK to `def_doc`
- `def_doc_segment_id` — FK to `def_doc_segment`
- `seq_no` — segment sequence number in the inbound document
- `segment_data` — fixed-width packed segment data

**Primary Key**: `(tenant_id, data_doc_hdr_id, data_doc_segment_id)`

**Foreign Keys**:
- `(tenant_id, data_doc_hdr_id)` → `data_doc_hdr`
- `(tenant_id, def_doc_id, def_doc_segment_id)` → `def_doc_segment`

**Rules**

- One row per parsed segment occurrence.
- Field values are packed into `segment_data` using the segment's field definitions.
- Segment hierarchy is not stored in runtime data; it is derived from definitions and ordering.

---

## Logical Structure

### Definitions

```
def_tenant
  ├── def_adapter
  │     └── def_adapter_route ──▶ def_doc
  │                                   └── def_doc_segment
  │                                             └── def_doc_field
  └── def_doc (scoped to tenant)
```

### Runtime Data

```
def_tenant
  └── data_doc_hdr
        └── data_doc_segment
```
