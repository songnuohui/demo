package com.example.demo.manager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.manager.entity.Student;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 学生表 服务类
 * </p>
 *
 * @author SongNuoHui
 * @since 2021-10-15
 */
public interface IStudentService extends IService<Student> {

    List<Student> queryLimit(int startIndex, int pageSize);

    int removeAll(Date from , Date to);
}
