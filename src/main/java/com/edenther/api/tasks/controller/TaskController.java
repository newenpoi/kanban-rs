package com.edenther.api.tasks.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edenther.api.tasks.dto.CreateTaskRequest;
import com.edenther.api.tasks.dto.TaskDetail;
import com.edenther.api.tasks.dto.TaskResume;
import com.edenther.api.tasks.dto.UpdateTaskRequest;
import com.edenther.api.tasks.service.TaskService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService service;

    /** 
     * Retrieve all tasks resumes of a project.
     */
    @GetMapping("/{idProject}")
    public ResponseEntity<List<TaskResume>> getProjectTasks(@PathVariable Long idProject, @AuthenticationPrincipal Jwt token) {
        // Retrieves all tasks resumes for a specific project from the service.
        List<TaskResume> tasks = service.retrieveTaskResumes(token.getSubject(), idProject);

        // Returns 204 No Content if the list is empty, otherwise returns 200 OK with the list.
        if (tasks.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(tasks);
    }

    /**
     * Retrieve tasks details of a task in a project.
     */
    @GetMapping("/{idProject}/{idTask}")
    public ResponseEntity<TaskDetail> getTaskDetail(@PathVariable Long idProject, @PathVariable Long idTask, @AuthenticationPrincipal Jwt token) {
        // Retrieves task details from the service.
        TaskDetail taskDetail = service.retrieveTaskDetail(token.getSubject(), idProject, idTask);

        // Returns 200 OK with the task details.
        return ResponseEntity.ok(taskDetail);
    }

    /**
     * Create a new task for a project in a given status column.
     */
    @PostMapping
    public ResponseEntity<TaskResume> createTask(@Valid @RequestBody CreateTaskRequest request, @AuthenticationPrincipal Jwt token) {
        // Creates a new task using the service.
        TaskResume created = service.createTask(token.getSubject(), request);
        
        // Returns 201 Created with the created task summary.
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Updates an existing task in a project.
     */
    @PatchMapping("/{idProject}/{idTask}")
    public ResponseEntity<TaskDetail> updateTask(@PathVariable Long idProject, @PathVariable Long idTask, @Valid @RequestBody UpdateTaskRequest request, @AuthenticationPrincipal Jwt token) {
        // Updates the task using the service.
        TaskDetail updated = service.updateTask(token.getSubject(), idProject, idTask, request);

        // Returns 200 OK with the updated task summary.
        return ResponseEntity.ok(updated);
    }

    /**
     * Delete a task from a project.
     */
    @DeleteMapping("/{idProject}/{idTask}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long idProject, @PathVariable Long idTask, @AuthenticationPrincipal Jwt token) {
        // Deletes the task using the service.
        service.deleteTask(token.getSubject(), idProject, idTask);

        // Returns 204 No Content to indicate successful deletion with no content to return.
        return ResponseEntity.noContent().build();
    }
}