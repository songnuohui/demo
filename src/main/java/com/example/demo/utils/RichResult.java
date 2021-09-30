package com.example.demo.utils;

/**
 * @author SongNuoHui
 * @date 2021/8/20 15:23
 */

public class RichResult<T> {
    private Integer state;
    private String internalErrorCode;
    private String msg;
    private T results;

    public RichResult() {
        this.internalErrorCode = "0";
        this.msg = "success";
        this.state = 200;
    }

    public RichResult(T results) {
        this();
        this.setState(200);
        this.results = results;
    }

    public Integer getState() {
        return this.state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getResults() {
        return this.results;
    }

    public void setResults(T results) {
        this.results = results;
    }

    public String getInternalErrorCode() {
        return this.internalErrorCode;
    }

    public void setInternalErrorCode(String internalErrorCode) {
        this.internalErrorCode = internalErrorCode;
    }

    public RichResult(Integer errorCode, String errorMsg) {
        this.internalErrorCode = "0";
        this.state = errorCode;
        this.msg = errorMsg;
    }

    public RichResult(Integer state, String internalErrorCode, String errorMsg) {
        this.state = state;
        this.internalErrorCode = internalErrorCode;
        this.msg = errorMsg;
    }

}
