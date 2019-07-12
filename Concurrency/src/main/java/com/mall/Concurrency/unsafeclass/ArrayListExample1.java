package com.mall.Concurrency.unsafeclass;

import com.mall.Concurrency.annoations.NotThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
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
@NotThreadSafe
public class ArrayListExample1 {

    //请求总数
    public static int clientTotal = 1000;
    //同时并发的线程数
    public static int threadTotal = 50;

    private static List<Integer> list = new ArrayList<>(1);


    public static void main(String[] args) throws Exception {
        log.info("final:{}", getAddress("elementData"));
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
        log.info("size:{}", list.size());
        log.info("final:{}", getAddress("elementData"));
    }

    /**
     * @Description: 测试方法
     * @return: void
     * @auther: xianzilei
     * @date: 2019/6/19 8:13
     **/
    private static void test(int count) {
        list.add(count);
    }

    /**
     * @Description: 通过反射获取list中属性的地址
     * @return: int
     * @auther: xianzilei
     * @date: 2019/7/12 8:18
     **/
    private static int getAddress(String fieldname) throws NoSuchFieldException, IllegalAccessException {
        Class<? extends List> listClass = list.getClass();
        Field field = listClass.getDeclaredField(fieldname);
        field.setAccessible(true);
        Object o = field.get(list);
        return System.identityHashCode(o);
    }
}
