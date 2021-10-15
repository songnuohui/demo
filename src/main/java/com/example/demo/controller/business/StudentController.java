package com.example.demo.controller.business;


import com.example.demo.manager.entity.Student;
import com.example.demo.service.StudentService;
import com.example.demo.utils.R;
import com.example.demo.utils.RichResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 学生表 前端控制器
 * </p>
 *
 * @author SongNuoHui
 * @since 2021-10-15
 */
@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @ApiOperation("多线程查询")
    @RequestMapping(method = RequestMethod.GET, value = "/multi-query.json")
    public RichResult<?> multiQuery() {
        List<List<Student>> lists = studentService.multiThreadQuery();
        return R.ok(lists);
    }

}
