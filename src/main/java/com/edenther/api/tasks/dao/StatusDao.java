package com.edenther.api.tasks.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edenther.api.tasks.business.Status;

public interface StatusDao extends JpaRepository<Status, Long> {
    List<Status> findByProjectIdAndProjectOwner(Long idProject, String owner);
    Optional<Status> findByIdAndProjectId(Long id, Long idProject);
}
