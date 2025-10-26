package com.edenther.api.tasks.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ProjectNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    
    public ProjectNotFoundException(String message) {
        super(message);
    }
}
