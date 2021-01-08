package com.example.twitter.clone.exception;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Header;

public class CustomExpiredJwtExceptionHandler extends ExpiredJwtException {
    public CustomExpiredJwtExceptionHandler(Header header, Claims claims, String message) {
        super(header, claims, message);
    }
}
