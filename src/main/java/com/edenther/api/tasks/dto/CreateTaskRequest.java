package com.edenther.api.tasks.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CreateTaskRequest {
    @NotNull(message = "The project identifier is required.")
    @Positive(message = "The project identifier is invalid.")
    private Long idProject;

    @NotNull(message = "The status column identifier is required.")
    @Positive(message = "The status column identifier is invalid.")
    private Long idStatus;

    @Length(min = 1, max = 64, message = "Can only accept titles between 1 and 64 characters.")
    private String title;
}
