package com.mall.Concurrency.atomic;

import com.mall.Concurrency.annoations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Auther: xianzilei
 * @Date: 2019/6/19 07:50
 * @Description:AtomicLong用法
 */
@Slf4j
@ThreadSafe
public class AtomicLongTest {

    //请求总数
    public static int clientTotal=10000;
    //同时并发的线程数
    public static int threadTotal=50;

    public static AtomicLong count=new AtomicLong();

    public static void main(String[] args) throws Exception {
        ExecutorService executorService= Executors.newCachedThreadPool();
        final Semaphore semaphore=new Semaphore(threadTotal);
        CountDownLatch countDownLatch=new CountDownLatch(clientTotal);
        for (int i = 0; i < clientTotal; i++) {
            executorService.execute(()->{
                try {
                    semaphore.acquire();
                    add();
                    semaphore.release();
                } catch (Exception e) {
                    log.error("exception:{}",e.getMessage(),e);
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        executorService.shutdown();
        log.info("count:{}",count.get());
    }

    /**
     * @Description: 测试方法
     * @return: void
     * @auther: xianzilei
     * @date: 2019/6/19 8:13
     **/
    private static void add(){
        //先加后获得值，相当于++count
        count.incrementAndGet();
        //先获得值后加，相当于count++
        //count.getAndIncrement();
    }

}
