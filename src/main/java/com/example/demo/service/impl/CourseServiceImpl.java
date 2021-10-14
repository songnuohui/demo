package com.example.demo.service.impl;

import com.example.demo.entity.Course;
import com.example.demo.mapper.CourseMapper;
import com.example.demo.service.ICourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程表 服务实现类
 * </p>
 *
 * @author song
 * @since 2021-10-14
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements ICourseService {

}
