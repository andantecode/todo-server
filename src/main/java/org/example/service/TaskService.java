package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.constants.TaskStatus;
import org.example.model.Task;
import org.example.persist.TaskRepository;
import org.example.persist.entity.TaskEntity;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public Task add(String title, String description, LocalDate dueDate) {
        var e = TaskEntity.builder()
                .title(title)
                .description(description)
                .dueDate(Date.valueOf(dueDate))
                .status(TaskStatus.TODO)
                .build();

        var saved = this.taskRepository.save(e);

        return entityToObject(saved);
    }

    public List<Task> getAll() {

        return this.taskRepository.findAll().stream()
                .map(this::entityToObject)
                .collect(Collectors.toList());
    }

    public List<Task> getByDueDate(LocalDate dueDate) {
        return this.taskRepository.findAllByDueDate(Date.valueOf(dueDate)).stream()
                .map(this::entityToObject)
                .collect(Collectors.toList());
    }

    public List<Task> getByStatus(TaskStatus status) {
        return this.taskRepository.findAllByStatus(status).stream()
                .map(this::entityToObject)
                .collect(Collectors.toList());
    }

    public Task getOne(Long id) {
        var entity = this.getById(id);
        return this.entityToObject(entity);
    }

    private TaskEntity getById(Long id) {
        return this.taskRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(String.format("Not exists task id [%d]", id)));
    }

    private Task entityToObject(TaskEntity saved) {
        return Task.builder()
                .id(saved.getId())
                .title(saved.getTitle())
                .description(saved.getDescription())
                .status(saved.getStatus())
                .dueDate(saved.getDueDate().toString())
                .createdAt(saved.getCreatedAt().toLocalDateTime())
                .updatedAt(saved.getUpdatedAt().toLocalDateTime())
                .build();
    }
}
