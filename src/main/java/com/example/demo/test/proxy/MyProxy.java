package com.example.demo.test.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * java动态代理
 *
 * @author SongNuoHui
 * @date 2021/10/12 16:27
 */
public class MyProxy implements InvocationHandler {

    //代理类中的真实对象
    private Object obj;

    public Object bind(Object obj) {
        this.obj = obj;
        /**
         * loader：一个classloader对象，定义了由哪个classloader对象对生成的代理类进行加载
         *
         * interfaces：一个interface对象数组，表示我们将要给我们的代理对象提供一组什么样的接口，
         * 如果我们提供了这样一个接口对象数组，那么也就是声明了代理类实现了这些接口，代理类就可以调用接口中声明的所有方法。
         *
         * h：一个InvocationHandler对象，
         * 表示的是当动态代理对象调用方法的时候会关联到哪一个InvocationHandler对象上，并最终由其调用。
         */
        return Proxy.newProxyInstance
                (this.obj.getClass().getClassLoader(),
                        this.obj.getClass().getInterfaces(),
                        this);
    }

    /**
     * proxy:代理类代理的真实代理对象com.sun.proxy.$Proxy0
     * method:我们所要调用某个对象真实的方法的Method对象
     * args:指代代理对象方法传递的参数
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;
        String methodName = method.getName();

        //假如方法名是sayHello
        if (methodName.equals("sayHello")) {
            System.out.println("执行sayHello中的方法！！！");
        }

        //在真实的对象执行之前我们可以添加自己的操作,如上操作
        result = method.invoke(this.obj, args);

        //在真实的对象执行之后我们可以添加自己的操作,如下操作
        if (methodName.equals("sayGoodBye")) {
            System.out.println("执行sayGoodBye的方法");
        }
        return result;
    }
}
