# domain.prompt.md — Domain Model

## Document Lifecycle

1. Document arrives via adapter (HTTP POST, FTP drop, MQ message)
2. Route maps to document definition
3. Parser decomposes document into segments
4. Each segment mapped to field definitions
5. Fields packed into fixed-width storage
6. Header record created with timestamp
7. Segment records written with sequence numbers

---

## Segment Hierarchy

- Documents contain 1+ segments
- Segments may nest (parent/child relationships)
- Root segment has no parent and must have `name = NULL`
- Segments with `name = NULL` do not appear in XML (abstract containers)
- Segments with non-null `name` match XML elements by that name
- Hierarchy defined in `def_doc_segment` table
- Runtime data uses sequence numbers, not parent pointers

---

## Field Packing Rules

- Fields ordered by `field_order` within segment
- Each field has fixed length
- Field offset = sum of preceding field lengths
- Values padded/truncated to fit length
- Entire segment packed into fixed-width record (string/bytes implementation-defined)

---

## Document Types

### XML Documents (Current)

- Root segment has `name = NULL` (abstract document container)
- Segments with non-null `name` match XML elements by that name
- Segments with `name = NULL` are logical containers (don't match XML elements)
- Fields = child elements or attributes
- Nesting = segment hierarchy
- Field names match XML child elements or attributes

### EDI Documents (Planned)

Future parser will map EDI segments and fields into the same canonical storage model.
