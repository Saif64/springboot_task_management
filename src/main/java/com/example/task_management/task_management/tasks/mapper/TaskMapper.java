package com.example.task_management.task_management.tasks.mapper;

import com.example.task_management.task_management.tasks.dto.TaskDto;
import com.example.task_management.task_management.tasks.entity.TaskEntity;

public class TaskMapper {
    public static TaskEntity toTasks(TaskDto taskDto) {
        return new TaskEntity(taskDto.getId(), taskDto.getName(), taskDto.getDescription(), taskDto.getStatus());
    }

    public static TaskDto toTaskDto(TaskEntity taskEntity) {
        return new TaskDto(taskEntity.getId(), taskEntity.getName(), taskEntity.getDescription(), taskEntity.getStatus());
    }
}
