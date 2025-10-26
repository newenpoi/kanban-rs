package com.edenther.api.tasks.service;

import java.util.List;

import com.edenther.api.tasks.business.Project;
import com.edenther.api.tasks.dto.CreateProjectRequest;
import com.edenther.api.tasks.dto.CreateStatusRequest;
import com.edenther.api.tasks.dto.ProjectResponse;
import com.edenther.api.tasks.dto.StatusColumn;

public interface ProjectService {
    public List<ProjectResponse> retrieveProjects(String sub);
    public Project retrieveProject(Long idProject);
    public Project retrieveUserProject(Long idProject, String sub);
    public ProjectResponse createProject(CreateProjectRequest request, String sub);
    public List<StatusColumn> retrieveProjectStatusColumns(Long idProject, String sub);
    
    public StatusColumn createProjectStatusColumn(CreateStatusRequest request, Long idProject, String sub);
    
    /**
     * Reorders the position of two status columns within a project.
     * @param idProject The ID of the project.
     * @param idSource The ID of the source column to move.
     * @param idTarget The ID of the target column to move to.
     * @param sub The subject (user) making the request.
     */
    public void swapColumnPositions(Long idProject, int a, int b, String sub);

    public void deleteProjectStatusColumn(Long idProject, Long idStatus, String sub);
}
