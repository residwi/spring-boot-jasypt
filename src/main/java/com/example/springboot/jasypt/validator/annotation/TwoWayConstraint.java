package com.example.springboot.jasypt.validator.annotation;

import com.example.springboot.jasypt.validator.TwoWayValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TwoWayValidator.class)
public @interface TwoWayConstraint {
    String message() default "Empty Secret Key";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
