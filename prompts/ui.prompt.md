# ui.prompt.md — User Interface Requirements

## Purpose

Provide a web-based administration interface for managing document definitions and viewing processed document data.

---

## UI Structure

The interface is divided into three main sections:

### 1. Configuration Management
### 2. Data Viewing
### 3. Testing & Development Tools

---

## Configuration Management UI

### Tree Navigation

A hierarchical expandable tree structure for navigating and managing configuration.

**Expand/Collapse Behavior:**
- Use **+** (plus) symbol for collapsed nodes
- Use **−** (minus) symbol for expanded nodes
- Clicking the symbol or node label toggles expansion
- Tree nodes are collapsible using HTML `<details>` elements

**Tree Structure:**

```
└── Tenant: acme-inc
    ├── Inbound
    │   ├── Adapters
    │   │   └── HTTP Adapter (http-1)
    │   │       └── Routes
    │   │           └── /ingest/partmaterial → pm-doc
    │   └── Documents
    │       └── PartMaterial (pm-doc)
    │           └── Segments
    │               ├── root (NULL)
    │               └── PartMaterial (pm-seg)
    │                   ├── Header (hdr-seg)
    │                   │   └── Fields
    │                   │       ├── DocNumber (doc-num)
    │                   │       ├── Date (date)
    │                   │       └── Supplier (supplier)
    │                   └── Part (part-seg)
    │                       ├── Fields
    │                       │   ├── PartNumber (part-num)
    │                       │   └── Description (desc)
    │                       └── Material (mat-seg)
    │                           └── Fields
    │                               ├── Code (code)
    │                               ├── Weight (weight)
    │                               └── UnitCost (cost)
    └── Outbound
        └── (Future: outbound adapters and routing)
```

### Tree Node Actions

Each node type supports context-appropriate actions:

**Tenant Node**
- View tenant details
- Switch tenant (if multiple tenants)
- Create inbound/outbound configurations

**Adapter Node**
- View adapter configuration
- Enable/disable adapter
- Create new route
- Edit adapter settings

**Route Node**
- View route details (path, target document)
- Enable/disable route
- Edit route mapping
- Delete route

**Document Node**
- View document metadata
- Create root segment
- Delete document (if no data exists)
- View inbound data count

**Segment Node**
- View segment details (name, parent)
- Create child segment
- Create field
- Edit segment
- Delete segment (if no data exists)
- Visualize segment hierarchy

**Field Node**
- View field details (name, order, length)
- Edit field
- Reorder fields
- Delete field (if no data exists)

### CRUD Forms

**Create/Edit Forms** for each entity type:

**Tenant Form**
- tenant_id (string, unique)
- name (string)

**Adapter Form**
- def_adapter_id (string, unique within tenant)
- name (string)
- enabled (boolean)

**Route Form**
- def_adapter_route_id (string, unique within adapter)
- path (string, validates as URL path)
- def_doc_id (dropdown: select from tenant's documents)
- enabled (boolean)

**Document Form**
- def_doc_id (string, unique within tenant)
- name (string)

**Segment Form**
- def_doc_segment_id (string, unique within document)
- parent_def_doc_segment_id (dropdown: select from document's segments, nullable)
- name (string, nullable)
- Note: Root segment must have name = NULL

**Field Form**
- def_doc_field_id (string, unique within segment)
- name (string)
- field_order (integer, auto-suggest next available)
- length (integer, positive)

### Validation Rules

- **String IDs**: Must be valid identifiers (alphanumeric, hyphens, underscores)
- **Root segment**: Exactly one segment per document with no parent
- **Root segment name**: Must be NULL
- **Field order**: Must be unique within segment
- **Field length**: Must be positive integer
- **Route path**: Must be valid URL path (start with /)
- **Composite uniqueness**: Prevent duplicate IDs within scope

### Visual Indicators

- **Enabled/Disabled**: Color coding or icon for adapter/route status
- **Root segment**: Special icon or styling for root segment
- **NULL name**: Display "(abstract)" or similar for segments with name = NULL
- **Field order**: Display fields sorted by field_order
- **Data exists**: Disable delete if runtime data references definition

---

## Data Viewing UI

### Document List View

**Search/Filter Panel**
- Tenant selector (if multi-tenant access)
- Document type selector (dropdown of def_doc records)
- Date range filter (received_at)
- Document ID search (data_doc_hdr_id)

**Results Table**
Column layout:
- data_doc_hdr_id
- def_doc_id (document type)
- received_at
- Segment count
- Actions (View, Export)

### Document Detail View

**Header Information**
- tenant_id
- data_doc_hdr_id
- def_doc_id (document type name)
- received_at

**Segment List**

Tabular view of all segments for the document:
Column layout:
- seq_no (ascending order)
- def_doc_segment_id
- Segment name (from definition)
- Actions (View Unpacked)

**Segment Detail Modal**

When viewing a specific segment:
- Display segment metadata (seq_no, segment type)
- Show unpacked fields in table:
  - Field name
  - Field value (extracted from segment_data)
  - Field order
  - Length

**Unpacking Logic**

To display segment data:
1. Load segment_data column
2. Query def_doc_field for segment definition
3. Sort fields by field_order
4. Extract each field using offset arithmetic:
   - offset = sum of preceding field lengths
   - value = segment_data.substring(offset, offset + length)
5. Display field name and trimmed value

### Export Options

- **Single document**: Export as original XML or JSON representation
- **Multiple documents**: Export as CSV (flattened) or JSON array
- **Segment data**: Export unpacked segment as CSV/JSON

---

## UI Navigation Flow

### Configuration Workflow

1. Select tenant (if multiple)
2. Navigate tree to desired level
3. Perform CRUD operation
4. Save changes
5. System restarts or reloads config (depending on hot-reload support)

### Data Viewing Workflow

1. Select tenant (if multi-tenant)
2. Filter/search for documents
3. Click document to view details
4. Expand segments to view unpacked data
5. Export if needed

---

## Testing & Development Tools UI

### Route Testing Interface

A Swagger-like interface for testing configured HTTP routes.

### Route Explorer Tree

Hierarchical view of all configured routes:

```
└── Tenant: acme-inc
    └── Inbound Routes
        └── HTTP Adapter (http-1)
            ├── POST /ingest/partmaterial → pm-doc (enabled)
            ├── POST /ingest/invoice → inv-doc (enabled)
            └── POST /ingest/shipment → ship-doc (disabled)
```

**Visual Indicators**
- HTTP method badge (POST, GET, etc.)
- Enabled/disabled status
- Target document type
- Clickable to open test interface

### Test Request Panel

When a route is selected, display a test interface:

**Route Information**
- Full URL: `http://localhost:8080/ingest/partmaterial`
- HTTP Method: POST
- Target Document: PartMaterial (pm-doc)
- Status: Enabled/Disabled

**Request Builder**

**Headers Section**
- Editable key-value pairs
- Pre-populated with common headers:
  - `Content-Type: application/xml`
  - `Content-Type: text/plain`
- Add/remove custom headers

**Body Section**
- Text area for raw document content
- Syntax highlighting (XML, JSON, text)
- Option to upload file
- **Generate Test Data** button

**Actions**
- **Send Request** button
- **Generate Test Data** button
- **Save as Test Case** (future)

### Test Data Generator

Modal dialog to generate valid test documents:

**Generator Options**

1. **Schema-Based Generation**
   - Use document definition to generate valid XML
   - Query def_doc_segment and def_doc_field
   - Create realistic sample values based on field names
   - Respect field lengths

2. **From Template**
   - Select from saved example documents
   - Modify template values

3. **Random Data**
   - Generate random strings within field length constraints
   - Optional: use realistic data generators (names, dates, numbers)

**Generated Output**

Display generated document in text area:
- Allow editing before sending
- Copy to clipboard button
- Insert into request body button

### Example Generator Logic

For PartMaterial document (pm-doc):

```xml
<PartMaterial>
  <Header>
    <DocNumber>PM-2024-001</DocNumber>
    <Date>2024-01-15</Date>
    <Supplier>ACME-CORP</Supplier>
  </Header>
  <Part>
    <PartNumber>BOLT-M8-50</PartNumber>
    <Description>M8 Bolt 50mm</Description>
    <Material>
      <Code>STEEL-304</Code>
      <Weight>0.025</Weight>
      <UnitCost>0.15</UnitCost>
    </Material>
  </Part>
</PartMaterial>
```

**Generation Rules**
- Root segment with name = NULL → skip in XML
- Segments with non-null name → create XML element
- Fields → create child elements or attributes
- Respect hierarchy from def_doc_segment.parent_def_doc_segment_id
- Limit values to field.length
- Generate realistic values based on field names:
  - "Date" → current date
  - "Number", "ID" → sequential or random
  - "Name", "Description" → placeholder text
  - Numeric fields → sample numbers

### Response Display

**Response Section**

After sending request, display:

**Status**
- HTTP status code (200, 400, 500, etc.)
- Status message
- Response time (ms)

**Headers**
- Display response headers
- Collapsible section

**Body**
- Response body (acknowledgment, error message)
- Syntax highlighting if JSON/XML
- Copy to clipboard

**Created Document Reference**
- If successful, show link to view created document in Data Viewing UI
- Display data_doc_hdr_id
- "View Document" button → navigates to document detail

### Test History

**Request History Panel**
- List of recent test requests (session-based)
- Show: method, path, status, timestamp
- Click to reload request configuration
- Clear history button

**Saved Test Cases** (Future)
- Save request configurations with names
- Organize by tenant/document type
- Quick replay saved tests

### Swagger-Style Documentation

**API Documentation View**

Auto-generated based on configured routes:

**For Each Route**
- Path and method
- Description (from document name)
- Request body schema (derived from document definition)
- Response codes and descriptions:
  - 200: Document ingested successfully
  - 400: Invalid document format
  - 404: Route not found
  - 500: Internal server error
- Example request (generated test data)
- Example response

**OpenAPI Spec Export**
- Generate OpenAPI 3.0 spec from route configuration
- Download as YAML or JSON
- Import into external API testing tools (Postman, Insomnia)

### Testing Workflow

1. Navigate to Testing section
2. Select tenant
3. Browse route tree, select route to test
4. Click "Generate Test Data"
5. Review/modify generated document
6. Click "Send Request"
7. Review response
8. If successful, view created document
9. Repeat or save test case

---

## Technical Considerations

### Performance

- Lazy load tree nodes (load children on expand)
- Paginate document list results
- Cache document definitions for unpacking
- Limit segment data preview size
- Cache document schemas for test data generation
- Store test history in session storage (not database)

### Testing UI Considerations

- Test requests should not require authentication bypass in production
- Rate limit test requests to prevent abuse
- Maximum request body size validation
- Syntax validation before sending request
- WebSocket or polling for long-running ingestion status (future)

### Permissions (Future)

- Tenant-scoped access control
- Read-only vs admin roles
- Audit logging for configuration changes
- Testing UI access control (dev/test environments only)
- Separate permissions for test data generation vs viewing production data

### Responsive Design

- Tree navigation in collapsible sidebar on mobile
- Responsive tables for document lists
- Modal forms for create/edit operations
