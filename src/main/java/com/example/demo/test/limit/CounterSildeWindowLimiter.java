package com.example.demo.test.limit;

/**
 * 限流算法
 * 计数器滑动窗口算法：
 * 规则如下，假设时间窗口为 1 秒：
 * 记录每次请求的时间
 * 统计每次请求的时间 至 往前推1秒这个时间窗口内请求数，并且 1 秒前的数据可以删除。
 * 统计的请求数小于阈值就记录这个请求的时间，并允许通过，反之拒绝。
 *
 * @author SongNuoHui
 * @date 2021/9/30 13:54
 */
public class CounterSildeWindowLimiter {

    /**
     * 窗口大小，毫秒为单位
     **/
    private int windowSize;
    /**
     * 窗口内限流大小
     **/
    private int limit;
    /**
     * 切分小窗口的数目大小
     **/
    private int splitNum;
    /**
     * 每个小窗口的计数数组
     **/
    private int[] counters;
    /**
     * 当前小窗口计数器的索引
     **/
    private int index;
    /**
     * 窗口开始时间
     **/
    private long startTime;

    private CounterSildeWindowLimiter() {
    }

    public CounterSildeWindowLimiter(int windowSize, int limit, int splitNum) {
        this.limit = limit;
        this.windowSize = windowSize;
        this.splitNum = splitNum;
        counters = new int[splitNum];
        index = 0;
        startTime = System.currentTimeMillis();
    }


    //是否符合要求，请求到达后先调用本方法，若返回true，则请求通过，否则限流
    public synchronized boolean tryAcquire() {
        long curTime = System.currentTimeMillis();
        //计算每个滑动小窗口的数量
        long windowsNum = Math.max(curTime - windowSize - startTime, 0) / (windowSize / splitNum);
        //滑动窗口
        slideWindow(windowsNum);
        int count = 0;
        for (int i = 0; i < splitNum; i++) {
            count += counters[i];
        }
        if (count >= limit) {
            return false;
        } else {
            counters[index]++;
            return true;
        }
    }

    /**
     * 滑动窗口
     */
    private synchronized void slideWindow(long windowsNum) {
        if (windowsNum == 0)
            return;
        long slideNum = Math.min(windowsNum, splitNum);
        for (int i = 0; i < slideNum; i++) {
            index = (index + 1) % splitNum;
            counters[index] = 0;
        }
        startTime = startTime + windowsNum * (windowSize / splitNum);//更新滑动窗口时间
    }

    //测试
    public static void main(String[] args) throws InterruptedException {
        //每秒20个请求
        int limit = 20;
        CounterSildeWindowLimiter counterSildeWindowLimiter = new CounterSildeWindowLimiter(1000, limit, 10);
        int count = 0;

        Thread.sleep(3000);
        //计数器滑动窗口算法模拟100组间隔30ms的50次请求
        System.out.println("计数器滑动窗口算法测试开始");
        System.out.println("开始模拟100组间隔150ms的50次请求");
        int failCount = 0;
        for (int j = 0; j < 100; j++) {
            count = 0;
            for (int i = 0; i < 50; i++) {
                if (counterSildeWindowLimiter.tryAcquire()) {
                    count++;
                }
            }
            Thread.sleep(150);
            //模拟50次请求，看多少能通过
            for (int i = 0; i < 50; i++) {
                if (counterSildeWindowLimiter.tryAcquire()) {
                    count++;
                }
            }
            if (count > limit) {
                System.out.println("时间窗口内放过的请求超过阈值，放过的请求数" + count + ",限流：" + limit);
                failCount++;
            }
            Thread.sleep((int) (Math.random() * 100));
        }
        System.out.println("计数器滑动窗口算法测试结束，100组间隔150ms的50次请求模拟完成，限流失败组数：" + failCount);
        System.out.println("===========================================================================================");


        //计数器固定窗口算法模拟100组间隔30ms的50次请求
        System.out.println("计数器固定窗口算法测试开始");
        //模拟100组间隔30ms的50次请求
        CounterLimiter counterLimiter = new CounterLimiter(1000, limit);
        System.out.println("开始模拟100组间隔150ms的50次请求");
        failCount = 0;
        for (int j = 0; j < 100; j++) {
            count = 0;
            for (int i = 0; i < 50; i++) {
                if (counterLimiter.tryAcquire()) {
                    count++;
                }
            }
            Thread.sleep(150);
            //模拟50次请求，看多少能通过
            for (int i = 0; i < 50; i++) {
                if (counterLimiter.tryAcquire()) {
                    count++;
                }
            }
            if (count > limit) {
                System.out.println("时间窗口内放过的请求超过阈值，放过的请求数" + count + ",限流：" + limit);
                failCount++;
            }
            Thread.sleep((int) (Math.random() * 100));
        }
        System.out.println("计数器滑动窗口算法测试结束，100组间隔150ms的50次请求模拟完成，限流失败组数：" + failCount);
    }
}