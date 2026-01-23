package es.library.springboot.exceptions;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.jsonwebtoken.ExpiredJwtException;


import es.library.springboot.DTOs.responses.WraperResponse;

@RestControllerAdvice
public class GlobalExceptionHandler
{
    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<WraperResponse<?>> notFound(ExpiredJwtException e) 
    {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new WraperResponse<>(null, false, e.getMessage()));
    }
	
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<WraperResponse<?>> notFound(EntityNotFoundException e) 
    {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new WraperResponse<>(null, false, e.getMessage()));
    }

    @ExceptionHandler(ValidateException.class)
    public ResponseEntity<WraperResponse<?>> badRequest(ValidateException e)
    {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new WraperResponse<>(null, false, e.getMessage()));
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<WraperResponse<?>> handleValidationErrors(
            MethodArgumentNotValidException ex) {

    	Map<String, String> errors = ex.getBindingResult()
    	        .getFieldErrors()
    	        .stream()
    	        .collect(Collectors.toMap(
    	            FieldError::getField,
    	            FieldError::getDefaultMessage,
    	            (a, b) -> a
    	        ));

        return ResponseEntity.badRequest()
                .body(new WraperResponse<>(errors, false, "Validation error"));
    }
    
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<WraperResponse<?>> handleMediaType(HttpMediaTypeNotSupportedException e) {
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                .body(new WraperResponse<>(
                        null,
                        false,
                        "Invalid content type. Use multipart/form-data"
                ));
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<WraperResponse<?>> internal(Exception e)
    {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new WraperResponse<>(null, false, "Internal Server Error"));
    }
    
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<WraperResponse<?>> handleBadCredentials(BadCredentialsException e)
    {
    	return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
    			.body(new WraperResponse<>(null, false, "Username or password incorrect"));
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<WraperResponse<?>> handleAuthorizedDenied(AuthorizationDeniedException e)
    {
    	return ResponseEntity.status(HttpStatus.FORBIDDEN)
    			.body(new WraperResponse<>(null, false, "Accesss denied: required permissions to carry out this action"));
    }
    
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<WraperResponse<?>> handleAuthorizedDenied(AccessDeniedException e)
    {
    	return ResponseEntity.status(HttpStatus.FORBIDDEN)
    			.body(new WraperResponse<>(null, false, "Access denied: User not authenticated"));
    }
    
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<WraperResponse<?>> handleUsernameNotFound(UsernameNotFoundException e)
    {
    	return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
    			.body(new WraperResponse<>(null, false, e.getMessage()));
    }
}