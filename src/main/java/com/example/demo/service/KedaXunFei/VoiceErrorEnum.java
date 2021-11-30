package com.example.demo.service.KedaXunFei;

import org.apache.commons.lang3.StringUtils;

/**
 * @author SongNuoHui
 * @date 2021/11/22 17:39
 */
public enum VoiceErrorEnum {

    SUCCESS_A("0", "成功"),
    ILLEGAL_ACCESS("10105", "没有权限"),
    INVALID_PARAMETER("10106", "无效参数"),
    ILLEGAL_PARAMETER("10107", "非法参数值"),
    NO_LICENSE("10110", "无授权许可"),
    ENGINE_ERROR("10700", "引擎错误"),
    WEBSOCKET_CONNECT_ERROR("10202", "websocket连接错误"),
    WEBSOCKET_WRITE_ERROR("10204", "服务端websocket写错误"),
    WEBSOCKET_READ_ERROR("10205", "服务端websocket读错误"),
    BASIC_COMPONENT_ERROR("16003", "基础组件异常"),
    OVER_MAX_CONNECT_LIMIT("10800", "超过授权的连接数");


    private String code;
    private String msg;

    VoiceErrorEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static String getMsgByCode(String code) {
        for (VoiceErrorEnum errorEnum : VoiceErrorEnum.values()) {
            if (StringUtils.equals(errorEnum.getCode(), code)) {
                return errorEnum.getMsg();
            }
        }
        return StringUtils.EMPTY;
    }
}
