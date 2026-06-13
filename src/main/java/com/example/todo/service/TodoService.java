package com.example.todo.service;

import com.example.todo.model.Todo;
import com.example.todo.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TodoService {
    private final TodoRepository repository;

    @Autowired
    public TodoService(TodoRepository repository) {
        this.repository = repository;
    }

    @Cacheable("todos")
    public List<Todo> getAllTodos() {
        return repository.findAll();
    }

    @CacheEvict(value = "todos", allEntries = true)
    public Todo createTodo(Todo todo) {
        return repository.save(todo);
    }

    public Optional<Todo> getTodoById(UUID id) {
        return repository.findById(id);
    }

    @CacheEvict(value = "todos", allEntries = true)
    public Optional<Todo> updateTodo(UUID id, Todo updatedTodo) {
        return repository.findById(id).map(todo -> {
            todo.setTitle(updatedTodo.getTitle());
            todo.setDescription(updatedTodo.getDescription());
            todo.setCompleted(updatedTodo.isCompleted());
            return repository.save(todo);
        });
    }

    @CacheEvict(value = "todos", allEntries = true)
    public void deleteTodoById(UUID id) {
        repository.deleteById(id);
    }
}
