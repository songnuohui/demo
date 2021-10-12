package com.example.demo.proxy;

/**
 * @author SongNuoHui
 * @date 2021/10/12 17:02
 */
public class MyProxyTest {
    public static void main(String[] args) {
        //要代理的真实对象
        IHelloImpl iHello = new IHelloImpl();

        MyProxy myProxy = new MyProxy();
        IHello hello = (IHello) myProxy.bind(iHello);

        System.out.println("begin");
        hello.sayHello("song");
        //hello.sayGoodBye("Song");
        System.out.println("end");

    }
}
