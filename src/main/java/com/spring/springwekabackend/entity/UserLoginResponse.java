package com.spring.springwekabackend.entity;

public class UserLoginResponse {
    private User data;
    private Boolean success;
    private String message;

    public UserLoginResponse(User data, Boolean success, String message) {
        this.data = data;
        this.success = success;
        this.message = message;
    }

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "UserLoginResponse{" +
                "data=" + data +
                ", success=" + success +
                ", message='" + message + '\'' +
                '}';
    }
}
