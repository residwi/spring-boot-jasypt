package com.example.springboot.jasypt.controller;

import com.example.springboot.jasypt.model.request.Decryption;
import com.example.springboot.jasypt.model.request.Encryption;
import com.example.springboot.jasypt.model.response.ResponseData;
import com.example.springboot.jasypt.service.JasyptService;
import com.gitlab.residwi.spring.library.common.helper.ResponseHelper;
import com.gitlab.residwi.spring.library.common.model.response.ResponsePayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@Validated
public class ApiController {

    private final JasyptService jasyptService;

    @Autowired
    public ApiController(JasyptService jasyptService) {
        this.jasyptService = jasyptService;
    }

    @PostMapping("/encrypt")
    public ResponsePayload<ResponseData> encrypt(@Valid @RequestBody Encryption encryption) {
        var result = jasyptService.encrypt(encryption);

        return ResponseHelper.ok(new ResponseData(result));
    }

    @PostMapping("/decrypt")
    public ResponsePayload<ResponseData> decrypt(@Valid @RequestBody Decryption decryption) {
        var result = jasyptService.decrypt(decryption);

        return ResponseHelper.ok(new ResponseData(result));
    }
}
