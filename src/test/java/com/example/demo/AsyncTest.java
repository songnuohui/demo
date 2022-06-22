package com.example.demo;

import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author SongNuoHui
 * @date 2022/5/11 10:25
 */
public class AsyncTest {

  @Test
  public void async() throws InterruptedException {
    String name = Thread.currentThread().getName();
    System.out.println("主线程开始->" + name);
    ExecutorService executorService = Executors.newFixedThreadPool(4);
    executorService.submit(
        () -> {
          for (int i = 0; i < 100000; i++) {
            System.out.println("executorService打印" + i + Thread.currentThread().getName());
          }
        });

    CompletableFuture.runAsync(
        () -> {
          for (int i = 0; i < 100000; i++) {
            System.out.println("CompletableFuture打印" + i + Thread.currentThread().getName());
          }
        },executorService);

    for (int i = 100001; i < 100010; i++) {
      System.out.println("继续打印" + i);
    }

  }
}
