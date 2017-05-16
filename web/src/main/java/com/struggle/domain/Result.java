package com.struggle.domain;

/**
 * @author xuchengdongxcd@126.com on 2016/12/1.
 */
public final class Result<T> {

    public static final int SUCCESS = 1000;
    public static final int FAIL = 9999;

    private int code;
    private String message;
    private T data;

    private Result(int code) {
        this.code = code;
    }

    public Result(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public Result(int code, T data) {
        this.code = code;
        this.data = data;
    }

    public Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static Result success() {
        return new Result(SUCCESS);
    }

    public static Result success(String message) {
        return new Result(SUCCESS, message);
    }

    public static Result success(Object data) {
        return new Result<Object>(SUCCESS, data);
    }

    public static Result fail() {
        return new Result(FAIL);
    }

    public static Result fail(String message) {
        return new Result(FAIL, message);
    }

    public static Result fail(int code, String message) {
        return new Result(code, message);
    }

    public static Result fail(int code, String message, Object data) {
        return new Result<Object>(code, message, data);
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return code != FAIL;
    }
}
