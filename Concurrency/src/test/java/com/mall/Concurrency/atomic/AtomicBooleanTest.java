package com.mall.Concurrency.atomic;

import lombok.extern.slf4j.Slf4j;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @Auther: xianzilei
 * @Date: 2019/6/20 08:26
 * @Description:AtomicBoolean介绍
 */
@Slf4j
public class AtomicBooleanTest {
    //请求总数
    public static int clientTotal=10000;
    //同时并发的线程数
    public static int threadTotal=50;

    private static AtomicBoolean isHappened=new AtomicBoolean(false);

    public static void main(String[] args) throws Exception{
        ExecutorService executorService= Executors.newCachedThreadPool();
        final Semaphore semaphore=new Semaphore(threadTotal);
        CountDownLatch countDownLatch=new CountDownLatch(clientTotal);
        for (int i = 0; i < clientTotal; i++) {
            executorService.execute(()->{
                try {
                    semaphore.acquire();
                    test();
                    semaphore.release();
                } catch (Exception e) {
                    log.error("exception:{}",e.getMessage(),e);
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        executorService.shutdown();
        log.info("isHappened:{}",isHappened.get());
    }

    //可以用来控制某个方法只执行一次
    private static void test(){
        if (isHappened.compareAndSet(false,true)) {
            log.info("execute");
        }
    }
}
