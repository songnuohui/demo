package com.example.demo.manager.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 学生表
 * </p>
 *
 * @author SongNuoHui
 * @since 2021-10-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_student")
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "s_id", type = IdType.INPUT)
    private String sId;

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

    private Boolean delFlag;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;


}
