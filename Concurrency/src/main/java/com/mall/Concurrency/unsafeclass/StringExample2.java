package com.mall.Concurrency.unsafeclass;

import com.mall.Concurrency.annoations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @Auther: xianzilei
 * @Date: 2019/7/10 18:56
 * @Description:
 */
@Slf4j
@ThreadSafe
public class StringExample2 {
    //请求总数
    public static int clientTotal = 10000;
    //同时并发的线程数
    public static int threadTotal = 10;

    public static StringBuffer stringBuffer = new StringBuffer();

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(threadTotal);
        CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        for (int i = 0; i < clientTotal; i++) {
            executorService.execute(() -> {
                try {
                    semaphore.acquire();
                    update();
                    semaphore.release();
                } catch (Exception e) {
                    log.error("exception:{}", e.getMessage(), e);
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        executorService.shutdown();
        log.info("size:{}", stringBuffer.length());
    }

    /**
     * @Description: 测试方法
     * @return: void
     * @auther: xianzilei
     * @date: 2019/6/19 8:13
     **/
    private static void update() {
        stringBuffer.append("1");
    }

    /**
     * StringBuffer是线程安全的类，但是性能较差
     **/
}
