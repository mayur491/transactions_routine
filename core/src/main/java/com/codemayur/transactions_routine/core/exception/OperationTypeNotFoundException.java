package com.codemayur.transactions_routine.core.exception;

public class OperationTypeNotFoundException extends OperationTypeException {

    private static final long serialVersionUID = 1L;

    public OperationTypeNotFoundException(String message) {
        super(message);
    }

    public OperationTypeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
