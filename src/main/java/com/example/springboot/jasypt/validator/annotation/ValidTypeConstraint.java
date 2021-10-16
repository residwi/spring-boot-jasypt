package com.example.springboot.jasypt.validator.annotation;

import com.example.springboot.jasypt.validator.ValidTypeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidTypeValidator.class)
public @interface ValidTypeConstraint {
    String message() default "Invalid Encryption or Decryption Type";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
