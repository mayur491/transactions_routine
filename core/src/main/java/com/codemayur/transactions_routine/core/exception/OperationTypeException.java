package com.codemayur.transactions_routine.core.exception;

public class OperationTypeException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public OperationTypeException(String message) {
        super(message);
    }

    public OperationTypeException(String message, Throwable cause) {
        super(message, cause);
    }

}
