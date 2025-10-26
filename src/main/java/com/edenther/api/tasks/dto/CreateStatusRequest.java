package com.edenther.api.tasks.dto;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import lombok.Data;

@Data
public class CreateStatusRequest {

    @Length(min = 1, max = 32, message = "Can only accept titles between 1 and 32 characters.")
    private String name;

    @Range(min = 0, max = 7, message = "The integer must be between 0 and 7.")
    private Integer position;

    // Not used yet.
    private String color;
}
