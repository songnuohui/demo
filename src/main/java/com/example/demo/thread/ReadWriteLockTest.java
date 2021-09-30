package com.example.demo.thread;

import lombok.SneakyThrows;
import lombok.var;

import java.util.Arrays;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁 ReadWriteLock
 * 有两个方法：readLock() 读锁 ， writeLock() 写锁
 * 效果是：当读的时候，允许多个线程读，不允许写 ； 当写的时候，只要有一条线程在写的时候，其他的线程必须等待
 *
 * 1、可以提高读取效率：
 *
 * 2、只允许一个线程写入；
 *
 * 3、允许多个线程在没有写入时同时读取；
 *
 * 4、适合读多写少的场景。
 *
 * @author SongNuoHui
 * @date 2021/9/15 17:53
 */
public class ReadWriteLockTest {
    public static void main(String[] args) throws InterruptedException {
        int[] arr = {1, 2, 3, 4, 5};
        int[] arr2 = {5, 6, 7, 8, 9};
        System.arraycopy(arr, 1, arr2, 2, 2);
        for (int i : arr2) {
            System.out.println(i);
        }
        System.out.println("---------------------------------");

        ReadAndWrite rw = new ReadAndWrite();

        for (int i = 0; i < 5; i++) {
            int finalI = i;
            Thread thread = new Thread() {
                @Override
                public void run() {
                    System.out.println("写入锁");
                    rw.inc(finalI);
                }
            };
            thread.start();

            Thread thread1 = new Thread() {
                @Override
                public void run() {
                    System.out.println("读锁");
                    int[] ints = rw.get();
                    for (int i1 = 0; i1 < ints.length; i1++) {
                        System.out.println(ints[i1]);
                    }
                    System.out.println(">>>>>>");
                }
            };
            thread1.start();

            Thread.sleep(3000);
        }
    }
}

class ReadAndWrite {
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final Lock rLock = readWriteLock.readLock();
    private final Lock wLock = readWriteLock.writeLock();

    private int[] counts = new int[6];

    public void inc(int index) {
        wLock.lock(); // 加写锁
        try {
            counts[index] += 1;
        } finally {
            wLock.unlock(); // 释放写锁
        }
    }

    public int[] get() {
        rLock.lock(); // 加读锁
        try {
            return Arrays.copyOf(counts, counts.length);
        } finally {
            rLock.unlock(); // 释放读锁
        }
    }
}