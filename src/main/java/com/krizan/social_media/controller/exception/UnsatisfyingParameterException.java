package com.krizan.social_media.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UnsatisfyingParameterException extends RuntimeException {

    public UnsatisfyingParameterException(String message) {
        super(message);
    }
}
