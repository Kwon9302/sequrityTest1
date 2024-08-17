package com.cos.security1.config.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UnauthorizedUserException.class)
    public String handleUnauthorizedUserException() {
        System.out.println("예외를 ControllerAdvice에서 잡았어요");
        return "redirect:/reject";
    }
}
