package com.edenther.api.tasks.dto;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class ProjectResponse {
    private Long id;
    private String title;
}
