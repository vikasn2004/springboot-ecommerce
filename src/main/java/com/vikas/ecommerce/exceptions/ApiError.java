package com.vikas.ecommerce.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class ApiError {
    private String message;
    private int status;
    private LocalDateTime timestamp;
}
