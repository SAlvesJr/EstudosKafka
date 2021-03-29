package com.libraryevents.exception;

public class OnFailureException extends RuntimeException {

    public OnFailureException (String msg) {
        super(msg);
    }
}