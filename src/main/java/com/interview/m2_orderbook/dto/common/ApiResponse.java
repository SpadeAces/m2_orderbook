package com.interview.m2_orderbook.dto.common;

public class ApiResponse <T>{
    private int status;
    private String message;
    private T data;
    public ApiResponse( int status, String message,T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
