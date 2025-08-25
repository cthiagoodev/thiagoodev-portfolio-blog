package br.com.thiagoodev.blogapi.presentation.controllers;

import br.com.thiagoodev.blogapi.domain.exceptions.*;
import br.com.thiagoodev.blogapi.presentation.dtos.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ErrorController {
    @ExceptionHandler({
        UserNotEnabledException.class,
    })
    public ResponseEntity<ApiError> handleForbiddenException(RuntimeException error) {
        ApiError err = buildApiError(HttpStatus.FORBIDDEN, error.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);
    }

    @ExceptionHandler({
        UserNotExistsException.class,
        PermissionNotExistsException.class,
    })
    public ResponseEntity<ApiError> handleNotFoundException(RuntimeException error) {
        ApiError err = buildApiError(HttpStatus.NOT_FOUND, error.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler({
        InvalidUuidFormatException.class,
        IllegalArgumentException.class,
    })
    public ResponseEntity<ApiError> handleBadRequestException(RuntimeException error) {
        ApiError err = buildApiError(HttpStatus.BAD_REQUEST, error.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleConflictException(RuntimeException error) {
        ApiError err = buildApiError(HttpStatus.CONFLICT, error.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(err);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleException(Exception error) {
        ApiError err = buildApiError(HttpStatus.INTERNAL_SERVER_ERROR, error.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err);
    }

    private ApiError buildApiError(HttpStatus status, String message) {
        return ApiError
                .builder()
                .timestamp(LocalDateTime.now())
                .code(status.value())
                .status(status.name())
                .error(message)
                .build();
    }
}
