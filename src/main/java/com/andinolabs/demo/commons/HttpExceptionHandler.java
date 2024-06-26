package com.andinolabs.demo.commons;

import com.andinolabs.demo.stateengine.StateTransitionException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HttpExceptionHandler {

    @ExceptionHandler(StateTransitionException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleStateTransitionException(StateTransitionException e) {
        return e.getMessage();
    }

}
