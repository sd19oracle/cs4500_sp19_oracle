package edu.neu.cs4500.services;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.CONFLICT, reason="User already registered with this email address")
public class DuplicateEmailException extends RuntimeException {}
