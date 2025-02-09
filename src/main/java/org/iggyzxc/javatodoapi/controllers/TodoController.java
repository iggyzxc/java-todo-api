package org.iggyzxc.javatodoapi.controllers;

import org.iggyzxc.javatodoapi.entities.Todo;
import org.iggyzxc.javatodoapi.repositories.TodoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/todos")
public class TodoController {

    private final TodoRepository todoRepository;

    public TodoController(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @GetMapping
    public ResponseEntity<List<Todo>> getTodos() {
        List<Todo> todos = this.todoRepository.findAll();
        return ResponseEntity.ok(todos);
    }

    @GetMapping("/{todoId}")
    public ResponseEntity<Todo> getTodo(@PathVariable Long todoId) {
        return todoRepository.findById(todoId)
                .map(todo -> ResponseEntity.ok(todo))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Todo> newTodo(@RequestBody Todo todo) {
        Todo createdTodo = this.todoRepository.save(todo);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTodo);
    }

    @PutMapping("/{todoId}")
    public ResponseEntity<Todo> updateTodo(@PathVariable Long todoId, @RequestBody Todo todo) {
        return this.todoRepository.findById(todoId)
                .map(oldTodo -> {
                    oldTodo.setTitle(todo.getTitle());
                    oldTodo.setCompleted(todo.getCompleted());
                    Todo updatedTodo = todoRepository.save(oldTodo);
                    return ResponseEntity.ok(updatedTodo);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{todoId}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long todoId) {
        if (this.todoRepository.existsById(todoId)) {
            this.todoRepository.deleteById(todoId);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
