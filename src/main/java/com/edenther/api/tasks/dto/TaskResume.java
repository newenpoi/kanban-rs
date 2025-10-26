package com.edenther.api.tasks.dto;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class TaskResume {
    private String id;
    private String title;
    private Long idStatus;
}
