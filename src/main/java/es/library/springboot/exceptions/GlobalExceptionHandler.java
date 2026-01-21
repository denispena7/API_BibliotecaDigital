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


import es.library.springboot.DTOs.responses.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler
{
    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ApiResponse<?>> notFound(ExpiredJwtException e) 
    {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse<>(null, false, e.getMessage()));
    }
	
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> notFound(EntityNotFoundException e) 
    {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse<>(null, false, e.getMessage()));
    }

    @ExceptionHandler(ValidateException.class)
    public ResponseEntity<ApiResponse<?>> badRequest(ValidateException e)
    {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>(null, false, e.getMessage()));
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleValidationErrors(
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
                .body(new ApiResponse<>(errors, false, "Validation error"));
    }
    
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ApiResponse<?>> handleMediaType(HttpMediaTypeNotSupportedException e) {
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                .body(new ApiResponse<>(
                        null,
                        false,
                        "Invalid content type. Use multipart/form-data"
                ));
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> internal(Exception e)
    {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(null, false, "Internal Server Error"));
    }
    
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse<?>> handleBadCredentials(BadCredentialsException e)
    {
    	return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
    			.body(new ApiResponse<>(null, false, "Username or password incorrect"));
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ApiResponse<?>> handleAuthorizedDenied(AuthorizationDeniedException e)
    {
    	return ResponseEntity.status(HttpStatus.FORBIDDEN)
    			.body(new ApiResponse<>(null, false, "Accesss denied: required permissions to carry out this action"));
    }
    
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<?>> handleAuthorizedDenied(AccessDeniedException e)
    {
    	return ResponseEntity.status(HttpStatus.FORBIDDEN)
    			.body(new ApiResponse<>(null, false, "Access denied: User not authenticated"));
    }
    
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleUsernameNotFound(UsernameNotFoundException e)
    {
    	return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
    			.body(new ApiResponse<>(null, false, e.getMessage()));
    }
}