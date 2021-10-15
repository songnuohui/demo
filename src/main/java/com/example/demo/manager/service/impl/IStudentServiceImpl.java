package com.example.demo.manager.service.impl;

import com.example.demo.manager.entity.Student;
import com.example.demo.manager.mapper.StudentMapper;
import com.example.demo.manager.service.IStudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
