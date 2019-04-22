package edu.neu.cs4500.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.UNAUTHORIZED, reason="Username/Password is incorrect")
public class NoUserFoundException extends RuntimeException {}
