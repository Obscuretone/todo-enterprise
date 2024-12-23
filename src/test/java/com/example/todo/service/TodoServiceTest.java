package com.example.todo.service;

import com.example.todo.model.Todo;
import com.example.todo.repository.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TodoServiceTest {

    @Mock
    private TodoRepository repository;

    @InjectMocks
    private TodoService todoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllTodos_ShouldReturnTodoList() {
        // Arrange
        List<Todo> mockTodos = Arrays.asList(
                new Todo(UUID.randomUUID(), "Task 1", "Description 1", false),
                new Todo(UUID.randomUUID(), "Task 2", "Description 2", true));
        when(repository.findAll()).thenReturn(mockTodos);

        // Act
        List<Todo> result = todoService.getAllTodos();

        // Assert
        assertEquals(2, result.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void createTodo_ShouldSaveAndReturnTodo() {
        // Arrange
        Todo newTodo = new Todo(UUID.randomUUID(), "Task", "Description", false);
        when(repository.save(newTodo)).thenReturn(newTodo);

        // Act
        Todo result = todoService.createTodo(newTodo);

        // Assert
        assertNotNull(result);
        assertEquals("Task", result.getTitle());
        verify(repository, times(1)).save(newTodo);
    }

    @Test
    void getTodoById_ShouldReturnTodoIfExists() {
        // Arrange
        UUID id = UUID.randomUUID();
        Todo todo = new Todo(id, "Task", "Description", false);
        when(repository.findById(id)).thenReturn(Optional.of(todo));

        // Act
        Optional<Todo> result = todoService.getTodoById(id);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Task", result.get().getTitle());
        verify(repository, times(1)).findById(id);
    }

    @Test
    void getTodoById_ShouldReturnEmptyIfNotFound() {
        // Arrange
        UUID id = UUID.randomUUID();
        when(repository.findById(id)).thenReturn(Optional.empty());

        // Act
        Optional<Todo> result = todoService.getTodoById(id);

        // Assert
        assertFalse(result.isPresent());
        verify(repository, times(1)).findById(id);
    }

    @Test
    void updateTodo_ShouldUpdateAndReturnTodo() {
        // Arrange
        UUID id = UUID.randomUUID();
        Todo existingTodo = new Todo(id, "Task", "Description", false);
        Todo updatedTodo = new Todo(id, "Updated Task", "Updated Description", true);

        when(repository.findById(id)).thenReturn(Optional.of(existingTodo));
        when(repository.save(any(Todo.class))).thenReturn(updatedTodo);

        // Act
        Todo result = todoService.updateTodo(id, updatedTodo);

        // Assert
        assertNotNull(result);
        assertEquals("Updated Task", result.getTitle());
        verify(repository, times(1)).findById(id);
        verify(repository, times(1)).save(existingTodo);
    }

    @Test
    void updateTodo_ShouldThrowIfNotFound() {
        // Arrange
        UUID id = UUID.randomUUID();
        Todo updatedTodo = new Todo(id, "Updated Task", "Updated Description", true);
        when(repository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> todoService.updateTodo(id, updatedTodo));
        assertEquals("ToDo not found", exception.getMessage());
        verify(repository, times(1)).findById(id);
        verify(repository, never()).save(any(Todo.class));
    }

    @Test
    void deleteTodoById_ShouldDeleteTodo() {
        // Arrange
        UUID id = UUID.randomUUID();
        doNothing().when(repository).deleteById(id);

        // Act
        todoService.deleteTodoById(id);

        // Assert
        verify(repository, times(1)).deleteById(id);
    }
}