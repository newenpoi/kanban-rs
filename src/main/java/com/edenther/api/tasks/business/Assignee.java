package com.edenther.api.tasks.business;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "assignees")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Assignee {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;
    
    /**
     * UUID of the assigned user (sub from JWT).
     */
    @Column(name = "assignee_id", nullable = false)
    private String idAssignee;
    
    /**
     * When this assignment was created.
     */
    @Column(name = "assigned_at", nullable = false)
    private LocalDateTime creation;
    
    /**
     * Who made this assignment (optional).
     */
    @Column(name = "assigned_by")
    private String instigator;
}
