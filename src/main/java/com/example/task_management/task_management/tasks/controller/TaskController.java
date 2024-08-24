package com.example.task_management.task_management.tasks.controller;

import com.example.task_management.task_management.tasks.dto.FilteredTaskDto;
import com.example.task_management.task_management.tasks.dto.TaskDto;
import com.example.task_management.task_management.tasks.entity.TaskEntity;
import com.example.task_management.task_management.tasks.services.impl.TaskServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskServiceImpl taskService;


    @PostMapping
    ResponseEntity<TaskDto> createTask(TaskDto taskDto) {
        return new ResponseEntity<>(taskService.createTask(taskDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable("id") String id) {
        TaskDto task = taskService.getTaskById(id);
        return ResponseEntity.ok(task);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable String id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<TaskEntity>> getAllTasks(FilteredTaskDto filteredTaskDto) {
        List<TaskEntity> tasks = taskService.getAllTasks(filteredTaskDto);

        return ResponseEntity.ok(tasks);
    }

    @PostMapping("/csv")
    public ResponseEntity<Map<String, Object>> uploadCsv(@RequestPart("file") MultipartFile file) {
        Map<String, Object> response = new HashMap<>();

        if (file.isEmpty()) {
            response.put("message", "No file uploaded");
            return ResponseEntity.badRequest().body(response);
        }

        try {
            List<TaskDto> tasks = taskService.parseCsv(file);
            List<TaskEntity> createdTasks = taskService.createTasksFromCsv(tasks);

            response.put("message", "Tasks created successfully");
            response.put("tasks", createdTasks);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            response.put("message", "Error processing file");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}
