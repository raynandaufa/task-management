package com.task.management.service;

import com.task.management.dto.TaskDto;
import com.task.management.dto.TaskRequestDto;
import com.task.management.entity.Task;
import com.task.management.entity.User;
import com.task.management.repository.TaskRepository;
import com.task.management.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public Page<TaskDto> getAllTaskByUserSign(Pageable pageable, Authentication authentication) {
        return taskRepository.findByUser(getUser(authentication), pageable)
                .map((element) -> modelMapper.map(element, TaskDto.class));
    }

    public TaskDto getTaskById(Long taskId){
        Task task = this.getTask(taskId);
        return modelMapper.map(task, TaskDto.class);
    }
    

    public TaskDto createTask(TaskRequestDto taskRequestDto, Authentication authentication) {
        // get user by id
        User user = getUser(authentication);

        // map dto to entity
        Task task = modelMapper.map(taskRequestDto, Task.class);
        task.setUser(user);

        // save task
        return modelMapper.map(taskRepository.save(task), TaskDto.class);
    }

    private User getUser(Authentication authentication) {
        return userRepository.findByUsername(authentication.getName())
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.BAD_REQUEST, "username not found")
                );
    }

    public TaskDto updateTask(Long taskId, TaskRequestDto taskRequestDto, Authentication authentication) {
        // Get the logged-in user's information
        String loggedInUsername = authentication.getName();

        // Get the task by ID
        Task task = getTask(taskId);

        // Check if the task owner is the same as the logged-in user
        if (!Objects.equals(task.getUser().getUsername(), loggedInUsername)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You do not have permission to update this task");
        }

        // Map the DTO data to the Task entity
        modelMapper.map(taskRequestDto, task);

        // Save the updated task
        return modelMapper.map(taskRepository.save(task), TaskDto.class);
    }

    private Task getTask(Long taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Task Id " + taskId + " not found"));
    }

    public void deleteTask(Long taskId, Authentication authentication) {

        // Get the logged-in user's information
        String loggedInUsername = authentication.getName();

        // Get the task by ID
        Task task = getTask(taskId);

        // Check if the task owner is the same as the logged-in user
        if (!Objects.equals(task.getUser().getUsername(), loggedInUsername)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You do not have permission to delete this task");
        }

        taskRepository.deleteById(taskId);
    }


}
