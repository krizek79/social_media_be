package com.krizan.social_media.controller.exception;

import com.krizan.social_media.controller.response.ExceptionResponse;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public final ExceptionResponse handleNotFoundException(NotFoundException exception) {
        return ExceptionResponse.builder()
            .timestamp(LocalDateTime.now())
            .message(exception.getMessage())
            .build();
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public final ExceptionResponse handleForbiddenException(ForbiddenException exception) {
        return ExceptionResponse.builder()
            .timestamp(LocalDateTime.now())
            .message(exception.getMessage())
            .build();
    }

    @ExceptionHandler(UnsatisfyingParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final ExceptionResponse handleUnsatisfyingParameterException(
        UnsatisfyingParameterException exception
    ) {
        return ExceptionResponse.builder()
            .timestamp(LocalDateTime.now())
            .message(exception.getMessage())
            .build();
    }

    @ExceptionHandler(IllegalOperationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final ExceptionResponse handleIllegalOperationException(
        IllegalOperationException exception
    ) {
        return ExceptionResponse.builder()
            .timestamp(LocalDateTime.now())
            .message(exception.getMessage())
            .build();
    }
}
