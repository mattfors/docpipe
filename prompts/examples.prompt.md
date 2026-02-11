# examples.prompt.md — Reference Examples & Seed Data

## Purpose

Provides concrete examples for implementing seed data in initial Liquibase migrations.

---

## Example: Part Material Document (OAGIS-inspired)

### Source XML Document

This example is inspired by OAGIS ShowPartMaster BOD structure.

```xml
<PartMaterial>
  <Header>
    <DocNumber>PM-2024-001</DocNumber>
    <Date>2024-01-15</Date>
    <Supplier>ACME-CORP</Supplier>
  </Header>
  <Part>
    <PartNumber>BOLT-M8-50</PartNumber>
    <Description>M8 Hex Bolt 50mm Zinc Plated</Description>
    <Material>
      <Code>STEEL-304</Code>
      <Weight>0.025</Weight>
      <UnitCost>0.15</UnitCost>
    </Material>
  </Part>
  <Part>
    <PartNumber>NUT-M8</PartNumber>
    <Description>M8 Hex Nut Zinc Plated</Description>
    <Material>
      <Code>STEEL-304</Code>
      <Weight>0.008</Weight>
      <UnitCost>0.05</UnitCost>
    </Material>
  </Part>
  <Part>
    <PartNumber>WASHER-M8</PartNumber>
    <Description>M8 Flat Washer</Description>
    <Material>
      <Code>STEEL-304</Code>
      <Weight>0.003</Weight>
      <UnitCost>0.02</UnitCost>
    </Material>
  </Part>
</PartMaterial>
```

---

## Seed Data Tables

### def_tenant

| tenant_id | name |
|-----------|--------------|
| demo-tenant | Demo Tenant |

---

### def_adapter

| tenant_id | def_adapter_id | name | enabled |
|-----------|----------------|--------------|---------|
| demo-tenant | http-adapter | HTTP Adapter | true |

---

### def_doc

| tenant_id | def_doc_id | name |
|-----------|------------|---------------|
| demo-tenant | pm-doc | PartMaterial |

---

### def_adapter_route

| tenant_id | def_adapter_id | def_adapter_route_id | def_doc_id | path | enabled |
|-----------|----------------|---------------------|-----------|----------------------|---------|
| demo-tenant | http-adapter | pm-route | pm-doc | /ingest/partmaterial | true |

---

### def_doc_segment

| tenant_id | def_doc_id | def_doc_segment_id | parent_def_doc_segment_id | name |
|-----------|------------|-------------------|--------------------------|--------------|
| demo-tenant | pm-doc | root | NULL | NULL |
| demo-tenant | pm-doc | pm-seg | root | PartMaterial |
| demo-tenant | pm-doc | hdr-seg | pm-seg | Header |
| demo-tenant | pm-doc | part-seg | pm-seg | Part |
| demo-tenant | pm-doc | mat-seg | part-seg | Material |

**Notes:**
- `root` segment has `name = NULL` (abstract container)
- `root` has no parent (`parent_def_doc_segment_id = NULL`)
- Hierarchy: root → PartMaterial → Header, Part → Material

---

### def_doc_field

**Header segment fields:**

| tenant_id | def_doc_id | def_doc_segment_id | def_doc_field_id | name | field_order | length |
|-----------|------------|-------------------|-----------------|-----------|-------------|--------|
| demo-tenant | pm-doc | hdr-seg | doc-num | DocNumber | 1 | 20 |
| demo-tenant | pm-doc | hdr-seg | date | Date | 2 | 10 |
| demo-tenant | pm-doc | hdr-seg | supplier | Supplier | 3 | 30 |

**Part segment fields:**

| tenant_id | def_doc_id | def_doc_segment_id | def_doc_field_id | name | field_order | length |
|-----------|------------|-------------------|-----------------|-------------|-------------|--------|
| demo-tenant | pm-doc | part-seg | part-num | PartNumber | 1 | 20 |
| demo-tenant | pm-doc | part-seg | desc | Description | 2 | 50 |

**Material segment fields:**

| tenant_id | def_doc_id | def_doc_segment_id | def_doc_field_id | name | field_order | length |
|-----------|------------|-------------------|-----------------|----------|-------------|--------|
| demo-tenant | pm-doc | mat-seg | code | Code | 1 | 15 |
| demo-tenant | pm-doc | mat-seg | weight | Weight | 2 | 10 |
| demo-tenant | pm-doc | mat-seg | cost | UnitCost | 3 | 10 |

---

## Runtime Data Example

Sample document processed from the XML above.

### data_doc_hdr

| tenant_id | data_doc_hdr_id | def_doc_id | received_at |
|-----------|-----------------|-----------|---------------------|
| demo-tenant | doc-001 | pm-doc | 2024-01-15T10:30:00Z |

---

### data_doc_segment

| tenant_id | data_doc_hdr_id | data_doc_segment_id | def_doc_id | def_doc_segment_id | seq_no | segment_data |
|-----------|----------------|-------------------|------------|-------------------|--------|--------------|
| demo-tenant | doc-001 | seg-001 | pm-doc | root | 1 | (empty) |
| demo-tenant | doc-001 | seg-002 | pm-doc | pm-seg | 2 | (empty) |
| demo-tenant | doc-001 | seg-003 | pm-doc | hdr-seg | 3 | (see below) |
| demo-tenant | doc-001 | seg-004 | pm-doc | part-seg | 4 | (see below) |
| demo-tenant | doc-001 | seg-005 | pm-doc | mat-seg | 5 | (see below) |
| demo-tenant | doc-001 | seg-006 | pm-doc | part-seg | 6 | (see below) |
| demo-tenant | doc-001 | seg-007 | pm-doc | mat-seg | 7 | (see below) |
| demo-tenant | doc-001 | seg-008 | pm-doc | part-seg | 8 | (see below) |
| demo-tenant | doc-001 | seg-009 | pm-doc | mat-seg | 9 | (see below) |

---

## Packed Segment Data Examples

### seg-003 (Header segment)

**Fields (60 bytes total):**
- DocNumber (20): `"PM-2024-001         "` (right-padded with spaces)
- Date (10): `"2024-01-15"`
- Supplier (30): `"ACME-CORP                     "` (right-padded)

**segment_data:**
```
PM-2024-001         2024-01-15ACME-CORP                     
```

---

### seg-004 (First Part segment)

**Fields (70 bytes total):**
- PartNumber (20): `"BOLT-M8-50          "`
- Description (50): `"M8 Hex Bolt 50mm Zinc Plated                     "`

**segment_data:**
```
BOLT-M8-50          M8 Hex Bolt 50mm Zinc Plated                     
```

---

### seg-005 (First Material segment)

**Fields (35 bytes total):**
- Code (15): `"STEEL-304      "`
- Weight (10): `"0.025     "`
- UnitCost (10): `"0.15      "`

**segment_data:**
```
STEEL-304      0.025     0.15      
```

---

### seg-006 (Second Part segment)

**Fields (70 bytes total):**
- PartNumber (20): `"NUT-M8              "`
- Description (50): `"M8 Hex Nut Zinc Plated                           "`

**segment_data:**
```
NUT-M8              M8 Hex Nut Zinc Plated                           
```

---

### seg-007 (Second Material segment)

**Fields (35 bytes total):**
- Code (15): `"STEEL-304      "`
- Weight (10): `"0.008     "`
- UnitCost (10): `"0.05      "`

**segment_data:**
```
STEEL-304      0.008     0.05      
```

---

### seg-008 (Third Part segment)

**Fields (70 bytes total):**
- PartNumber (20): `"WASHER-M8           "`
- Description (50): `"M8 Flat Washer                                    "`

**segment_data:**
```
WASHER-M8           M8 Flat Washer                                    
```

---

### seg-009 (Third Material segment)

**Fields (35 bytes total):**
- Code (15): `"STEEL-304      "`
- Weight (10): `"0.003     "`
- UnitCost (10): `"0.02      "`

**segment_data:**
```
STEEL-304      0.003     0.02      
```

---

## Implementation Notes

### Packing Algorithm

For each segment:
1. Query `def_doc_field` for segment definition, order by `field_order`
2. For each field in order:
   - Extract value from XML element
   - Pad or truncate to `length` (right-pad with spaces for strings)
   - Append to segment_data buffer
3. Store resulting packed string in `segment_data` column

### Unpacking Algorithm

To display or query segment data:
1. Query `def_doc_field` for segment definition, order by `field_order`
2. Calculate field offsets:
   - First field offset = 0
   - Each subsequent field offset = previous offset + previous field length
3. Extract each field:
   - value = segment_data.substring(offset, offset + length)
   - trim trailing spaces if needed

### Open Business Standard References

This example is inspired by:
- **OAGIS ShowPartMaster BOD**: Standard for part/material master data
- **UBL Catalogue**: Item specification documents
- Simplified for demonstration purposes while maintaining realistic structure

The document pattern (Header + repeating Part + nested Material) is common in:
- Bill of Materials (BOM)
- Part catalogues
- Material specifications
- Supplier catalogs
