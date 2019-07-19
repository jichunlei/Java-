package com.mall.Concurrency.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @Auther: xianzilei
 * @Date: 2019/7/18 08:47
 * @Description: Semaphore介绍--每次获取多个许可
 */
@Slf4j
public class SemaphoreExample2 {

    private final static int threadNum = 30;
    private final static int currentThreadNum = 3;

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(currentThreadNum);
        for (int i = 0; i < threadNum; i++) {
            final int threadNum = i;
            executorService.execute(() -> {
                try {
                    //获取多个许可（当获取许可个数=currentThreadNum，类似单线程执行）
                    semaphore.acquire(currentThreadNum);
                    test(threadNum);
                    //释放多个许可
                    semaphore.release(currentThreadNum);
                } catch (Exception e) {
                    log.info("exception", e);
                }
            });
        }
        executorService.shutdown();
    }

    public static void test(int threadNum) throws Exception {
        log.info("count:{}", threadNum);
        Thread.sleep(1000);
    }
}
