package com.project.busticket.exception;

public enum ErrorCode {
    NOT_NULL(1000, "One item is null"),
    INVALID_KEY(1001, "INVALID MESSAGE  KEY"),
    UNCATEGORIZED_EXCEPTION(9999, "uncateegorized error"),
    USER_EXISTED(1002, "User existed"),
    USER_NOT_EXISTED(1003, "User not existed"),
    USERNAME_INVALID(1004, "Username must be at least 3 charactor"),
    PASSWORD_INVALID(1004, "Password must be at lest 8 charactors"),
    UN_AUTHENTICATED(1005, "Unauthenticated"),
    EMAIL_INCORRECT(1006, "Email is not in correct format"),
    PHONE_INCORRECT(1007, "Phone number must start with 0 and have 10 digits"),
    NOT_BLANK(1008, "cannot be left blank this"),

    BUS_EXISTED(1009, "BusOperator existed"),
    BUS_UN_EXISTED(1010, "BusOperator not existed"),
    TIME_UNAVALIABLE(1011, "Time of trip must be in the future"),
    TRIP_INVALID(1012, "There should not be two buses from the same company at the same time"),
    TRIP_NOT_EXISTED(1013, "Trip not existed"),

    INVALID_NUMBER(1014, "Must be a positive integer and least 1"),
    PAYMENT_FAILED(1015, "Some thing may be wrong"),;

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
