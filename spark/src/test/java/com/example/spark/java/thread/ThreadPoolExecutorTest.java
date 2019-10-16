package com.example.spark.java.thread;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author xYang
 * @date 2019/10/14 0014 17:07
 * @purchase //TODO 一句话说明
 */
public class ThreadPoolExecutorTest {
    @Test
    public void simpleThradPoolExecutorTest() {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 5; i++) {
            executorService.execute(() -> System.out.println(Thread.currentThread().getName()));
        }
    }
}
