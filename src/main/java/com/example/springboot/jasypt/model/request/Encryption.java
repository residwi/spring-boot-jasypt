package com.example.springboot.jasypt.model.request;

import com.example.springboot.jasypt.model.Type;
import com.example.springboot.jasypt.validator.annotation.TwoWayConstraint;
import com.example.springboot.jasypt.validator.annotation.ValidTypeConstraint;

import javax.validation.constraints.NotBlank;

@TwoWayConstraint
public class Encryption {

    @NotBlank
    private String plainText;

    @ValidTypeConstraint
    private Type type;

    private String secretKey;

    public Encryption() {
    }

    public Encryption(String plainText, Type type) {
        this.plainText = plainText;
        this.type = type;
    }

    public Encryption(String plainText, Type type, String secretKey) {
        this.plainText = plainText;
        this.type = type;
        this.secretKey = secretKey;
    }

    public String getPlainText() {
        return plainText;
    }

    public void setPlainText(String plainText) {
        this.plainText = plainText;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}
