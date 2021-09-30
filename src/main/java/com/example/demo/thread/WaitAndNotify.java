package com.example.demo.thread;

import lombok.var;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * wait():线程进入等待状态
 * notify():让等待的线程被重新唤醒
 *
 * @author SongNuoHui
 * @date 2021/9/15 17:01
 */
public class WaitAndNotify {
    public static void main(String[] args) throws InterruptedException {
        TaskQueue q = new TaskQueue();
        ArrayList<Thread> ts = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Thread t = new Thread() {
                public void run() {
                    // 执行task:
                    while (true) {
                        try {
                            String s = q.getTask();
                            System.out.println("execute task: " + s);
                            System.out.println("--------------------");
                        } catch (InterruptedException e) {
                            return;
                        }
                    }
                }
            };
            t.start();
            ts.add(t);
        }
        var add = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                // 放入task:
                String s = "t-" + Math.random();
                System.out.println("add task: " + s);
                q.addTask(s);
                try {
                    Thread.sleep(3*1000);
                } catch (InterruptedException e) {
                }
            }
        });
        add.start();
        add.join();
        Thread.sleep(1000);
        for (var t : ts) {
            t.interrupt();
        }
    }
}

class TaskQueue {

    Queue<String> queue = new LinkedList<>();

    public synchronized void addTask(String s) {
        this.queue.add(s);
        this.notifyAll();
    }

    public synchronized String getTask() throws InterruptedException {
        while (queue.isEmpty()) {
            this.wait();
        }
        return queue.remove();
    }
}
