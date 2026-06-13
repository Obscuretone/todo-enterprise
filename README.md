# Todo Enterprise

A deliberately over-engineered TODO API built with Spring Boot, Oracle, Redis, and Docker Compose.

The app exposes a small REST API for creating, reading, updating, and deleting tasks. The default Docker stack persists tasks in Oracle and caches the task list in Redis. A `local` profile uses in-memory H2 so the app can still run quickly without containers.

## Tech Stack

- Java 17
- Spring Boot 3
- Spring Web
- Spring Data JPA
- Oracle Database Free
- Redis
- Maven
- Docker Compose

## Quick Start

### Local H2 Mode

Use this when you want the API running without Oracle or Redis:

```sh
./mvnw spring-boot:run -Dspring-boot.run.profiles=local
```

The API starts on `http://localhost:8080`.

### Full Oracle + Redis Stack

Build the Spring Boot jar, then start the full stack:

```sh
./mvnw package
docker compose up --build
```

On the first run, Oracle may take a couple of minutes to initialize. Docker Compose waits for Oracle and Redis health checks before starting the app.

To stop the stack:

```sh
docker compose down
```

To remove persisted Oracle data too:

```sh
docker compose down -v
```

## Configuration

The default profile uses Oracle:

| Variable | Default | Description |
| --- | --- | --- |
| `DB_HOST` | `localhost` | Oracle host |
| `DB_PORT` | `1521` | Oracle listener port |
| `DB_SERVICE` | `FREEPDB1` | Oracle service name |
| `DB_USER` | `appuser` | Oracle app user |
| `DB_PASSWORD` | `appuser123` | Oracle app password |
| `REDIS_HOST` | `localhost` | Redis host |
| `REDIS_PORT` | `6379` | Redis port |
| `SERVER_PORT` | `8080` | HTTP server port |

Docker Compose sets these automatically for the containerized stack.

## API

### List Todos

```sh
curl http://localhost:8080/tasks
```

### Create Todo

```sh
curl -X POST http://localhost:8080/tasks \
  -H 'Content-Type: application/json' \
  -d '{"title":"Ship it","description":"Make the TODO app work","completed":false}'
```

### Get Todo

```sh
curl http://localhost:8080/tasks/{id}
```

### Update Todo

```sh
curl -X PUT http://localhost:8080/tasks/{id} \
  -H 'Content-Type: application/json' \
  -d '{"title":"Ship it harder","description":"Now with Oracle","completed":true}'
```

### Delete Todo

```sh
curl -X DELETE http://localhost:8080/tasks/{id}
```

## Validation

Run unit tests:

```sh
./mvnw test
```

Run a quick full-stack smoke test after `docker compose up --build`:

```sh
created=$(curl -fsS -H 'Content-Type: application/json' \
  -d '{"title":"Oracle smoke test","description":"CRUD against Docker","completed":false}' \
  http://localhost:8080/tasks)

id=$(printf '%s' "$created" | sed -E 's/.*"id":"([^"]+)".*/\1/')
curl -fsS "http://localhost:8080/tasks/$id"
curl -fsS -X DELETE "http://localhost:8080/tasks/$id"
```

Check the Oracle table directly:

```sh
docker compose exec oracle sqlplus appuser/appuser123@//localhost:1521/FREEPDB1
```

Then run:

```sql
select count(*) from todo;
```

## Notes

- The `local` profile uses H2 and simple in-memory caching.
- The default Docker stack uses Oracle and Redis.
- Redis caches the task list; create, update, and delete operations evict the list cache.
