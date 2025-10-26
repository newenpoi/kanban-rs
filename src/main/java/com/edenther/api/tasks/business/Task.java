package com.edenther.api.tasks.business;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import com.edenther.api.tasks.dto.TaskAssignee;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Future;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tasks")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Task {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Length(min = 2, max = 100)
    private String title;

    private String content;
    
    @Column(nullable = false)
    private LocalDateTime created;

    /**
     * Sub id of the user who created the task.
     * @see #USER_SERVICE_REFERENCE
     */
    @Column(name = "owner_id", nullable = false)
    private String owner;

    @Future
    private LocalDateTime expiration;

    @Range(min = 0, max = 100)
    private int progression;

    /**
     * Reference to the project this task belongs to.
     */
    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    /**
     * The status of a task (in order to sort by columns on the front).
     */
    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    private Status status;

    /**
     * List of users assigned to this task.
     */
    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Assignee> assignees;

    /**
     * Helper approach to convert internal assignees to DTOs.
     */
    public List<TaskAssignee> getTaskAssignees() {
        return assignees.stream()
                .map(assignee -> TaskAssignee.builder().identifier(assignee.getIdAssignee()).instigator(assignee.getInstigator()).creation(assignee.getCreation()).build())
                .toList();
    }
}
