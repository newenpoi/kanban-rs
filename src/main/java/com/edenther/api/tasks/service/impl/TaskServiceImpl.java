package com.edenther.api.tasks.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.edenther.api.tasks.business.Project;
import com.edenther.api.tasks.business.Status;
import com.edenther.api.tasks.business.Task;
import com.edenther.api.tasks.dao.ProjectDao;
import com.edenther.api.tasks.dao.StatusDao;
import com.edenther.api.tasks.dao.TaskDao;
import com.edenther.api.tasks.dto.CreateTaskRequest;
import com.edenther.api.tasks.dto.TaskDetail;
import com.edenther.api.tasks.dto.TaskResume;
import com.edenther.api.tasks.dto.UpdateTaskRequest;
import com.edenther.api.tasks.exceptions.ProjectNotFoundException;
import com.edenther.api.tasks.exceptions.StatusNotFoundException;
import com.edenther.api.tasks.exceptions.TaskNotFoundException;
import com.edenther.api.tasks.service.TaskService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskDao dao;
    private final ProjectDao projectDao;
    private final StatusDao statusDao;

    @Override
    public List<TaskResume> retrieveTaskResumes(String identifier) {
        return dao.findByOwner(identifier).stream()
                .map(task -> TaskResume.builder().id(task.getId().toString()).title(task.getTitle()).idStatus(task.getStatus().getId()).build())
                .toList();
    }

    /**
     * Retrieves a list of task summaries for a specific user and project.
     */
    @Override
    public List<TaskResume> retrieveTaskResumes(String identifier, Long idProject) {
        return dao.findByOwnerAndProjectId(identifier, idProject).stream()
                .map(task -> TaskResume.builder().id(task.getId().toString()).title(task.getTitle()).idStatus(task.getStatus().getId()).build())
                .toList();
    }

    /**
     * Retrieve detailed information about a specific task by its ID, ensuring it belongs to the specified user and project.
     */
    @Override
    public TaskDetail retrieveTaskDetail(String identifier, Long idProject, Long idTask) {
        Task task = dao.findByIdAndOwnerAndProjectId(idTask, identifier, idProject).orElseThrow(() -> new TaskNotFoundException());
        
        // Builds and returns the TaskDetail DTO with all relevant information.
        return TaskDetail.builder().id(task.getId()).title(task.getTitle()).content(task.getContent()).created(task.getCreated()).assignees(task.getTaskAssignees()).progression(task.getProgression()).expiration(task.getExpiration()).build();
    }

    @Override
    public TaskResume createTask(String identifier, CreateTaskRequest request) {
        // Retrieves the project entity to ensure it exists and belongs to the user.
        Project project = projectDao.findByIdAndOwner(request.getIdProject(), identifier).orElseThrow(() -> new ProjectNotFoundException());

        // Retrieves the status column entity to ensure it exists within the project.
        Status status = statusDao.findByIdAndProjectId(request.getIdStatus(), request.getIdProject()).orElseThrow(() -> new StatusNotFoundException());

        // Prepares the new task entity.
        Task task = Task.builder().title(request.getTitle()).created(LocalDateTime.now()).owner(identifier).progression(0).project(project).status(status).build();

        // Saves the new task entity.
        dao.save(task);

        // Returns a summary of the newly created task.
        return TaskResume.builder().id(task.getId().toString()).title(task.getTitle()).idStatus(task.getStatus().getId()).build();
    }

    @Override
    @Transactional
    public TaskDetail updateTask(String identifier, Long idProject, Long idTask, UpdateTaskRequest request) {
        // Retrieves the task entity to ensure it exists and belongs to the user.
        Task task = dao.findByIdAndOwnerAndProjectId(idTask, identifier, idProject).orElseThrow(() -> new TaskNotFoundException());

        // Updates the task fields if they are provided in the request.
        if (request.getTitle() != null) task.setTitle(request.getTitle());
        if (request.getContent() != null) task.setContent(request.getContent());
        if (request.getProgression() != null) task.setProgression(request.getProgression());
        if (request.getExpiration() != null) task.setExpiration(request.getExpiration());
        
        // If a new status ID is provided, update the task's status after validating it exists within the project.
        if (request.getIdStatus() != null) {
            // Retrieves the new status column entity to ensure it exists within the project.
            Status status = statusDao.findByIdAndProjectId(request.getIdStatus(), idProject).orElseThrow(() -> new StatusNotFoundException());
            task.setStatus(status);
        }

        // Saves the updated task entity.
        dao.save(task);

        // Returns a summary of the updated task.
        return TaskDetail.builder().id(task.getId()).title(task.getTitle()).content(task.getContent()).created(task.getCreated()).assignees(task.getTaskAssignees()).progression(task.getProgression()).expiration(task.getExpiration()).build();
    }

    @Override
    public void deleteTask(String identifier, Long idProject, Long idTask) {
        // Retrieves the task entity to ensure it exists and belongs to the user.
        Task task = dao.findByIdAndOwnerAndProjectId(idTask, identifier, idProject).orElseThrow(() -> new TaskNotFoundException());

        // Deletes the task entity.
        dao.delete(task);
    }
    
}
