package com.krizan.social_media.controller.exception;

import com.krizan.social_media.controller.response.ExceptionResponse;
import java.time.LocalDateTime;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    //  TODO: make exceptions return correct http status

    @ExceptionHandler(NotFoundException.class)
    public final ExceptionResponse handleNotFoundException(NotFoundException exception) {
        return ExceptionResponse.builder()
            .timestamp(LocalDateTime.now())
            .message(exception.getMessage())
            .build();
    }

    @ExceptionHandler(ForbiddenException.class)
    public final ExceptionResponse handleForbiddenException(ForbiddenException exception) {
        return ExceptionResponse.builder()
            .timestamp(LocalDateTime.now())
            .message(exception.getMessage())
            .build();
    }

    @ExceptionHandler(UnsatisfyingParameterException.class)
    public final ExceptionResponse handleUnsatisfyingParameterException(
        UnsatisfyingParameterException exception
    ) {
        return ExceptionResponse.builder()
            .timestamp(LocalDateTime.now())
            .message(exception.getMessage())
            .build();
    }
}
