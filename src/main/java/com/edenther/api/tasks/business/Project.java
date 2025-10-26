package com.edenther.api.tasks.business;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "projects")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Project {

    private static final int MAX_STATUS_COLUMNS = 8;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Length(min = 2, max = 32)
    private String title;
    
    @Column(nullable = false)
    private LocalDateTime created;

    /**
     * In other words the owner subject from the JWT.
     */
    @Column(name = "owner_id", nullable = false)
    private String owner;

    // Task list.
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Task> tasks;

    // Status list.
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Status> statuses;

    /**
     * Adds a new status column to a maximum of eight status columns by reordering the position of existing columns if necessary.
     * The new status column takes the next available position.
     * @param column The new status column to be added.
     */
    public void addStatusColumn(Status column) {
        // Prevents adding more than eight columns.
        if (this.statuses.size() >= MAX_STATUS_COLUMNS) throw new IllegalStateException("A project cannot have more than eight status columns.");

        // Extracts the next available position in status column list.
        int next = statuses.stream().mapToInt(Status::getPosition).max().orElse(-1) + 1;

        // The new position.
        column.setPosition(next);
        
        // The new column belongs to this project.
        column.setProject(this);

        // Adds the new column to the list.
        this.statuses.add(column);
    }

    /**
     * Reorders the position of two status columns within a project.
     * @param a The position of the source column to be moved.
     * @param b The position of the target column to be moved.
     */
    public void swapStatusColumnPositions(int a, int b) {
        // Finds the source and target columns.
        Status source = this.findPosition(a);
        Status target = this.findPosition(b);

        // Swaps the positions.
        int temp = source.getPosition();
        source.setPosition(target.getPosition());
        target.setPosition(temp);
    }

    /**
     * Helper method to return a status column by its position.
     * @param position The position that the status column carries.
     * @return The status column with the specified position, or null if not found.
     */
    private Status findPosition(int position) { return this.statuses.stream().filter(c -> c.getPosition() == position).findFirst().orElse(null); }
}
