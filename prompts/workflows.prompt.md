# workflows.prompt.md — Key Workflows

## Route Configuration

1. Admin creates `def_adapter` entry
2. Admin creates `def_adapter_route` with path and `def_doc_id`
3. Service loads enabled routes on startup
4. HTTP endpoints materialized for each route
5. Service restart required for route configuration changes

---

## Document Definition

1. Create `def_doc` for document type
2. Define segment hierarchy in `def_doc_segment`
3. Define fields per segment in `def_doc_field`
4. Parser uses definitions to decompose inbound documents

---

## Document Ingestion

1. POST to configured route path
2. Request body contains document bytes
3. Route identifies `def_doc_id`
4. Parser selected based on document type
5. Document parsed into segments
6. Segments packed using field definitions
7. `data_doc_hdr` created
8. `data_doc_segment` records written
9. Response returned (acknowledgment/error)

---

## Segment Unpacking

1. Query `data_doc_segment` by criteria
2. Load `segment_data` column
3. Lookup `def_doc_field` for segment definition
4. Extract fields using offset arithmetic
5. Return as structured data
