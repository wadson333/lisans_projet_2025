package com.ciatech.uniconnect.security;

import org.springframework.security.core.AuthenticationException;

public class UserBlockedException extends AuthenticationException {

    private static final long serialVersionUID = 1L;

    public UserBlockedException(String message) {
        super(message);
    }

    public UserBlockedException(String message, Throwable t) {
        super(message, t);
    }
}
