package com.edenther.api.tasks.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edenther.api.tasks.business.Task;

public interface TaskDao extends JpaRepository<Task, Long> {
    List<Task> findByOwner(String owner);
    List<Task> findByOwnerAndProjectId(String owner, Long idProject);
    
    /**
     * Task by id, owner and project id.
     * @param id
     * @param owner
     * @param idProject
     * @return
     */
    Optional<Task> findByIdAndOwnerAndProjectId(Long id, String owner, Long idProject);
}
