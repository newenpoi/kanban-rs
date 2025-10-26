package com.edenther.api.tasks.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class TaskDetail {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime created;
    private List<TaskAssignee> assignees;
    private int progression;
    private LocalDateTime expiration;
}
