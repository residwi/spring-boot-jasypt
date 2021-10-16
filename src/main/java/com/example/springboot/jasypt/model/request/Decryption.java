package com.example.springboot.jasypt.model.request;

import com.example.springboot.jasypt.model.Type;
import com.example.springboot.jasypt.validator.annotation.OneWayDecryptionConstraint;
import com.example.springboot.jasypt.validator.annotation.TwoWayConstraint;
import com.example.springboot.jasypt.validator.annotation.ValidTypeConstraint;

import javax.validation.constraints.NotBlank;

@OneWayDecryptionConstraint
@TwoWayConstraint
public class Decryption {

    @NotBlank
    private String encryptedText;

    @ValidTypeConstraint
    private Type type;

    private String matchText;

    private String secretKey;

    public Decryption() {
    }

    public Decryption(String encryptedText, Type type, String matchText) {
        this.encryptedText = encryptedText;
        this.type = type;
        this.matchText = matchText;
    }

    public Decryption(String encryptText, Type type, String matchText, String secretKey) {
        this.encryptedText = encryptText;
        this.type = type;
        this.matchText = matchText;
        this.secretKey = secretKey;
    }

    public String getEncryptedText() {
        return encryptedText;
    }

    public void setEncryptedText(String encryptedText) {
        this.encryptedText = encryptedText;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getMatchText() {
        return matchText;
    }

    public void setMatchText(String matchText) {
        this.matchText = matchText;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

}
