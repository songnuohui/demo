package com.example.demo.leetcode;

import com.example.demo.manager.entity.Student;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author SongNuoHui
 * @date 2021/12/14 11:06
 */
@Slf4j
public class Te {

    private static final List<String> orgList = new ArrayList<>();

    private static final Student st = new Student();

    public static void main(String[] args) {


        for (int i = 0; i < 10; i++) {
            System.out.println("开始添加");
            st.setSUserName(""+i);
            System.out.println(st);
            log.error("ddddddd");
            System.out.println("继续执行");
            /*System.out.println("开始清除");
            orgList.clear();
            System.out.println("清除后的结果");
            System.out.println(orgList);*/
        }

    }
}
