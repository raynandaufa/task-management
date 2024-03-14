package com.task.management.controller;

import com.task.management.dto.TaskDto;
import com.task.management.dto.TaskRequestDto;
import com.task.management.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tasks")
@Validated
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public ResponseEntity<Page<TaskDto>> getAll(Pageable pageable, Authentication authentication) {
        return new ResponseEntity<>(taskService.getAllTaskByUserSign(pageable,authentication), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> getById(@PathVariable Long id){
        return new ResponseEntity<>(taskService.getTaskById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TaskDto> createTask(@RequestBody @Validated TaskRequestDto taskRequestDto, Authentication authentication){
        return new ResponseEntity<>(taskService.createTask(taskRequestDto, authentication),HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDto> updateTask(@PathVariable Long id, @RequestBody @Validated TaskRequestDto taskRequestDto, Authentication authentication){
        return new ResponseEntity<>(taskService.updateTask(id, taskRequestDto, authentication),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable Long id, Authentication authentication){
        taskService.deleteTask(id, authentication);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
