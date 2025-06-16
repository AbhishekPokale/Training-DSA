package com.assignment.orderstatemachine.model;

//package com.example.orderstatemachine.exception;

public class InvalidOrderStateException extends RuntimeException {
    public InvalidOrderStateException(String message) {
        super(message);
    }
}

