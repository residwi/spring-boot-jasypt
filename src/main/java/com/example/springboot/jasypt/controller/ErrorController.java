package com.example.springboot.jasypt.controller;

import com.gitlab.residwi.spring.library.common.exception.CommonErrorException;
import com.gitlab.residwi.spring.library.common.model.response.ResponsePayload;
import org.jasypt.exceptions.EncryptionInitializationException;
import org.jasypt.exceptions.EncryptionOperationNotPossibleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.*;

@RestControllerAdvice
public class ErrorController implements CommonErrorException, MessageSourceAware {
    private static final Logger log = LoggerFactory.getLogger(ErrorController.class);

    private MessageSource messageSource;

    @Override
    public Logger getLogger() {
        return log;
    }

    @Override
    public MessageSource getMessageSource() {
        return messageSource;
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler({NoHandlerFoundException.class})
    public ResponseEntity<ResponsePayload<Object>> methodArgumentNotValidException(NoHandlerFoundException e) {
        var response = new ResponsePayload<>();
        response.setCode(HttpStatus.NOT_FOUND.value());
        response.setStatus(HttpStatus.NOT_FOUND.name());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler({EncryptionInitializationException.class, EncryptionOperationNotPossibleException.class})
    public ResponseEntity<ResponsePayload<Object>> encryptionInitializationException(RuntimeException e) {
        log.warn(e.getClass().getName(), e);

        var message = e.getMessage() == null ? "Invalid Encrypted Text" : e.getMessage();
        var errors = Map.of("encryptedText", List.of(message));

        var response = new ResponsePayload<>();
        response.setCode(HttpStatus.BAD_REQUEST.value());
        response.setStatus(HttpStatus.BAD_REQUEST.name());
        response.setErrors(errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @Override
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ResponsePayload<Object>> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.warn(MethodArgumentNotValidException.class.getName(), e);

        var errors = CommonErrorException.from(e.getBindingResult(), this.getMessageSource());
        var customErrors = addCustomValidatorErrors(e.getBindingResult(), errors);

        var response = new ResponsePayload<>();
        response.setCode(HttpStatus.BAD_REQUEST.value());
        response.setStatus(HttpStatus.BAD_REQUEST.name());
        response.setErrors(customErrors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    private Map<String, List<String>> addCustomValidatorErrors(BindingResult result, Map<String, List<String>> errors) {
        var customValidator = Set.of("TwoWayConstraint", "OneWayDecryptionConstraint");

        var customErrors = new HashMap<>(errors);
        result.getGlobalErrors().forEach(objectError -> {
            if (customValidator.contains(objectError.getCode())) {
                customErrors.put(objectError.getObjectName(), List.of(Objects.requireNonNull(objectError.getDefaultMessage())));
            }
        });

        return customErrors;
    }
}
