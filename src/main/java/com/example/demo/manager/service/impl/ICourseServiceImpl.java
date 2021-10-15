package com.example.demo.manager.service.impl;

import com.example.demo.manager.entity.Course;
import com.example.demo.manager.mapper.CourseMapper;
import com.example.demo.manager.service.ICourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程表 服务实现类
 * </p>
 *
 * @author SongNuoHui
 * @since 2021-10-15
 */
@Service
public class ICourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements ICourseService {

}
