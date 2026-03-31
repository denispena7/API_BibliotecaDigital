package es.library.springboot.controllers.base;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import es.library.springboot.DTOs.responses.WraperResponse;

public abstract class BaseController 
{
    protected <T> ResponseEntity<WraperResponse<T>> ok(T data, String message) {
        return ResponseEntity.ok(
                WraperResponse.<T>builder()
                        .data(data)
                        .ok(true)
                        .message(message)
                        .build()
        );
    }

    protected <T> ResponseEntity<WraperResponse<T>> created(T data, String message) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        WraperResponse.<T>builder()
                                .data(data)
                                .ok(true)
                                .message(message)
                                .build()
                );
    }
    
    protected ResponseEntity<Void> noContent() {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
