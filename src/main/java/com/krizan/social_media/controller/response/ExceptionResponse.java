package com.krizan.social_media.controller.response;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record ExceptionResponse(
    LocalDateTime timestamp,
    String message
) {
}
