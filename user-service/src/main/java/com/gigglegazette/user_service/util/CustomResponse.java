package com.gigglegazette.user_service.util;

public class CustomResponse<T> {
    private String message;
    private T data;
    private boolean success;

    public CustomResponse(String message, T data, boolean success) {
        this.message = message;
        this.data = data;
        this.success = success;
    }

    // Getters and setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
