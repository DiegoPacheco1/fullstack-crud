package com.fullstack.backend.controller;

import com.fullstack.backend.domain.Task;
import com.fullstack.backend.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // GET /api/users/{userId}/tasks
    @GetMapping("/users/{userId}/tasks")
    public ResponseEntity<List<Task>> getTasksByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(taskService.findByUserId(userId));
    }

    // GET /api/tasks/{taskId}
    @GetMapping("/tasks/{taskId}")
    public ResponseEntity<Task> getTask(@PathVariable Long taskId) {
        return ResponseEntity.ok(taskService.findById(taskId));
    }

    // POST /api/users/{userId}/tasks
    @PostMapping("/users/{userId}/tasks")
    public ResponseEntity<Task> createTaskForUser(@PathVariable Long userId,
                                                  @RequestBody Task task) {
        Task created = taskService.createForUser(userId, task);
        return ResponseEntity
                .created(URI.create("/api/tasks/" + created.getId()))
                .body(created);
    }

    // PUT /api/tasks/{taskId}
    @PutMapping("/tasks/{taskId}")
    public ResponseEntity<Task> updateTask(@PathVariable Long taskId,
                                           @RequestBody Task task) {
        return ResponseEntity.ok(taskService.update(taskId, task));
    }

    // DELETE /api/tasks/{taskId}
    @DeleteMapping("/tasks/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
        taskService.delete(taskId);
        return ResponseEntity.noContent().build();
    }
}
