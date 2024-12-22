# Todo App (Java)

Overview

The ToDo App is a simple yet powerful task management application built with Spring Boot. It allows users to create, manage, update, and delete tasks. This application uses a MariaDB database for storing tasks and Redis for caching frequently accessed data, ensuring fast performance. Each task is uniquely identified by a UUID to ensure non-colliding identifiers.

The app is designed to be easily extendable, providing a foundation for building more advanced features like authentication, user roles, and notifications.

Features
- Task Management: Perform CRUD (Create, Read, Update, Delete) operations on tasks.
- UUID-based Task Identification: Tasks are identified by unique UUIDs.
- Caching: Uses Redis to cache task data for faster retrieval.
- Database Integration: MariaDB is used to persist task data.
- Simple REST API: Exposes a set of RESTful endpoints to interact with the application.

Tech Stack
- Back-end: Java 17 with Spring Boot
- Database: MariaDB
- Caching: Redis
- Build Tool: Maven


## Setup
`mvn clean install && docker-compose up --build`


## API



1. Get All Todos
- GET /tasks
- Retrieves a list of all tasks.

2. Create a Todo
- POST /tasks
- Create a new task by providing a JSON body with the task details:

```
{
  "title": "New Task",
  "description": "Task Description",
  "completed": false
}
```

3. Get Todo by ID
- GET /tasks/{id}
- Retrieve a specific task by its UUID.

4. Update a Todo
- PUT /tasks/{id}
- Update the details of an existing task.

5. Delete a Todo
- DELETE /tasks/{id}
- Delete a task by its UUID.
