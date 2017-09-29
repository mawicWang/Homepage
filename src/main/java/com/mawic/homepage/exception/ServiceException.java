package com.mawic.homepage.exception;

public class ServiceException extends Exception {

    private ErrorCode errorCode;

    public ServiceException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ServiceException(ErrorCode errorCode, Throwable t) {
        super(errorCode.getMessage(), t);
        this.errorCode = errorCode;
    }

}
