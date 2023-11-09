package com.billlog.publicweatherapi.global.custom.exception;

public class BLBusinessException extends RuntimeException {
    public BLBusinessException(String message) {
        super(message);
    }
}