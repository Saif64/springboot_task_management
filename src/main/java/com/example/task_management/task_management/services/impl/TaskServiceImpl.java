package com.example.task_management.task_management.services.impl;

import com.example.task_management.task_management.dto.FilteredTaskDto;
import com.example.task_management.task_management.dto.TaskDto;
import com.example.task_management.task_management.entity.TaskEntity;
import com.example.task_management.task_management.enums.TaskStatus;
import com.example.task_management.task_management.exceptions.ResourceNotFoundException;
import com.example.task_management.task_management.mapper.TaskMapper;
import com.example.task_management.task_management.repository.TaskRepository;
import com.example.task_management.task_management.services.TaskService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;


    @Override
    public TaskDto createTask(TaskDto taskDto) {
        TaskEntity tasks = TaskMapper.toTasks(taskDto);
        TaskEntity savedTask = taskRepository.save(tasks);

        return TaskMapper.toTaskDto(savedTask);
    }

    @Override
    public TaskDto getTaskById(String id) {
        TaskEntity task = taskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));

        return TaskMapper.toTaskDto(task);
    }

    @Override
    public void deleteTask(String id) {
        TaskEntity task = taskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));

        taskRepository.delete(task);
    }

    @Override
    public List<TaskEntity> getAllTasks(FilteredTaskDto filteredTaskDto) {

        return taskRepository.findAll(new TaskSpecification(filteredTaskDto));
    }

    @Override
    public List<TaskDto> parseCsv(MultipartFile file) throws IOException {
        List<TaskDto> tasks = new ArrayList<>();

        try (Reader reader = new InputStreamReader(file.getInputStream())) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withHeader("name", "description", "status").withFirstRecordAsHeader().parse(reader);

            for (CSVRecord record : records) {
                TaskDto taskDto = new TaskDto();
                taskDto.setName(record.get("name"));
                taskDto.setDescription(record.get("description"));
                taskDto.setStatus(TaskStatus.valueOf(record.get("status")));
                tasks.add(taskDto);
            }
        }

        return tasks;
    }

    @Override
    public List<TaskEntity> createTasksFromCsv(List<TaskDto> tasksDto) {
        List<TaskEntity> tasks = tasksDto.stream().map(dto -> new TaskEntity(dto.getId(), dto.getName(), dto.getDescription(), dto.getStatus())).collect(Collectors.toList());

        return taskRepository.saveAll(tasks);
    }
}
