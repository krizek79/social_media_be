package com.krizan.social_media.controller.response;

import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ExceptionResponse {

    private HttpStatus status;
    private LocalDateTime timestamp;
    private String message;

    public ExceptionResponse(HttpStatus status, Throwable ex) {
        this.status = status;
        this.timestamp = LocalDateTime.now();
        this.message = ex.getMessage();
    }
}
