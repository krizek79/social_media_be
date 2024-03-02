package com.krizan.social_media.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

import java.util.Objects;

public class ValueMatchingValidator implements ConstraintValidator<ValueMatching, Object> {

    private String field;
    private String matchField;

    @Override
    public void initialize(ValueMatching constraintAnnotation) {
        field = constraintAnnotation.value();
        matchField = constraintAnnotation.matchingValue();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        Object fieldValue = new BeanWrapperImpl(value).getPropertyValue(field);
        Object matchFieldValue = new BeanWrapperImpl(value).getPropertyValue(matchField);

        return Objects.equals(fieldValue, matchFieldValue);
    }
}