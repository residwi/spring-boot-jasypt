package com.example.springboot.jasypt.validator;

import com.example.springboot.jasypt.model.Type;
import com.example.springboot.jasypt.model.request.Decryption;
import com.example.springboot.jasypt.validator.annotation.OneWayDecryptionConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class OneWayDecryptionValidator implements ConstraintValidator<OneWayDecryptionConstraint, Decryption> {

    @Override
    public boolean isValid(Decryption decryption, ConstraintValidatorContext constraintValidatorContext) {
        return (decryption.getType() != null && !decryption.getType().equals(Type.ONE_WAY)) ||
                (decryption.getMatchText() != null && !decryption.getMatchText().isBlank());
    }
}
