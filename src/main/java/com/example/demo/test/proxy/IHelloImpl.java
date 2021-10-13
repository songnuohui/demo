package com.example.demo.test.proxy;

/**
 * @author SongNuoHui
 * @date 2021/10/12 17:01
 */
public class IHelloImpl implements IHello {
    @Override
    public void sayHello(String name) {
        System.out.println("hello " + name);
    }

    @Override
    public void sayGoodBye(String name) {
        System.out.println("goodBye " + name);
    }
}
