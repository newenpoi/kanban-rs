package com.edenther.api.tasks.dto;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class StatusColumn {
    private Long id;
    private String name;
    private Integer position;
    private String color;
}
