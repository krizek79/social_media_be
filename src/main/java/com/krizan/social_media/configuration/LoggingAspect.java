package com.krizan.social_media.configuration;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    private static final String LOG_MESSAGE =
        "URL: %s | HTTP method: %s | Method name: %s | Issued at: %s";

    @Before("execution(* com.krizan.social_media.controller.endpoint..*(..))")
    public void logControllerMethodCall(JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
            .currentRequestAttributes()).getRequest();

        LocalDateTime now = LocalDateTime.now();
        String url = request.getRequestURI();
        String httpMethod = request.getMethod();
        String methodName = joinPoint.getSignature().toShortString();

        logger.info(LOG_MESSAGE.formatted(url, httpMethod, methodName, now));
    }
}