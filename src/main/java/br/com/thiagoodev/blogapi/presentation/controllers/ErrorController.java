package br.com.thiagoodev.blogapi.presentation.controllers;

import br.com.thiagoodev.blogapi.presentation.dtos.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ErrorController {
    @ExceptionHandler(Exception.class)
    public ApiError handleException(Exception error) {
        return ApiError
                .builder()
                .timestamp(LocalDateTime.now())
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                .error(error.getMessage())
                .build();
    }
}
