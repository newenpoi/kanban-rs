package com.edenther.api.tasks.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edenther.api.tasks.business.Project;

public interface ProjectDao extends JpaRepository<Project, Long> {
    /**
     * List of all projects by owner.
     * @param owner
     * @return
     */
    List<Project> findByOwner(String owner);

    /**
     * Returns a project entity by id and owner.
     * @param id
     * @param owner
     * @return
     */
    Optional<Project> findByIdAndOwner(Long id, String owner);
}
