package com.example.demo.test.limit;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 限流算法
 * 计数器固定窗口算法：
 * 请求次数小于阈值，允许访问并且计数器 +1；
 * 请求次数大于阈值，拒绝访问；
 * 这个时间窗口过了之后，计数器清零；
 *
 * @author SongNuoHui
 * @date 2021/9/30 11:36
 */
public class CounterLimiter {

    /**
     * 窗口大小，毫秒为单位
     **/
    private int windowSize;
    /**
     * 窗口内限流大小
     **/
    private int limit;
    /**
     * 当前窗口的计数器
     **/
    private AtomicInteger count;

    private CounterLimiter() {
    }

    public CounterLimiter(int windowSize, int limit) {
        this.limit = limit;
        this.windowSize = windowSize;
        count = new AtomicInteger(0);

        //开启一个线程，达到窗口结束时清空count
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    count.set(0);
                    try {
                        Thread.sleep(windowSize);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    //请求到达后先调用本方法，若返回true，则请求通过，否则限流
    public boolean tryAcquire() {
        int newCount = count.addAndGet(1);
        if (newCount > limit) {
            return false;
        } else {
            return true;
        }
    }

    //测试
    public static void main(String[] args) throws InterruptedException {
        //每秒20个请求
        CounterLimiter counterLimiter = new CounterLimiter(1000, 20);
        int count = 0;
        //模拟50次请求，看多少能通过
        for (int i = 0; i < 10000; i++) {
            if (counterLimiter.tryAcquire()) {
                count++;
            }
        }
        System.out.println("第一拨50次请求中通过：" + count + ",限流：" + (10000 - count));
        //过一秒再请求
        Thread.sleep(1000);
        //模拟50次请求，看多少能通过
        count = 0;
        for (int i = 0; i < 50; i++) {
            if (counterLimiter.tryAcquire()) {
                count++;
            }
        }
        System.out.println("第二拨50次请求中通过：" + count + ",限流：" + (50 - count));
    }
}