package com.fullstack.backend.service;

import com.fullstack.backend.dao.TaskRepository;
import com.fullstack.backend.dao.UserRepository;
import com.fullstack.backend.domain.Task;
import com.fullstack.backend.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository,
                       UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public List<Task> findByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + userId));
        return taskRepository.findByUser(user);
    }

    public Task findById(Long taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada con id: " + taskId));
    }

    public Task createForUser(Long userId, Task taskData) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + userId));

        taskData.setId(null); // por si acaso
        taskData.setUser(user);
        return taskRepository.save(taskData);
    }

    public Task update(Long taskId, Task taskData) {
        Task existing = findById(taskId);
        existing.setTitle(taskData.getTitle());
        existing.setDescription(taskData.getDescription());
        existing.setCompleted(taskData.isCompleted());
        return taskRepository.save(existing);
    }

    public void delete(Long taskId) {
        Task existing = findById(taskId);
        taskRepository.delete(existing);
    }
}
