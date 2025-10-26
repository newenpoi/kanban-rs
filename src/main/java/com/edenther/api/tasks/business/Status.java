package com.edenther.api.tasks.business;

import java.util.List;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "statuses")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Status {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Length(min = 2, max = 32)
    private String name;
    
    @Column(nullable = false)
    @Range(min = 0, max = 7, message = "The integer must be between 0 and 7.")
    private Integer position;

    @Length(min = 4, max = 12)
    private String color;

    // Task list.
    @OneToMany(mappedBy = "status", cascade = CascadeType.ALL)
    private List<Task> tasks;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
}
