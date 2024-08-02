package com.example.cashout.controller;

import com.example.cashout.exception.CashoutNoFoundException;
import com.example.cashout.exception.Exception400Exception;
import com.example.cashout.exception.PaymentsException;
import com.example.cashout.exception.UserNoFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;


@RestControllerAdvice
public class GlobalExceptionHandle {


    @ExceptionHandler(Exception400Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Mono<ResponseEntity<String>> handleException400Exception(Exception400Exception exception){
        return Mono.just(ResponseEntity.badRequest().body(exception.getMessage()));
    }


    @ExceptionHandler(UserNoFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Mono<ResponseEntity<String>> handleUserNoFoundException(UserNoFoundException exception){
        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage()));
    }

    @ExceptionHandler(CashoutNoFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Mono<ResponseEntity<String>> handleCashoutNoFoundException(CashoutNoFoundException exception){
        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage()));
    }

    @ExceptionHandler(PaymentsException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Mono<ResponseEntity<String>> handlePaymentsException(PaymentsException exception){
        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage()));
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<String> handleValidationExceptions(WebExchangeBindException ex) {
        String errorMessage = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

}
