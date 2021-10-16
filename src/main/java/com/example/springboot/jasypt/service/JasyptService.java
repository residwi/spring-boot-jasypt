package com.example.springboot.jasypt.service;

import com.example.springboot.jasypt.model.request.Decryption;
import com.example.springboot.jasypt.model.request.Encryption;

public interface JasyptService {

    String encrypt(Encryption encryption);

    String decrypt(Decryption decryption);
}
