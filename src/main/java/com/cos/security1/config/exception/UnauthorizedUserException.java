package com.cos.security1.config.exception;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class UnauthorizedUserException extends AuthenticationException {
    public UnauthorizedUserException(String message) {
        super(message);
    }
}
