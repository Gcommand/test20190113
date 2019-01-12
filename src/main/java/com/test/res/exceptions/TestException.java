package com.test.res.exceptions;

/**
 * Created by user on 11/1/2019.
 */
public class TestException extends RuntimeException {
    private String errorCode;
    public TestException(String errorCode, String errorMessage, Throwable err) {
        super(errorMessage, err);
        setErrorCode(errorCode);
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
