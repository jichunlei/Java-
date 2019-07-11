package com.mall.Concurrency.unsafeclass;

import com.mall.Concurrency.annoations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @Auther: xianzilei
 * @Date: 2019/7/11 07:50
 * @Description:
 */
@Slf4j
@ThreadSafe
public class SimpleDateFormatExample2 {

    //请求总数
    public static int clientTotal = 10000;
    //同时并发的线程数
    public static int threadTotal = 100;


    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(threadTotal);
        CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        for (int i = 0; i < clientTotal; i++) {
            executorService.execute(() -> {
                try {
                    semaphore.acquire();
                    test();
                    semaphore.release();
                } catch (Exception e) {
                    log.error("exception:{}", e.getMessage(), e);
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        executorService.shutdown();
    }

    /**
     * @Description: 测试方法
     * @return: void
     * @auther: xianzilei
     * @date: 2019/6/19 8:13
     **/
    private static void test() {
        try {
            //每次new一个新的simpleDateFormat变量，利用线程封闭原则，
            // 使simpleDateFormat称为局部变量，从而避免线程不安全问题
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
            simpleDateFormat.parse("20190101");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
