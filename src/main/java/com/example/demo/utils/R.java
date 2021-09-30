package com.example.demo.utils;

/**
 * @author SongNuoHui
 * @date 2021/8/20 15:29
 */
public class R<T> extends RichResult {
    public R() {
    }

    public static RichResult ok() {
        return new RichResult();
    }

    public static <T> RichResult<T> ok(T t) {
        return new RichResult(t);
    }

    public static RichResult error(String errorMsg) {
        return new RichResult(500, errorMsg);
    }

    public static RichResult success(String msg) {
        return new RichResult(200, msg);
    }

    public static RichResult badRequest(String errorMsg) {
        return new RichResult(400, errorMsg);
    }
}
