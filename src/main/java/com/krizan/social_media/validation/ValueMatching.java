package com.krizan.social_media.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ValueMatchingValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValueMatching {

    String value();
    String matchingValue();

    String message() default "Values do not match.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}