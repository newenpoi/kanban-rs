package com.edenther.api.tasks.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.edenther.api.tasks.dto.RestError;
import com.edenther.api.tasks.exceptions.ProjectNotFoundException;
import com.edenther.api.tasks.exceptions.StatusNotFoundException;
import com.edenther.api.tasks.exceptions.TaskNotFoundException;

@RestControllerAdvice
public class DefaultExceptionHandler {

    @ExceptionHandler(ProjectNotFoundException.class)
    public ResponseEntity<RestError> handleException(ProjectNotFoundException e) {
		RestError re = new RestError(HttpStatus.NOT_FOUND.toString(), "This project does not exist.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(re);
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<RestError> handleException(TaskNotFoundException e) {
        RestError re = new RestError(HttpStatus.NOT_FOUND.toString(), "This task does not exist.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(re);
    }

    @ExceptionHandler(StatusNotFoundException.class)
    public ResponseEntity<RestError> handleException(StatusNotFoundException e) {
        RestError re = new RestError(HttpStatus.NOT_FOUND.toString(), "This status does not exist.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(re);
    }
    
}
