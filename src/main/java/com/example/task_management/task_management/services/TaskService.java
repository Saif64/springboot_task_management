package com.example.task_management.task_management.services;

import com.example.task_management.task_management.dto.FilteredTaskDto;
import com.example.task_management.task_management.dto.TaskDto;
import com.example.task_management.task_management.entity.TaskEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface TaskService {
    TaskDto createTask(TaskDto taskDto);

    TaskDto getTaskById(String id);

    void deleteTask(String id);

    List<TaskEntity> getAllTasks(FilteredTaskDto filteredTaskDto);

    public List<TaskDto> parseCsv(MultipartFile file) throws IOException;

    public List<TaskEntity> createTasksFromCsv(List<TaskDto> tasksDto);
}
