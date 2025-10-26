package com.edenther.api.tasks.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class TaskAssignee {
    private String identifier;
    private String instigator;
    private LocalDateTime creation;
}
