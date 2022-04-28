package com.example.demo.test;

import com.example.demo.common.request.User;
import com.example.demo.service.TestService;
import com.example.demo.service.impl.SonTest;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author SongNuoHui
 * @date 2021/9/14 9:41
 */
public class Test {

    @Autowired
    private TestService testService;

    public static void main(String[] args) {

        SonTest sonTest = new SonTest();
        sonTest.setName("songnuohui");
        sonTest.setGender("man");
        sonTest.setAge(22);
        System.out.println(sonTest.testInterface());
        System.out.println(sonTest.testMethod());
        System.out.println(sonTest.newInterface());
        System.out.println(sonTest.setCookie());
        System.out.println("----------------");

        com.example.demo.common.abs.Test test = new SonTest();
        User user = test.setCookie();
        System.out.println(user.toString());

        System.out.println(test.testMethod());
        System.out.println(test.testInterface());
        System.out.println(test.newInterface());

    }
}
