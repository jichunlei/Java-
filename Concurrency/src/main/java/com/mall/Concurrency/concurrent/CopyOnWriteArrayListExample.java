package com.mall.Concurrency.concurrent;

import com.mall.Concurrency.annoations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.List;
import java.util.concurrent.*;

/**
 * @Auther: xianzilei
 * @Date: 2019/7/11 07:50
 * @Description: CopyOnWriteArrayList介绍
 */
@Slf4j
@ThreadSafe
public class CopyOnWriteArrayListExample {

    //请求总数
    public static int clientTotal = 10000;
    //同时并发的线程数
    public static int threadTotal = 100;

    private static List<Integer> list = new CopyOnWriteArrayList<>();

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
        log.info("size:{}", list.size());
    }

    /**
     * @Description: 通过反射获取list中array属性的地址
     * @return: int
     * @auther: xianzilei
     * @date: 2019/7/12 8:18
     **/
    private static int getAddress() {
        Class<? extends List> listClass = list.getClass();
        Field field = null;
        Object o = null;
        try {
            field = listClass.getDeclaredField("array");
            field.setAccessible(true);
            o = field.get(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return System.identityHashCode(o);
    }

    /**
     * @Description: 测试方法
     * @return: void
     * @auther: xianzilei
     * @date: 2019/6/19 8:13
     **/
    private static void test(int count) {
        list.add(count);
        //每次执行add操作后，打印出来的array的地址发生了变化
        //因为copy-on-write操作导致
        log.info("address-{}:{}", count, getAddress());
    }
}
