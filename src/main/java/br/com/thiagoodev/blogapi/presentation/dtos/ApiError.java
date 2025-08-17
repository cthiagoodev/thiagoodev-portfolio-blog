package br.com.thiagoodev.blogapi.presentation.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record ApiError(
    @JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss")
    LocalDateTime timestamp,
    int code,
    String status,
    List<String> errors
) { }
