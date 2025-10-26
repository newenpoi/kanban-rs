package com.edenther.api.tasks.service;

import java.util.List;

import com.edenther.api.tasks.dto.CreateTaskRequest;
import com.edenther.api.tasks.dto.TaskDetail;
import com.edenther.api.tasks.dto.TaskResume;
import com.edenther.api.tasks.dto.UpdateTaskRequest;

public interface TaskService {
    public List<TaskResume> retrieveTaskResumes(String identifier);
    public List<TaskResume> retrieveTaskResumes(String identifier, Long idProject);
    public TaskDetail retrieveTaskDetail(String identifier, Long idProject, Long idTask);
    public TaskResume createTask(String identifier, CreateTaskRequest request);
    public TaskDetail updateTask(String identifier, Long idProject, Long idTask, UpdateTaskRequest request);
    public void deleteTask(String identifier, Long idProject, Long idTask);
}
