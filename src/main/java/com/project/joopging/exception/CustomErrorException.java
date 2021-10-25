package com.project.joopging.exception;

public class CustomErrorException extends RuntimeException {
    public CustomErrorException(String msg) {
        super(msg);
    }
}
