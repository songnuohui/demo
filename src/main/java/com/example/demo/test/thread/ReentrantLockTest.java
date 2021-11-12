package com.example.demo.test.thread;

import lombok.var;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 可重入锁：
 * 实现wait和notify的功能
 * 使用Condition对象就可以实现
 *
 * @author SongNuoHui
 * @date 2021/9/15 17:41
 */
public class ReentrantLockTest {
    public static void main(String[] args) throws InterruptedException {
        ConcurrentHashMap<Object, Object> objectObjectConcurrentHashMap = new ConcurrentHashMap<>();
        TaskQueues q = new TaskQueues();
        ArrayList<Thread> ts = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Thread t = new Thread() {
                public void run() {
                    // 执行task:
                    while (true) {
                        String s = q.getTask();
                        System.out.println("execute task: " + s);
                        System.out.println("--------------------");
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
                    Thread.sleep(3 * 1000);
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

class TaskQueues {

    private final Lock lock = new ReentrantLock();
    /**
     * 使用Condition时，
     * 引用的Condition对象必须从Lock实例的newCondition()返回，
     * 这样才能获得一个绑定了Lock实例的Condition实例
     */
    private final Condition condition = lock.newCondition();
    private Queue<String> queue = new LinkedList<>();

    /**
     * Condition提供的await()、signal()、signalAll()原理和synchronized锁对象的wait()、notify()、notifyAll()是一致的，并且其行为也是一样的：
     * await()会释放当前锁，进入等待状态；
     * signal()会唤醒某个等待线程；
     * signalAll()会唤醒所有等待线程；
     * 唤醒线程从await()返回后需要重新获得锁。
     * 此外，和tryLock()类似，await()可以在等待指定时间后，如果还没有被其他线程通过signal()或signalAll()唤醒，可以自己醒来：
     */
    public void addTask(String s) {
        lock.lock();
        try {
            queue.add(s);
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public String getTask() {
        lock.lock();
        try {
            while (queue.isEmpty()) {
                condition.await();
            }
            return queue.remove();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();

        }
        return null;
    }
}