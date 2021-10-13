package com.example.demo.test.thread;

import lombok.var;

/**
 * 线程同步
 *
 * @author SongNuoHui
 * @date 2021/9/15 14:45
 */
public class ThreadSync {
    public static void main(String[] args) throws InterruptedException {
        /** 未加锁操作，导致每次结果都不一样 */
        var add = new AddThread();
        var dec = new DecThread();
        add.start();
        dec.start();
        add.join();
        dec.join();
        System.out.println("1>>>" + Counter.count);

        /** synchronized加锁操作，不管多少次操作结果都是一样的 */
        var syncAddThread = new SyncAddThread();
        var syncDecThread = new SyncDecThread();
        syncAddThread.start();
        syncDecThread.start();
        syncAddThread.join();
        syncDecThread.join();
        System.out.println("2>>>" + SyncCounter.count);

        /** 使用synchronized的时候，获取到的是哪个锁非常重要。锁对象如果不对，代码逻辑就不对。*/
        var ts = new Thread[]{new AddStudentThread(), new DecStudentThread(), new AddTeacherThread(), new DecTeacherThread()};
        for (var t : ts) {
            t.start();
        }
        for (var t : ts) {
            t.join();
        }
        System.out.println("3>>>" + SCounter.studentCount);
        System.out.println("4>>>" + SCounter.teacherCount);
    }
}

class Counter {
    public static int count = 0;
}

/**
 * 每次运行的结果都不一样
 * 必须要保证原子操作，原子操作是指不能被中断的一个或一系列操作
 */
class AddThread extends Thread {
    public void run() {
        for (int i = 0; i < 10000; i++) {
            Counter.count += 1;
        }
    }
}

class DecThread extends Thread {
    public void run() {
        for (int i = 0; i < 10000; i++) {
            Counter.count -= 1;
        }
    }
}


/***************************************************************/

class SyncCounter {
    //public static final Object lock = new Object();
    public static int count = 0;
}

class SyncAddThread extends Thread {
    public void run() {
        for (int i = 0; i < 10000; i++) {
            synchronized (SyncCounter.class) {
                SyncCounter.count += 1;
            }
        }
    }
}

class SyncDecThread extends Thread {
    public void run() {
        for (int i = 0; i < 10000; i++) {
            synchronized (SyncCounter.class) {
                SyncCounter.count -= 1;
            }
        }
    }
}

/***************************************************************/
class SCounter {
    public static final Object lockStudent = new Object();
    public static final Object lockTeacher = new Object();
    public static int studentCount = 0;
    public static int teacherCount = 0;
}

class AddStudentThread extends Thread {
    public void run() {
        for (int i = 0; i < 10000; i++) {
            synchronized (SCounter.lockStudent) {
                SCounter.studentCount += 1;
            }
        }
    }
}

class DecStudentThread extends Thread {
    public void run() {
        for (int i = 0; i < 10000; i++) {
            synchronized (SCounter.lockStudent) {
                SCounter.studentCount -= 1;
            }
        }
    }
}

class AddTeacherThread extends Thread {
    public void run() {
        for (int i = 0; i < 10000; i++) {
            synchronized (SCounter.lockTeacher) {
                SCounter.teacherCount += 1;
            }
        }
    }
}

class DecTeacherThread extends Thread {
    public void run() {
        for (int i = 0; i < 10000; i++) {
            synchronized (SCounter.lockTeacher) {
                SCounter.teacherCount -= 1;
            }
        }
    }
}