package com.edenther.api.tasks.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.edenther.api.tasks.business.Project;
import com.edenther.api.tasks.business.Status;
import com.edenther.api.tasks.dao.ProjectDao;
import com.edenther.api.tasks.dao.StatusDao;
import com.edenther.api.tasks.dto.CreateProjectRequest;
import com.edenther.api.tasks.dto.CreateStatusRequest;
import com.edenther.api.tasks.dto.ProjectResponse;
import com.edenther.api.tasks.dto.StatusColumn;
import com.edenther.api.tasks.exceptions.ProjectNotFoundException;
import com.edenther.api.tasks.exceptions.StatusNotFoundException;
import com.edenther.api.tasks.service.ProjectService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectDao projectDao;
    private final StatusDao statusDao;

    /**
     * Retrieves all projects owned by the specified user.
     */
    @Override
    public List<ProjectResponse> retrieveProjects(String identifier) {
        return projectDao.findByOwner(identifier).stream().map(project -> ProjectResponse.builder().id(project.getId()).title(project.getTitle()).build()).toList();
    }

    /**
     * Retrieves a project by its ID.
     */
    @Override
    public Project retrieveProject(Long idProject) throws ProjectNotFoundException {
        return projectDao.findById(idProject).orElseThrow(() -> new ProjectNotFoundException());
    }

    /**
     * Retrieves a project that matches the project id and specified user identifier.
     */
    @Override
    public Project retrieveUserProject(Long idProject, String identifier) throws ProjectNotFoundException {
        return projectDao.findByIdAndOwner(idProject, identifier).orElseThrow(() -> new ProjectNotFoundException());
    }

    /**
     * Creates a new project based on the provided request.
     */
    @Override
    @Transactional
    public ProjectResponse createProject(CreateProjectRequest request, String owner) {

        // Prepares the project entity.
        Project project = Project.builder().title(request.getTitle()).created(LocalDateTime.now()).owner(owner).build();

        // A project must have by default three status colums created and we must define the project reference.
        Status todo = Status.builder().name("Pending").color("blue").position(0).project(project).build();

        // Sets the statuses to the project to ensure integrity.
        project.setStatuses(List.of(todo));

        // Saves the project which will cascade the save to the statuses.
        projectDao.save(project);

        // Returns the saved project as a DTO.
        return ProjectResponse.builder().id(project.getId()).title(project.getTitle()).build();
    }

    /**
     * Retrieves all status columns for a given project and user identifier.
     */
    @Override
    public List<StatusColumn> retrieveProjectStatusColumns(Long idProject, String identifier) {
        // Finds statuses by project ID and owner identifier.
        // Queries statuses directly from the StatusDao to avoid loading the entire project entity (to avoid lazy loading issues).
        List<Status> statuses = statusDao.findByProjectIdAndProjectOwner(idProject, identifier);
        
        // Returns the status columns mapped to DTOs.
        return statuses.stream().map(status -> StatusColumn.builder().id(status.getId()).name(status.getName()).color(status.getColor()).position(status.getPosition()).build()).toList();
    }

    /**
     * Creates a new status column for a given project.
     * We can use addStatusColumn() at the business layer to ensure proper ordering and integrity.
     */
    @Override
    @Transactional
    public StatusColumn createProjectStatusColumn(CreateStatusRequest request, Long idProject, String identifier) {
        // Finds the project to which the status column will be added.
        Project project = this.retrieveUserProject(idProject, identifier);

        // Creates the new status entity.
        Status status = Status.builder().name(request.getName()).build();

        // Use business method to add the status column to the project while keeping positions consistent.
        project.addStatusColumn(status);
        
        // Saves the project which will cascade the save to the new status column.
        projectDao.save(project);

        // Returns the created status column as a DTO.
        return StatusColumn.builder().id(status.getId()).name(status.getName()).color(status.getColor()).position(status.getPosition()).build();
    }

    @Override
    @Transactional
    public void swapColumnPositions(Long idProject, int a, int b, String sub) {
        // Verifies ownership and existence of the project.
        Project project = this.retrieveUserProject(idProject, sub);
        
        // Use business level logic to swap positions.
        project.swapStatusColumnPositions(a, b);
        
        // Persist the changes.
        projectDao.save(project);
    }

    @Override
    @Transactional
    public void deleteProjectStatusColumn(Long idProject, Long idStatus, String sub) {
        // Verifies ownership and existence of the project.
        Project project = this.retrieveUserProject(idProject, sub);

        // Finds the status column to be deleted from the project's status list by finding the first match that equals the provided idStatus.
        Status removal = project.getStatuses().stream().filter(status -> status.getId().equals(idStatus)).findFirst().orElseThrow(() -> new StatusNotFoundException());
        
        // Stores the position of the status column to be removed to reorder remaining columns.
        int removedPosition = removal.getPosition();

        // Removes the status column from the project.
        project.getStatuses().remove(removal);

        // Reorders the positions of remaining status columns to fill the gap.
        project.getStatuses().stream().filter(status -> status.getPosition() > removedPosition).forEach(status -> status.setPosition(status.getPosition() - 1));
    }
}
