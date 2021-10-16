package com.example.springboot.jasypt.service;

import com.example.springboot.jasypt.model.Type;
import com.example.springboot.jasypt.model.request.Decryption;
import com.example.springboot.jasypt.model.request.Encryption;
import org.jasypt.digest.StandardStringDigester;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.stereotype.Service;

@Service
public class JasyptServiceImpl implements JasyptService {

    @Override
    public String encrypt(Encryption encryption) {
        if (encryption.getType().equals(Type.TWO_WAY)) {
            return encryptTwoWay(encryption.getPlainText(), encryption.getSecretKey());
        }
        return encryptOneWay(encryption.getPlainText());
    }

    @Override
    public String decrypt(Decryption decryption) {
        if (decryption.getType().equals(Type.TWO_WAY)) {
            return decryptTwoWay(decryption.getSecretKey(), decryption.getEncryptedText());
        }
        var isMatch = matchEncryptOneWay(decryption.getMatchText(), decryption.getEncryptedText());

        return isMatch ? "Password Matched" : "Password do not match";
    }

    private String encryptOneWay(String plainText) {
        var digester = new StandardStringDigester();
        return digester.digest(plainText);
    }

    private String encryptTwoWay(String plainText, String secretKey) {
        var encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(secretKey);
        return encryptor.encrypt(plainText);
    }

    private boolean matchEncryptOneWay(String matchText, String encryptedText) {
        var digester = new StandardStringDigester();
        return digester.matches(matchText, encryptedText);
    }

    private String decryptTwoWay(String secretKey, String encryptedText) {
        var encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(secretKey);
        return encryptor.decrypt(encryptedText);
    }
}
