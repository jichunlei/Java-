package com.mall.Concurrency.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Auther: xianzilei
 * @Date: 2019/7/18 08:47
 * @Description: CountDownLatch的实际用例：开会用例
 */
@Slf4j
public class CountDownLatchExample3 {

    private final static int threadNum = 20;
    private final static CountDownLatch countDownLatch = new CountDownLatch(threadNum);

    public static void main(String[] args) throws Exception {
        log.info("老板在组织开会，一共有{}名员工参加会议。", threadNum);
        //员工入场
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < threadNum; i++) {
            executorService.execute(() ->
                    employee());
        }
        //老板等待员工入场
        countDownLatch.await();
        log.info("人到齐了，开始开会吧。");
        executorService.shutdown();
    }

    /**
     * @Description: 员工入场
     * @return: void
     * @auther: xianzilei
     * @date: 2019/7/19 8:32
     **/
    private static void employee() {
        log.info("员工[{}]到达会场。", Thread.currentThread().getName());
        countDownLatch.countDown();

    }
}
