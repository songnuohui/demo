package com.example.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.manager.entity.Student;
import com.mysql.cj.Constants;
import org.apache.ibatis.annotations.Param;

import java.sql.Wrapper;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author SongNuoHui
 * @date 2021/10/15 11:14
 */
public interface StudentService {

    /**
     * 多线程查询
     */
    List<List<Student>> multiThreadQuery();

    List<Student> query();

    List<List<Student>> multiQuery();

    /**
     * 单线程和多线程查询所用时间
     */
    Map<String, String> queryTime() throws InterruptedException;

    /**
     * 批量插入
     */
    void batchInsert();

    /** 多线程批量插入 */
    void multiBatchInsert();

    int deleteAll(Date from, Date to);
}
