package com.example.demo.test;

import com.example.demo.common.request.IpResult;
import com.example.demo.common.request.User;
import com.example.demo.service.impl.SonTest;
import com.example.demo.service.TestService;

/**
 * @author SongNuoHui
 * @date 2021/9/14 9:41
 */
public class Test {
    public static void main(String[] args) {
        TestService testService = new TestService(){
            @Override
            public User setCookie() {
                return null;
            }

            @Override
            public IpResult getIpDetail(String ip) {
                return null;
            }

            @Override
            public String newInterface() {
                return "hello word";
            }
        };
        System.out.println(testService.newInterface());
        System.out.println("----------------");

        SonTest sonTest = new SonTest();
        sonTest.setName("songnuohui");
        sonTest.setGender("man");
        sonTest.setAge(22);
        System.out.println(sonTest.testInterface());
        System.out.println(sonTest.testMethod());
        System.out.println(sonTest.newInterface());
        System.out.println("----------------");

        com.example.demo.common.abs.Test test = new SonTest();
        System.out.println(test.testMethod());
        System.out.println(test.testInterface());
        System.out.println(test.newInterface());

    }
}
