package com.edenther.api.tasks.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SwapColumnsRequest {
    
    @NotNull(message = "First position is required.")
    @Min(value = 0, message = "Position must be at least 0.")
    @Max(value = 7, message = "Position must be at most 7.")
    private Integer a;
    
    @NotNull(message = "Second position is required.")
    @Min(value = 0, message = "Position must be at least 0.")
    @Max(value = 7, message = "Position must be at most 7.")
    private Integer b;
}