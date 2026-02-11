# Getting Started

## Prerequisites

- Java 17 or later
- Maven 3.6+
- Docker (for PostgreSQL)

## Quick Start

### 1. Start PostgreSQL

```bash
docker-compose up -d
```

### 2. Build the Application

```bash
mvn clean install
```

### 3. Run the Application

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

### 4. Test the Keepalive Endpoint

```bash
curl http://localhost:8080/api/keepalive
```

Expected response:
```json
{
  "status": "ok",
  "application": "docpipe",
  "timestamp": "2024-01-15T10:30:00.000Z"
}
```

### 5. Check Health Endpoint

```bash
curl http://localhost:8080/actuator/health
```

## Stopping

Stop the application: `Ctrl+C`

Stop PostgreSQL:
```bash
docker-compose down
```

## Next Steps

Schema migrations and application logic will be implemented according to the specifications in the `prompts/` directory.
