package com.edenther.api.tasks.exceptions;

public class StatusNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    
    public StatusNotFoundException() {
        super("The specified status column was not found.");
    }
}
