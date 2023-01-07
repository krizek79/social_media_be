package com.krizan.social_media.controller.response;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ExceptionResponse {

    private LocalDateTime timestamp;
    private String message;

    public ExceptionResponse(Throwable ex) {
        this.timestamp = LocalDateTime.now();
        this.message = ex.getMessage();
    }
}
