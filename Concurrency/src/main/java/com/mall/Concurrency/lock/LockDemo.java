package com.mall.Concurrency.lock;

import org.junit.jupiter.api.Test;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Auther: xianzilei
 * @Date: 2019/7/2 18:33
 * @Description: lock学习
 */
public class LockDemo {
    int count = 0;

    /**
     * @Description: lock()方法使用
     * @return: void
     * @auther: xianzilei
     * @date: 2019/7/2 18:34
     **/
    @Test
    public void test0101() throws Exception {
        Lock lock = new ReentrantLock();
        long startTime = System.nanoTime();
        Thread[] ts = new Thread[10000];
        for (int i = 0; i < 10000; i++) {
            ts[i] = new Thread(() -> {
                lock.lock();
                try {
                    count++;
                } finally {
                    lock.unlock();
                }
            });
        }
        for (int i = 0; i < 10000; i++) {
            ts[i].start();
        }
        //主线程等待子线程执行完成后再执行
        for (int i = 0; i < 10000; i++) {
            ts[i].join();
        }
        long endTime = System.nanoTime();
        System.out.println("执行结果：" + count);
        System.out.println("执行时间：" + (endTime - startTime));
    }
}
