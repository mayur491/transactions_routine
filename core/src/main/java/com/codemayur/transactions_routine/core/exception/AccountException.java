package com.codemayur.transactions_routine.core.exception;

public class AccountException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public AccountException(String message) {
        super(message);
    }

    public AccountException(String message, Throwable cause) {
        super(message, cause);
    }

}
