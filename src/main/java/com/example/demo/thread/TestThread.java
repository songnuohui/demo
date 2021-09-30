package com.example.demo.thread;

import java.time.LocalTime;

/**
 * 测试多线程：
 * 创建线程
 * 中断线程
 * 守护线程
 * @author SongNuoHui
 * @date 2021/9/15 11:18
 */
public class TestThread {
    public static void main(String[] args) throws InterruptedException {

        System.out.println("main start，开始时间为：" + LocalTime.now() + "\n");

        System.out.println("------------继承Thread类---------------");
        for (int i = 0; i < 10; i++) {
            Thread myThread = new MyThread();
            System.out.println(i);
            if (i != 5) {
                myThread.start();
            } else {
                myThread.stop();
            }
            //Thread.sleep(500);
        }
        Thread.sleep(500);

        System.out.println("\n------------实现Runnable接口---------------");
        for (int i = 10; i < 20; i++) {
            Thread myRunnable = new Thread(new MyRunnable());
            System.out.println(i);
            myRunnable.start();
            //Thread.sleep(500);
            /**
             * 通过对另一个线程对象调用join() 方法可以等待其执行结束；
             * 可以指定等待时间，超过等待时间线程仍然没有结束就不再等待；
             * 对已经运行结束的线程调用join() 方法会立刻返回。
             * */
            myRunnable.join();
        }
        Thread.sleep(500);

        System.out.println("\n------------实例化Runnable接口---------------");
        Runnable runnable = () -> System.out.println("实例化Runnable接口");
        for (int i = 20; i < 30; i++) {
            Thread thread = new Thread(runnable);
            thread.start();
            System.out.println(i);
            //Thread.sleep(500);
        }
        Thread.sleep(500);

        System.out.println("\n------------线程中断---------------");
        Thread t = new InterruptedThread();
        t.start();
        Thread.sleep(500);
        t.interrupt();

        System.out.println("\n------------volatile关键字，设置标志位---------------");
        VThread v = new VThread();
        v.start();
        Thread.sleep(1);
        v.running = false;

        System.out.println("\n------------设置守护线程---------------");
        Thread timeThread = new TimerThread();
        timeThread.setDaemon(true);
        timeThread.start();

        System.out.println("\nmain end，结束时间为：" + LocalTime.now());
    }
}

/**
 * 继承Thread类
 */
class MyThread extends Thread {
    @Override
    public void run() {
        System.out.println("start new Thread");
    }
}

/**
 * 实现Runnable接口
 */
class MyRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println("start new Runnable");
    }
}

/**
 * 线程中断isInterrupted()
 */
class InterruptedThread extends Thread {
    @Override
    public void run() {
        int n = 0;
        while (!isInterrupted()) {
            n++;
            System.out.println(n + " HelloThread!");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}

/**
 * 设立标志位
 * 线程间共享变量需要使用volatile关键字标记，确保每个线程都能读取到更新后的变量值
 * volatile关键字解决了共享变量在线程间的可见性问题。
 */
class VThread extends Thread {
    public volatile boolean running = true;

    public void run() {
        int n = 0;
        while (running) {
            n++;
            System.out.println(n + " hello VThread!");
        }
        System.out.println("end!");
    }
}

/**
 * 守护线程  守护线程是指为其他线程服务的线程。
 * 在JVM中，所有非守护线程都执行完毕后，无论有没有守护线程，虚拟机都会自动退出
 */
class TimerThread extends Thread {
    @Override
    public void run() {
        while (true) {
            System.out.println("守护线程结束时间：" + LocalTime.now());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}

