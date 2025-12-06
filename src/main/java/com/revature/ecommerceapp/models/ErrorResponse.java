package com.revature.ecommerceapp.models;

public class ErrorResponse {

    private String errorMessage;
    private String exceptionInfo;

    public ErrorResponse(String errorMessage, String exceptionInfo) {
        this.errorMessage = errorMessage;
        this.exceptionInfo = exceptionInfo;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getExceptionInfo() {
        return exceptionInfo;
    }

    public void setExceptionInfo(String exceptionInfo) {
        this.exceptionInfo = exceptionInfo;
    }
}
