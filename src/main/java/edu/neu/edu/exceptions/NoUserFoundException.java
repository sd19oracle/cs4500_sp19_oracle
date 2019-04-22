package edu.neu.edu.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.UNAUTHORIZED, reason="Username/Password is incorrect")
public class NoUserFoundException extends RuntimeException {}
