package com.example.demo.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.manager.entity.Student;
import com.example.demo.manager.mapper.StudentMapper;
import com.example.demo.manager.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


/**
 * <p>
 * 学生表 服务实现类
 * </p>
 *
 * @author SongNuoHui
 * @since 2021-10-15
 */
@Service(value = "IStudentServiceImpl")
public class IStudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements IStudentService {

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public List<Student> queryLimit(int startIndex, int pageSize) {
        return studentMapper.queryLimit(startIndex, pageSize);
    }

    @Override
    public int removeAll(Date from, Date to) {
        LambdaQueryWrapper<Student> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Student::getDelFlag,0).between(Student::getCreateTime,from,to);
        return baseMapper.delete(wrapper);
    }
}
