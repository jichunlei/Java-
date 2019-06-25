package com.mall.Concurrency.atomic;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Auther: xianzilei
 * @Date: 2019/6/24 18:38
 * @Description: Atomic包
 */
@Slf4j
public class example1 {

    private Integer count = 1;

    /**
     * @Description: AtomicInteger
     * @return: void
     * @auther: xianzilei
     * @date: 2019/6/25 8:53
     **/
    @Test
    public void test0101() {
        AtomicInteger atomicInteger = new AtomicInteger(1);
        ExecutorService executorService = Executors.newCachedThreadPool();
        List<Callable<Integer>> list = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            list.add(() -> {
                log.info(Thread.currentThread()+"--atomicInteger:{}", atomicInteger.incrementAndGet());
                return atomicInteger.get();
            });
        }
        try {
            executorService.invokeAll(list);
            log.info("atomicInteger:{}", atomicInteger.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
            executorService.shutdown();
        }
    }

    /**
     * @Description: 普通自增
     * @return: void
     * @auther: xianzilei
     * @date: 2019/6/25 8:54
     **/
    @Test
    public void test0102() {
        for (int i = 0; i < 1000; i++) {
            new Thread(() ->
                    count++
            ).start();
        }
        log.info("count:{}", count);

    }
}
