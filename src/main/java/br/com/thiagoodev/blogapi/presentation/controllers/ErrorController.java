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
        ApiError err = ApiError
                .builder()
                .timestamp(LocalDateTime.now())
                .code(HttpStatus.FORBIDDEN.value())
                .status(HttpStatus.FORBIDDEN.name())
                .error(error.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);
    }


    @ExceptionHandler({
        UserNotExistsException.class,
        PermissionNotExistsException.class,
    })
    public ResponseEntity<ApiError> handleNotFoundException(RuntimeException error) {
        ApiError err = ApiError
                .builder()
                .timestamp(LocalDateTime.now())
                .code(HttpStatus.NOT_FOUND.value())
                .status(HttpStatus.NOT_FOUND.name())
                .error(error.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler({
        InvalidUuidFormatException.class,
        IllegalArgumentException.class,
    })

    public ResponseEntity<ApiError> handleBadRequestException(RuntimeException error) {
        ApiError err = ApiError
                .builder()
                .timestamp(LocalDateTime.now())
                .code(HttpStatus.BAD_REQUEST.value())
                .status(HttpStatus.BAD_REQUEST.name())
                .error(error.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleConflictException(RuntimeException error) {
        ApiError err = ApiError
                .builder()
                .timestamp(LocalDateTime.now())
                .code(HttpStatus.CONFLICT.value())
                .status(HttpStatus.CONFLICT.name())
                .error(error.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(err);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleException(Exception error) {
        ApiError err = ApiError
                .builder()
                .timestamp(LocalDateTime.now())
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                .error(error.getMessage())
                .build();

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(err);
    }
}
