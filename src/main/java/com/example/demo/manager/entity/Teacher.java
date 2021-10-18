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
 * 教师表
 * </p>
 *
 * @author SongNuoHui
 * @since 2021-10-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_teacher")
public class Teacher implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "t_id", type = IdType.INPUT)
    private String tId;

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
    private Date tJobTime;

    /**
     * 教育背景
     */
    private String tEduBg;

    /**
     * 毕业学校
     */
    private String tGraIns;

    private String tPhone;

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
