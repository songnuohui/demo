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

import javax.sound.sampled.FloatControl;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

    @ApiOperation("多线程查询1")
    @RequestMapping(method = RequestMethod.GET, value = "/multi-query1.json")
    public RichResult<?> multiQuery1() {
        List<List<Student>> lists = studentService.multiQuery();
        return R.ok(lists);
    }

    @ApiOperation("时间比较")
    @RequestMapping(method = RequestMethod.GET, value = "/query-compare.json")
    public RichResult<?> queryCompare() {
        Map<String, String> map = null;
        try {
            map = studentService.queryTime();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return R.ok(map);
    }


    @ApiOperation("批量插入")
    @RequestMapping(method = RequestMethod.POST, value = "/batch-insert.json")
    public RichResult<?> batchInsert() {
        studentService.batchInsert();
        return R.ok();
    }

    @ApiOperation("多线程批量插入")
    @RequestMapping(method = RequestMethod.POST, value = "/multi-insert.json")
    public RichResult<?> multiBatchInsert() {
        studentService.multiBatchInsert();
        return R.ok();
    }


    @ApiOperation("根据时间删除")
    @RequestMapping(method = RequestMethod.POST, value = "/remove.json")
    public RichResult<?> remove() {
        ZonedDateTime zonedDateTime = LocalDateTime.now().minusHours(1).atZone(ZoneId.systemDefault());
        Date from = Date.from(zonedDateTime.toInstant());
        ZonedDateTime zonedDateTime1 = LocalDateTime.now().atZone(ZoneId.systemDefault());
        Date to = Date.from(zonedDateTime1.toInstant());
        int i = studentService.deleteAll(from, to);
        return R.ok(i);
    }


}
