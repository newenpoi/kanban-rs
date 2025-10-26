package com.edenther.api.tasks.controller;

import java.util.List;

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

import com.edenther.api.tasks.dto.CreateProjectRequest;
import com.edenther.api.tasks.dto.CreateStatusRequest;
import com.edenther.api.tasks.dto.ProjectResponse;
import com.edenther.api.tasks.dto.StatusColumn;
import com.edenther.api.tasks.dto.SwapColumnsRequest;
import com.edenther.api.tasks.service.ProjectService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {
    
    private final ProjectService service;

    @GetMapping
    public ResponseEntity<List<ProjectResponse>> getProjects(@AuthenticationPrincipal Jwt token) {

        // Retrieves projects for the authenticated user from the service.
        List<ProjectResponse> projects = service.retrieveProjects(token.getSubject());

        // Returns 204 No Content if the list is empty, otherwise returns 200 OK with the list.
        if (projects.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(projects);
    }

    /**
     * Get status columns from a user's project.
     * @param idProject
     * @param token
     * @return List of status columns.
     */
    @GetMapping("/{idProject}/columns")
    public ResponseEntity<List<StatusColumn>> getStatusColumns(@PathVariable Long idProject, @AuthenticationPrincipal Jwt token) {
        List<StatusColumn> statusColumns = service.retrieveProjectStatusColumns(idProject, token.getSubject());
        return ResponseEntity.ok(statusColumns);
    }

    /**
     * Create a new project.
     * @param request
     * @return
     */
    @PostMapping
    public ResponseEntity<ProjectResponse> createProject(@Valid @RequestBody CreateProjectRequest request, @AuthenticationPrincipal Jwt token) {
        return ResponseEntity.ok(service.createProject(request, token.getSubject()));
    }

    /**
     * Create a new project status column.
     * @param idProject
     */
    @PostMapping("/{idProject}/columns")
    public ResponseEntity<StatusColumn> createProjectStatusColumn(@PathVariable Long idProject, @Valid @RequestBody CreateStatusRequest request, @AuthenticationPrincipal Jwt token) {
        return ResponseEntity.ok(service.createProjectStatusColumn(request, idProject, token.getSubject()));
    }

    /**
     * Swaps status column positions within a project.
     */
    @PatchMapping("/{idProject}/columns/swap")
    public ResponseEntity<Void> swapProjectStatusColumns(@PathVariable Long idProject, @Valid @RequestBody SwapColumnsRequest request, @AuthenticationPrincipal Jwt token) {
        service.swapColumnPositions(idProject, request.getA(), request.getB(), token.getSubject());
        return ResponseEntity.noContent().build();
    }

    /**
     * Deletes a status column from a project.
     */
    @DeleteMapping("/{idProject}/columns/{idStatus}")
    public ResponseEntity<Void> deleteProjectStatusColumn(@PathVariable Long idProject, @PathVariable Long idStatus, @AuthenticationPrincipal Jwt token) {
        service.deleteProjectStatusColumn(idProject, idStatus, token.getSubject());
        return ResponseEntity.noContent().build();
    }
}
