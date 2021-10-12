package com.example.demo.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 线程池
 * Executors里面的静态方法，封装好了创建线程池的方法，主要是用到了Executor框架
 *
 * @author SongNuoHui
 * @date 2021/10/12 15:25
 */
public class ThreadPool {
    //线程数量
    private static final int ThreadsCount = 5;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("----------单任务线程池----------");
        createSingleThreadPool();

        Thread.sleep(1000);
        System.out.println("\n----------多任务线程池----------");
        createFixedThreadPoll();

        Thread.sleep(1000);
        System.out.println("\n----------可变大小线程池----------");
        createCachedThreadPool();

        Thread.sleep(1000);
        System.out.println("\n----------固定大小延迟线程池----------");
        createScheduledThreadPool();
    }

    /**
     * 创建一个单线程的线程池
     * SingleThreadExecutor保证了任务执行的顺序，不会存在多线程活动。
     */
    private static void createSingleThreadPool() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + "正在执行。。。");
                }
            });
            executorService.execute(thread);
        }
        executorService.shutdown();
    }

    /**
     * 创建一个规定大小的线程池:
     * 满足了资源管理的需求，可以限制当前线程数量。适用于负载较重的服务器环境。
     * newFixedThreadPool的参数指定了可以运行的线程的最大数目，
     * 超过这个数目的线程加进去以后，不会运行。
     * 其次，加入线程池的线程属于托管状态，线程的运行不受加入顺序的影响。
     **/
    private static void createFixedThreadPoll() {
        ExecutorService executorService = Executors.newFixedThreadPool(ThreadsCount);
        //执行线程数不会超过ThreadsCount
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + "正在执行。。。");
                }
            });
            executorService.execute(thread);
        }
        executorService.shutdown();
    }

    /**
     * 可变尺寸的线程池:适用于执行很多短期异步任务的小程序，适用于负载较轻的服务器
     * 可根据需要创建新线程的线程池，但是在以前构造的线程可用时将重用它们。
     */
    private static void createCachedThreadPool() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        //可以有10条线程执行
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + "正在执行。。。");
                }
            });
            executorService.execute(thread);
        }
        executorService.shutdown();
    }

    /**
     * 延迟连接池:它可以在给定的延迟时间后执行命令，或者定期执行命令，它比Timer更强大更灵活
     * <p>
     * ScheduledThreadPoolExecutor具有固定线程个数，适用于需要多个后台线程执行周期任务，
     * 并且为了满足资源管理需求而限制后台线程数量的场景。
     * <p>
     * 它适用于单个后台线程执行周期任务，并且保证顺序一致执行的场景。
     * <p>
     * 使用newScheduledThreadPool来模拟心跳机制
     */
    private static void createScheduledThreadPool() throws InterruptedException {
        //容量为5的延迟线程池
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(ThreadsCount);
        for (int i = 0; i < 3; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + "心跳，蹦蹦蹦！！！");
                }
            });
            //2秒后第一次执行，之后每隔1秒执行一次，每3条执行一次
            scheduledExecutorService.scheduleAtFixedRate(thread, 2, 1, TimeUnit.SECONDS);
        }
        Thread.sleep(10 * 1000);
        boolean shutdown = scheduledExecutorService.isShutdown();
        if (!shutdown) {
            scheduledExecutorService.shutdown();
        }
    }
}
