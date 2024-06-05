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
