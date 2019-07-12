package com.mall.Concurrency.concurrent;

import com.mall.Concurrency.annoations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;
import java.util.concurrent.*;

/**
 * @Auther: xianzilei
 * @Date: 2019/7/11 07:50
 * @Description: CopyOnWriteArraySet介绍
 */
@Slf4j
@ThreadSafe
public class CopyOnWriteArraySetExample {
    //请求总数
    public static int clientTotal = 10000;
    //同时并发的线程数
    public static int threadTotal = 100;

    private static Set<Integer> set = new CopyOnWriteArraySet<>();

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(threadTotal);
        CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        for (int i = 0; i < clientTotal; i++) {
            final int count = i;
            executorService.execute(() -> {
                try {
                    semaphore.acquire();
                    test(count);
                    semaphore.release();
                } catch (Exception e) {
                    log.error("exception:{}", e.getMessage(), e);
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        executorService.shutdown();
        //结果永远正确
        log.info("size:{}", set.size());
    }

    /**
     * @Description: 测试方法
     * @return: void
     * @auther: xianzilei
     * @date: 2019/6/19 8:13
     **/
    private static void test(int count) {
        //底层使用了CopyOnWriteArrayList，原理与CopyOnWriteArrayList相同
        set.add(count);
    }
}
