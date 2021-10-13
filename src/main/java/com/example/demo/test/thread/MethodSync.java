package com.example.demo.test.thread;

/**
 * @author SongNuoHui
 * @date 2021/9/15 15:20
 */
public class MethodSync {
    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            Count c1 = new Count();
            Count c2 = new Count();
            Thread t1 = new Thread(() -> c1.add(100));
            t1.start();
            Thread t2 = new Thread(() -> c1.dec(100));
            t2.start();

            Thread t3 = new Thread(() -> c2.add(100));
            t3.start();
            Thread t4 = new Thread(() -> c2.dec(100));
            t4.start();
            t1.join();
            t2.join();
            t3.join();
            t4.join();

            System.out.println("c1.count = " + c1.get());
            System.out.println("c2.count = " + c2.get());
            System.out.println("-----------------------");
        }

    }
}

class Count {
    private int count = 0;

    public synchronized void add(int n) {
        count += n;
    }

    /**
     * 上面的等同于下面的
     * 因此，用synchronized修饰的方法就是同步方法，它表示整个方法都必须用this实例加锁。
     * 对于static方法，是没有this实例的，因为static方法是针对类而不是实例。
     * 但是我们注意到任何一个类都有一个由JVM自动创建的Class实例，
     * 因此，对static方法添加synchronized，锁住的是该类的Class实例。
     */
    /*
    public void add(int n) {
        synchronized(this) { // 锁住this
            count += n;
        } // 解锁
    }
    */


    public synchronized void dec(int n) {
        count -= n;
    }

    public int get() {
        return count;
    }
}