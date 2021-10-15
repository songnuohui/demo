package com.example.demo.service;

import com.example.demo.manager.entity.Student;

import java.util.List;

/**
 * @author SongNuoHui
 * @date 2021/10/15 11:14
 */
public interface StudentService {

    /**
     * 多线程查询
     */
    List<List<Student>> multiThreadQuery();

}
