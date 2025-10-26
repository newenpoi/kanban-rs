package com.edenther.api.tasks.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TaskNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    
    public TaskNotFoundException(String message) {
        super(message);
    }
}
