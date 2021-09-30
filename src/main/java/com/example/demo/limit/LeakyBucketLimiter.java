package com.example.demo.limit;

import java.util.Date;
import java.util.LinkedList;

/**
 * 限流算法
 * 漏斗算法：
 * 请求来了放入桶中
 * 桶内请求量满了拒绝请求
 * 服务定速从桶内拿请求处理
 *
 * @author SongNuoHui
 * @date 2021/9/30 14:36
 */
public class LeakyBucketLimiter {

    /**
     * 漏斗容量
     **/
    private int capaticy;
    /**
     * 漏斗速率
     **/
    private int rate;
    /**
     * 剩余容量
     **/
    private int left;
    /**
     * 请求
     **/
    private LinkedList<Request> requestList;

    private LeakyBucketLimiter() {
    }

    public LeakyBucketLimiter(int capaticy, int rate) {
        this.capaticy = capaticy;
        this.rate = rate;
        this.left = capaticy;
        requestList = new LinkedList<>();

        //开启一个定时线程，以固定的速率将漏斗中的请求流出，进行处理
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (!requestList.isEmpty()) {
                        Request request = requestList.removeFirst();
                        handleRequest(request);
                    }
                    try {
                        Thread.sleep(1000 / rate); //睡眠
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    /**
     * 处理请求
     */
    private void handleRequest(Request request) {
        request.setHandleTime(new Date());
        System.out.println(request.getCode() + "号请求被处理，请求发起时间："
                + request.getLaunchTime() + ",请求处理时间：" + request.getHandleTime() + ",处理耗时："
                + (request.getHandleTime().getTime() - request.getLaunchTime().getTime()) + "ms");
    }

    public synchronized boolean tryAcquire(Request request) {
        if (left <= 0) {
            return false;
        } else {
            left--;
            requestList.addLast(request);
            return true;
        }
    }


    /**
     * 请求类，属性包含编号字符串、请求达到时间和请求处理时间
     */
    static class Request {
        private int code;
        private Date launchTime;
        private Date handleTime;

        private Request() {
        }

        public Request(int code, Date launchTime) {
            this.launchTime = launchTime;
            this.code = code;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public Date getLaunchTime() {
            return launchTime;
        }

        public void setLaunchTime(Date launchTime) {
            this.launchTime = launchTime;
        }

        public Date getHandleTime() {
            return handleTime;
        }

        public void setHandleTime(Date handleTime) {
            this.handleTime = handleTime;
        }
    }

    public static void main(String[] args) {
        LeakyBucketLimiter leakyBucketLimiter = new LeakyBucketLimiter(5, 2);
        for (int i = 1; i <= 10; i++) {
            Request request = new Request(i, new Date());
            if (leakyBucketLimiter.tryAcquire(request)) {
                System.out.println(i + "号请求被接受");
            } else {
                System.out.println(i + "号请求被拒绝");
            }
        }
    }
}