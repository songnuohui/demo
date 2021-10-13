package com.example.demo.test.thread;

/**
 * 线程死锁
 *
 * @author SongNuoHui
 * @date 2021/9/15 15:38
 */
//Cou类
class Cou {
    private int value = 0;
    private int another = 0;
    private int count = 0;
    private static final Object lock1 = new Object();
    private static final Object lock2 = new Object();

    /*
    public void add(int n) {
    synchronized(this) {
    this.count=this.count+n;
    }
    }
    public void desc(int n) {
    synchronized(this) {
    this.count=this.count-n;
    }
    }
    }*/
//可重入锁
    public synchronized void add(int n) {
        if (n < 0) {
            desc(-n);
        } else {
            this.count = this.count + n;
        }
    }

    public synchronized void desc(int n) {
        if (n > 0) {
            this.count = this.count - n;
        } else {
            add(-n);
        }
    }

    //获取一个锁A后再获取另外一个锁B，修复死锁
    public void addValue(int n) {
        System.out.println("Thread-1: try to get lock1");
        synchronized (this.lock1) {
            System.out.println("Thread-1: lock1 got");
            this.value = this.value + n;
            System.out.println("Thread-1: try to get lock2");
            synchronized (this.lock2) {
                System.out.println("Thread-1: lock2 got");
                this.another = this.another + n;
            }
            System.out.println("Thread-1: lock2 released");
        }
        System.out.println("Thread-1: lock1 released");
    }

    //获取一个锁A后再获取另外一个锁B，修复死锁
    public void descValue(int n) {
        System.out.println("Thread-2: try to get lock1");
        synchronized (this.lock1) {
            System.out.println("Thread-2: lock1 got");
            this.value = this.value - n;
            System.out.println("Thread-2: try to get lock2");
            synchronized (this.lock2) {
                System.out.println("Thread-2: lock2 got");
                this.another = this.another - n;
            }
            System.out.println("Thread-2: lock2 released");
        }
        System.out.println("Thread-2: lock1 released");
    }

   /* //获取一个锁B后再获取另外一个锁A，与addValue()形成死锁

public  void descValue(int n) {
System.out.println("Thread-2: try to get lock2");
synchronized(this.lock2) {
System.out.println("Thread-2: lock2 got");
this.another=this.another-n;
System.out.println("Thread-2: try to get lock1");
synchronized(this.lock1) {
System.out.println("Thread-2: lock1 got");
this.value=this.value-n;
}
System.out.println("Thread-2: lock1 released");
}
System.out.println("Thread-2: lock2 released");
}
*/

    public int getCount() {
        return this.count;
    }

    public int getValue() {
        return this.value;
    }

    public int getAnother() {
        return this.another;
    }
}

//测试couter类，可重入锁，死锁
public class DeadLock {
    public static void main(String[] args) throws InterruptedException {
        Cou c1 = new Cou();
        Cou c2 = new Cou();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                c1.add(i);
            }
        });
        t1.start();
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                c1.desc(i);
            }
        });
        t2.start();
        Thread t3 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                c2.addValue(i);
            }
        });
        t3.start();
        Thread t4 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                c2.descValue(i);
            }
        });
        t4.start();
        t1.join();
        t2.join();
        t3.join();
        t4.join();
        System.out.println("c1.count= " + c1.getCount());
        System.out.println("c2.value= " + c2.getValue());
        System.out.println("c2.value= " + c2.getAnother());
    }
}
