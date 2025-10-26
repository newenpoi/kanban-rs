package com.edenther.api.tasks.dto;

import java.time.LocalDateTime;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class UpdateTaskRequest {
    @Length(min = 2, max = 100, message = "Title must be between 2 and 100 characters.")
    private String title;

    @Length(max = 2000, message = "Content cannot exceed 2000 characters.")
    private String content;
    
    @Range(min = 0, max = 100, message = "Progression must be between 0 and 100.")
    private Integer progression;
    
    @Future(message = "Expiration must be in the future.")
    private LocalDateTime expiration;

    @Positive(message = "Status ID must be positive.")
    private Long idStatus;
}
