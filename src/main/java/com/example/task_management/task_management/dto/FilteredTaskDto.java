package com.example.task_management.task_management.dto;

import com.example.task_management.task_management.enums.TaskStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FilteredTaskDto {
    @Enumerated(EnumType.STRING)
    private TaskStatus status;


    private String search;
}
