package com.example.todo.controller;

import com.example.todo.model.Todo;
import com.example.todo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TodoController {
    private final TodoService service;

    @Autowired
    public TodoController(TodoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Todo> getAllTodos() {
        return service.getAllTodos();
    }

    @PostMapping
    public Todo createTodo(@RequestBody Todo todo) {
        return service.createTodo(todo);
    }

    // Change Long to UUID for the id parameter
    @GetMapping("/{id}")
    public ResponseEntity<Todo> getTodoById(@PathVariable UUID id) {
        return service.getTodoById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Change Long to UUID for the id parameter
    @PutMapping("/{id}")
    public Todo updateTodo(@PathVariable UUID id, @RequestBody Todo todo) {
        return service.updateTodo(id, todo);
    }

    // Change Long to UUID for the id parameter
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodoById(@PathVariable UUID id) {
        service.deleteTodoById(id);
        return ResponseEntity.noContent().build();
    }
}