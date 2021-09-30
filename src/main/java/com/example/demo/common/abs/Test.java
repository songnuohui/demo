package com.example.demo.common.abs;

import com.example.demo.service.TestService;

/**
 * @author SongNuoHui
 * @date 2021/9/14 10:02
 */
public abstract class Test implements TestService {

    @Override
    public String newInterface() {
        return "test abstract";
    }

    //抽象方法
    public abstract String testInterface();

    public String testMethod() {
        return "test method";
    }
}
