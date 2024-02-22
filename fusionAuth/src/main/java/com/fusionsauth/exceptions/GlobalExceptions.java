package com.fusionsauth.exceptions;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptions extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserDuplicateException.class)
    public ResponseEntity<?> userDuplicateException(UserDuplicateException userDuplicateException, WebRequest webRequest){
        LocalDate localDate = LocalDate.now();
        Details details = new Details(localDate,userDuplicateException.getMessage(),webRequest.getDescription(false));
        return new ResponseEntity<>(details, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globalException(Exception exception,WebRequest webRequest){
        LocalDate localDate = LocalDate.now();
        Details details = new Details(localDate,webRequest.getDescription(false),exception.getMessage());
        return new ResponseEntity<>(details,HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(UserDetailsIncorrectException.class)
    public ResponseEntity<?> userDetailsIncorrectException(UserDetailsIncorrectException userDetailsIncorrectException,WebRequest webRequest){
        LocalDate localDate =LocalDate.now();
        Details details = new Details(localDate,userDetailsIncorrectException.getMessage(),webRequest.getDescription(false));
        return new ResponseEntity<>(details,HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        List<ObjectError> errorList = ex.getBindingResult().getAllErrors();

        errorList.forEach((error) ->{
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
