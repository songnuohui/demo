package com.example.demo.common.request;

import lombok.Data;

import java.time.LocalDate;

/**
 * @author SongNuoHui
 * @date 2021/10/15 9:41
 */
@Data
public class TeaRequest {


    private String tName;

    private String tSex;

    /**
     * 身份信息
     */
    private String tIdCard;

    private String tAddress;

    /**
     * 开始教学时间
     */
    private LocalDate tJobTime;

    /**
     * 教育背景
     */
    private String tEduBg;

    /**
     * 毕业学校
     */
    private String tGraIns;

    private String tPhone;

}
