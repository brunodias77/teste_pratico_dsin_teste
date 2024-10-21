package com.brunodias.dsin.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponseDTO {
    private int status;
    private String message;
    private String jwt;
    private final LocalDateTime timestamp = LocalDateTime.now();
}

