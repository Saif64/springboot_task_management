package com.example.task_management.task_management.dto;

import com.example.task_management.task_management.enums.TaskStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {
    private String id;

    @NonNull
    private String name;

    @NonNull
    private String description;

    @NonNull

    private TaskStatus status;
}
