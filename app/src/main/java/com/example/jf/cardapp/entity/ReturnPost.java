package com.example.jf.cardapp.entity;

/**
 * Created by jf on 2017/2/26.
 */

public class ReturnPost {
    private int ErrorCode;
    private String Message;
    public ReturnPost(){}

    public ReturnPost(int errorCode, String message) {
        ErrorCode = errorCode;
        Message = message;
    }

    public int getErrorCode() {
        return ErrorCode;
    }

    public void setErrorCode(int errorCode) {
        ErrorCode = errorCode;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    @Override
    public String toString() {
        return "ReturnPost{" +
                "ErrorCode=" + ErrorCode +
                ", Message='" + Message + '\'' +
                '}';
    }
}
