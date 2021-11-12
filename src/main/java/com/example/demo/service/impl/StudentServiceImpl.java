package com.example.demo.service.impl;

import com.example.demo.manager.entity.Student;
import com.example.demo.manager.service.IStudentService;
import com.example.demo.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.*;

/**
 * @author SongNuoHui
 * @date 2021/10/15 11:16
 */
@Service
@Slf4j
public class StudentServiceImpl implements StudentService {

    @Autowired
    @Qualifier("IStudentServiceImpl")
    private IStudentService iStudentService;

    //定义线程
    private static final int CoreThreads = 10;
    private static final int MaxThreads = 20;
    private static final long KeepAliveTime = 60L;

    @Override
    public List<List<Student>> multiThreadQuery() {
        long from = System.currentTimeMillis();
        //创建线程池
        ExecutorService executorService = new ThreadPoolExecutor(
                CoreThreads,
                MaxThreads,
                KeepAliveTime,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(10));

        //统计总条数
        int count = iStudentService.count();
        log.info("count>>>{}", count);

        Map<String, String> splitMap = splitMap(count);

        // 封装Callable产生的结果
        List<Callable<List<Student>>> tasks = new ArrayList<>();
        for (int i = 0; i < CoreThreads; i++) {
            String[] split = splitMap.get(String.valueOf(i)).split(":");
            int index = Integer.parseInt(split[0]);
            int number = Integer.parseInt(split[1]);

            //多线程查询利用的是limit查询，当用mp分页插件的时候，index是页数，number是每页的数量，此时不能实现多线程查询
            //IPage<Student> page = iStudentService.page(new Page<>(index, number));
            List<Student> page = iStudentService.queryLimit(index, number);
            Callable<List<Student>> listCallable = new Callable<List<Student>>() {
                @Override
                public List<Student> call() throws Exception {
                    return page;
                }
            };
            tasks.add(listCallable);
        }
        List<List<Student>> list = new ArrayList<>();
        try {
            List<Future<List<Student>>> futures = executorService.invokeAll(tasks);
            if (futures.size() > 0) {
                // 迭代结果
                futures.forEach(it -> {
                    try {
                        list.addAll(Collections.singleton(it.get()));
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                });

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (executorService.isTerminated()) {
            long to = System.currentTimeMillis();
            log.info("多线程运行时间>>>" + (to - from));
        }
        return list;
    }

    @Override
    public List<Student> query() {
        return iStudentService.list();
    }

    @Override
    public List<List<Student>> multiQuery() {
        long from = System.currentTimeMillis();
        //创建线程池
        ExecutorService executorService = new ThreadPoolExecutor(
                CoreThreads,
                MaxThreads,
                KeepAliveTime,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(10));

        //统计总条数
        int count = iStudentService.count();
        log.info("count>>>{}", count);

        Map<String, String> splitMap = splitMap(count);
        List<Future<List<Student>>> list = new ArrayList<>();
        for (int i = 0; i < CoreThreads; i++) {
            String[] split = splitMap.get(String.valueOf(i)).split(":");
            int index = Integer.parseInt(split[0]);
            int number = Integer.parseInt(split[1]);

            List<Student> queryLimit = iStudentService.queryLimit(index, number);
            Callable<List<Student>> callable = new Callable<List<Student>>() {
                @Override
                public List<Student> call() throws Exception {
                    return queryLimit;
                }
            };
            // 执行任务并获取Future对象
            Future<List<Student>> future = executorService.submit(callable);
            list.add(future);
        }
        // 关闭线程池
        executorService.shutdown();

        // 获取所有并发任务的运行结果
        List<List<Student>> lists = new ArrayList<>();
        list.forEach(listFuture -> {
            try {
                List<Student> li = listFuture.get();
                lists.add(li);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });
        if (executorService.isTerminated()) {
            long to = System.currentTimeMillis();
            log.info("多线程运行时间>>>" + (to - from));
        }
        return lists;
    }

    @Override
    public Map<String, String> queryTime() throws InterruptedException {
        Map<String, String> map = new HashMap<>();
        long from = System.currentTimeMillis();
        multiThreadQuery();
        long to = System.currentTimeMillis();
        map.put("多线程1", to - from + "毫秒");

        Thread.sleep(5 * 1000);

        long from1 = System.currentTimeMillis();
        multiQuery();
        long to1 = System.currentTimeMillis();
        map.put("多线程2", to1 - from1 + "毫秒");

        Thread.sleep(5 * 1000);

        long from2 = System.currentTimeMillis();
        query();
        long to2 = System.currentTimeMillis();
        map.put("多线程3", to2 - from2 + "毫秒");

        return map;
    }

    @Override
    public void batchInsert() {
        long from = System.currentTimeMillis();
        List<Student> list = new ArrayList<>();
        int fromCount = 200000;
        int toCount = 400000;
        for (int i = fromCount; i < toCount; i++) {
            Student student = new Student();
            student.setSId("id" + i);
            student.setSUserName("userName" + i);
            student.setSName("name" + i);
            student.setSAddress("address" + i);
            student.setSPass("password" + i);
            student.setSSex("男");
            student.setSIdCard("idCard" + i);
            LocalDateTime now = LocalDateTime.now();
            ZonedDateTime zonedDateTime = now.atZone(ZoneId.systemDefault());
            Date date = Date.from(zonedDateTime.toInstant());
            student.setCreateTime(date);
            student.setUpdateTime(date);
            list.add(student);
        }
        iStudentService.saveOrUpdateBatch(list);
        long to = System.currentTimeMillis();
        log.info("单线程插入" + (fromCount - toCount) + "条数据所花费时间：：：" + (to - from));
    }

    @Override
    public void multiBatchInsert() {
        long from = System.currentTimeMillis();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                CoreThreads,
                MaxThreads,
                KeepAliveTime,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(CoreThreads));
        int records = 200000;
        Map<Integer, List<Integer>> insertMap = insertMap(CoreThreads, records);
        for (int i = 0; i < CoreThreads; i++) {
            List<Integer> list = insertMap.get(i);
            List<Student> studentList = buildInsert(list);

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    boolean b = iStudentService.saveOrUpdateBatch(studentList);
                    if (b) {
                        log.info(Thread.currentThread() + ">>>>>插入成功！");
                    }
                }
            });
            executor.execute(thread);
        }
        executor.shutdown();
        if (executor.isShutdown()) {
            long to = System.currentTimeMillis();
            log.info("多线程插入" + records + "条数据所花费时间：：：" + (to - from));
        }
    }

    @Override
    public int deleteAll(Date from, Date to) {
        return iStudentService.removeAll(from, to);
    }


    /**
     * 根据查询记录数和线程数量分配每条线程查询记录数
     *
     * @param count 总数
     * @return key:线程编号，value: 查询的起始-终止索引
     */
    private Map<String, String> splitMap(int count) {
        Map<String, String> splitMap = new HashMap<>(StudentServiceImpl.CoreThreads);

        // 每个线程分配的查询记录数
        int offsetNum = count / StudentServiceImpl.CoreThreads;
        int residue = count % StudentServiceImpl.CoreThreads;

        for (int i = 0; i < StudentServiceImpl.CoreThreads; i++) {
            if (i == 0) {
                splitMap.put(String.valueOf(i), i + ":" + offsetNum);
            } else if (i < CoreThreads - 1) {
                splitMap.put(String.valueOf(i), (i * offsetNum) + ":" + offsetNum);
            } else {
                splitMap.put(String.valueOf(i), (i * offsetNum) + ":" + (offsetNum + residue));
            }
        }
        return splitMap;
    }


    /**
     * 根据线程数和插入总条数分配线程执行数
     *
     * @param count   线程数
     * @param records 插入总记录数
     * @return map
     */
    private Map<Integer, List<Integer>> insertMap(int count, int records) {
        int singleCount = records / count;
        int lastCount = records % count;
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < count; i++) {
            int from = i * singleCount;
            int to = (i + 1) * singleCount;
            List<Integer> list = new ArrayList<>();
            if (i < count - 1) {
                for (int i1 = from; i1 < to; i1++) {
                    list.add(i1);
                }
            } else {
                to = to + lastCount;
                for (int i1 = from; i1 < to; i1++) {
                    list.add(i1);
                }
            }
            map.put(i, list);
        }
        return map;
    }

    List<Student> buildInsert(List<Integer> list) {
        List<Student> list1 = new ArrayList<>();
        for (Integer i : list) {
            Student student = new Student();
            student.setSId("id" + i);
            student.setSUserName("userName" + i);
            student.setSName("name" + i);
            student.setSAddress("address" + i);
            student.setSPass("password" + i);
            student.setSSex("男");
            student.setSIdCard("idCard" + i);
            LocalDateTime now = LocalDateTime.now();
            ZonedDateTime zonedDateTime = now.atZone(ZoneId.systemDefault());
            Date date = Date.from(zonedDateTime.toInstant());
            student.setCreateTime(date);
            student.setUpdateTime(date);
            list1.add(student);
        }
        return list1;
    }
}
