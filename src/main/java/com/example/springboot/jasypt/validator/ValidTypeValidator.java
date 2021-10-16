package com.example.springboot.jasypt.validator;

import com.example.springboot.jasypt.model.Type;
import com.example.springboot.jasypt.validator.annotation.ValidTypeConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidTypeValidator implements ConstraintValidator<ValidTypeConstraint, Type> {

    @Override
    public boolean isValid(Type type, ConstraintValidatorContext constraintValidatorContext) {
        if (type == null) {
            return false;
        }

        return type.equals(Type.ONE_WAY) || type.equals(Type.TWO_WAY);
    }
}
