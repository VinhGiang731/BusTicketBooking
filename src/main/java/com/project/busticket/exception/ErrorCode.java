package com.project.busticket.exception;

public enum ErrorCode {
    INVALID_KEY(1001, "INVALID MESSAGE  KEY"),
    UNCATEGORIZED_EXCEPTION(9999, "uncateegorized error"),
    USER_EXISTED(1002, "User existed"),
    USER_NOT_EXISTED(1003, "User not existed"),
    USERNAME_INVALID(1004, "Username must be at least 3 charactor"),
    PASSWORĐ_INVALID(1004, "Password must be at lest 8 charactors"),
    UN_AUTHENTICATED(1005, "Unauthenticated"),
    ;

    private int code;
    private String message;

    private ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
