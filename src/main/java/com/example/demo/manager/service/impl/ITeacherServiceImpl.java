package com.example.demo.manager.service.impl;

import com.example.demo.manager.entity.Teacher;
import com.example.demo.manager.mapper.TeacherMapper;
import com.example.demo.manager.service.ITeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 教师表 服务实现类
 * </p>
 *
 * @author SongNuoHui
 * @since 2021-10-18
 */
@Service
public class ITeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements ITeacherService {

}
