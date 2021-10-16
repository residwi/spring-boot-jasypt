package com.example.springboot.jasypt.validator;

import com.example.springboot.jasypt.model.Type;
import com.example.springboot.jasypt.model.request.Decryption;
import com.example.springboot.jasypt.model.request.Encryption;
import com.example.springboot.jasypt.validator.annotation.TwoWayConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TwoWayValidator implements ConstraintValidator<TwoWayConstraint, Object> {

    @Override
    public boolean isValid(Object request, ConstraintValidatorContext constraintValidatorContext) {
        if (request instanceof Encryption) {
            var o = (Encryption) request;
            return twoWayNotEmptySecretKey(o.getType(), o.getSecretKey());
        } else if (request instanceof Decryption) {
            var o = (Decryption) request;
            return twoWayNotEmptySecretKey(o.getType(), o.getSecretKey());
        }
        return false;
    }

    private boolean twoWayNotEmptySecretKey(Type type, String secretKey) {
        return (type != null && !type.equals(Type.TWO_WAY)) || (secretKey != null && !secretKey.isBlank());
    }
}
