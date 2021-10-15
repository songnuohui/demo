package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.manager.entity.Student;
import com.example.demo.manager.service.IStudentService;
import com.example.demo.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private static final int CoreThreads = 5;
    private static final int MaxThreads = 10;
    private static final long KeepAliveTime = 30L;

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
        for (int i = 1; i <= CoreThreads; i++) {
            String[] split = splitMap.get(String.valueOf(i)).split(":");
            int index = Integer.parseInt(split[0]);
            int number = Integer.parseInt(split[1]);

            IPage<Student> page = iStudentService.page(new Page<>(index, number));

            Callable<List<Student>> listCallable = new Callable<List<Student>>() {
                @Override
                public List<Student> call() throws Exception {
                    return page.getRecords();
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
                        list.add(it.get());
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                });

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long to = System.currentTimeMillis();
        log.info("多线程运行时间>>>" + (to - from));
        return list;
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

        for (int i = 1; i <= StudentServiceImpl.CoreThreads; i++) {
            if (i == 1) {
                splitMap.put(String.valueOf(i), 0 + ":" + offsetNum);
            } else if (i < StudentServiceImpl.CoreThreads) {
                splitMap.put(String.valueOf(i), (i - 1) * offsetNum + ":" + offsetNum);
            } else {
                //最后一条线程处理（平均的数量+余数数量）
                splitMap.put(String.valueOf(i), (i - 1) * offsetNum + ":" + offsetNum + residue);
            }
        }
        return splitMap;
    }
}
