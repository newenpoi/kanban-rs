package com.edenther.api.tasks.dto;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class CreateProjectRequest {
    @Length(min = 1, max = 32, message = "Can only accept titles between 1 and 32 characters.")
    private String title;
}
