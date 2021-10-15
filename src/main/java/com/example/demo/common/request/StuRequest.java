package com.example.demo.common.request;

import lombok.Data;

/**
 * @author SongNuoHui
 * @date 2021/10/15 9:41
 */
@Data
public class StuRequest {

    /**
     * 名字
     */
    private String sName;

    /**
     * 用户名
     */
    private String sUserName;

    /**
     * 密码
     */
    private String sPass;

    private String sSex;

    /**
     * 身份证
     */
    private String sIdCard;

    private String sAddress;


}
