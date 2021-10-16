package com.example.springboot.jasypt.validator.annotation;

import com.example.springboot.jasypt.validator.OneWayDecryptionValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = OneWayDecryptionValidator.class)
public @interface OneWayDecryptionConstraint {
    String message() default "Empty Match Text";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
