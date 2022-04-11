package com.marionete.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
@RestController
public class CustomExceptionHandler {

    @ExceptionHandler(InvalidCredentialsException.class)
    public final ResponseEntity<Object> handleBadcredentialsException(Exception ex, WebRequest webRequest){
    ExceptionMessage exceptionMessage = new ExceptionMessage(new Date(), ex.getMessage(), webRequest.getDescription(false), HttpStatus.UNAUTHORIZED.toString());
    return new ResponseEntity<>(exceptionMessage,HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(EmptyCredentialsException.class)
    public final ResponseEntity<Object> handleEmptycredentialsException(Exception ex, WebRequest webRequest){
        ExceptionMessage exceptionMessage = new ExceptionMessage(new Date(), ex.getMessage(), webRequest.getDescription(false), HttpStatus.BAD_REQUEST.toString());
        return new ResponseEntity<>(exceptionMessage,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<Object> handleMethodArgumentInvalidException(MethodArgumentNotValidException ex, WebRequest webRequest){
        ExceptionMessage exceptionMessage = new ExceptionMessage(new Date(), ex.getMessage(), webRequest.getDescription(false), HttpStatus.BAD_REQUEST.toString());
        return new ResponseEntity<>(exceptionMessage,HttpStatus.BAD_REQUEST);
    }


}
