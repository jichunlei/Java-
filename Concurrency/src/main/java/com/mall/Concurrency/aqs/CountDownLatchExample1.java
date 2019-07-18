package com.mall.Concurrency.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Auther: xianzilei
 * @Date: 2019/7/18 08:47
 * @Description: CountDownLatch
 */
@Slf4j
public class CountDownLatchExample1 {

    private final static int threadNum = 200;

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newCachedThreadPool();
        final CountDownLatch countDownLatch = new CountDownLatch(threadNum);
        for (int i = 0; i < threadNum; i++) {
            final int threadNum = i;
            executorService.execute(() -> {
                try {
                    test(threadNum);
                } catch (Exception e) {
                    log.info("exception", e);
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        //保证前面线程执行完成后才执行下去
        countDownLatch.await();
        log.info("finish");
    }

    public static void test(int threadNum) throws Exception {
        Thread.sleep(100);
        log.info("count:{}", threadNum);
        Thread.sleep(100);
    }
}
