package com.mall.Concurrency.lock;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Auther: xianzilei
 * @Date: 2019/7/2 18:33
 * @Description: lock学习
 */
@Slf4j
public class LockDemo {
    int count = 0;

    /**
     * @Description: lock()方法使用(ReentrantLock)
     * @return: void
     * @auther: xianzilei
     * @date: 2019/7/2 18:34
     **/
    @Test
    public void test0101() throws Exception {
        //ReentrantLock：唯一实现了Lock接口的类
        Lock lock = new ReentrantLock();
        long startTime = System.nanoTime();
        Thread[] ts = new Thread[1000];
        for (int i = 0; i < 1000; i++) {
            ts[i] = new Thread(() -> {
                lock.lock();
                try {
                    count++;
                } finally {
                    lock.unlock();
                }
            });
        }
        for (int i = 0; i < 1000; i++) {
            ts[i].start();
        }
        //保证主线程等待子线程执行完成后再执行
        for (int i = 0; i < 1000; i++) {
            ts[i].join();
        }
        long endTime = System.nanoTime();
        System.out.println("执行结果：" + count);
        System.out.println("执行时间：" + (endTime - startTime));
    }

    /**
     * @Description: synchronized与lock性能对比
     * @return: void
     * @auther: xianzilei
     * @date: 2019/7/2 18:34
     **/
    @Test
    public void test0102() throws Exception {
        long startTime = System.nanoTime();
        Thread[] ts = new Thread[1000];
        for (int i = 0; i < 1000; i++) {
            ts[i] = new Thread(() -> {
                synchronized (this) {
                    count++;
                }
            });
        }
        for (int i = 0; i < 1000; i++) {
            ts[i].start();
        }
        //保证主线程等待子线程执行完成后再执行
        for (int i = 0; i < 1000; i++) {
            ts[i].join();
        }
        long endTime = System.nanoTime();
        System.out.println("执行结果：" + count);
        System.out.println("执行时间：" + (endTime - startTime));
        /**执行结果表明（jdk1.8版本）：
         * synchronized与lock的效果差不多，甚至有些时候synchronized比lock性能更优化
         * 查阅资料得知，在jdk1.6之前，synchronized的性能远远不如lock，而jdk1.6之后，
         * synchronized在语义上进行了优化（适应自旋，锁消除，锁粗化，轻量级锁，偏向锁），
         * 所以在jdk1.6之后，二者的性能差不多，甚至在某些场景更推荐使用synchronized
         */
    }

    /**
     * @Description: tryLock()方法介绍(ReentrantLock)
     * @return: void
     * @auther: xianzilei
     * @date: 2019/7/3 8:27
     **/
    @Test
    public void test0201() {
        ReentrantLock lock = new ReentrantLock();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                if (lock.tryLock()) {
                    try {
                        log.info("线程[{}]获得了锁！", Thread.currentThread().getName());
                    } finally {
                        lock.unlock();
                        log.info("线程[{}]释放了锁！", Thread.currentThread().getName());
                    }
                } else {
                    log.info("线程[{}]获得锁失败！", Thread.currentThread().getName());
                }
            }).start();
        }
    }

    /**
     * @Description: tryLock(long time, TimeUnit unit)方法介绍(ReentrantLock)
     * @return: void
     * @auther: xianzilei
     * @date: 2019/7/3 8:27
     **/
    @Test
    public void test0202() {
        ReentrantLock lock = new ReentrantLock();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    //这个方法在拿不到锁时会等待一定的时间，在时间期限之内如果还拿不到锁，就返回false，同时可以响应中断
                    if (lock.tryLock(1, TimeUnit.MILLISECONDS)) {
                        try {
                            log.info("线程[{}]获得了锁！", Thread.currentThread().getName());
                        } finally {
                            lock.unlock();
                            log.info("线程[{}]释放了锁！", Thread.currentThread().getName());
                        }
                    } else {
                        log.info("线程[{}]获得锁失败！", Thread.currentThread().getName());
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    /**
     * @Description: lockInterruptibly()方法介绍（ReentrantLock）
     * @return: void
     * @auther: xianzilei
     * @date: 2019/7/3 8:43
     **/
    @Test
    public void test0301() throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        Thread[] ts = new Thread[10000];
        for (int i = 0; i < ts.length; i++) {
            ts[i] = new Thread(() -> {
                try {
                    lock.lockInterruptibly();
                    try {
                        count++;
                    } finally {
                        lock.unlock();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        for (int i = 0; i < ts.length; i++) {
            ts[i].start();
            ts[i].join();
        }
        log.info("执行结果：{}", count);
    }

    /**
     * @Description: ReadWriteLock接口--读操作(不互斥)
     * @return: void
     * @auther: xianzilei
     * @date: 2019/7/3 18:36
     **/
    @Test
    public void test0401() {
        ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                readWriteLock.readLock().lock();
                try {
                    log.info("线程[{}]进入读操作", Thread.currentThread().getName());
                } finally {
                    log.info("线程[{}]退出读操作", Thread.currentThread().getName());
                    readWriteLock.readLock().unlock();
                }
            }).start();
        }
    }

    /**
     * @Description: ReadWriteLock接口--写操作(互斥)
     * @return: void
     * @auther: xianzilei
     * @date: 2019/7/3 19:21
     **/
    @Test
    public void test0402(){
        ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                readWriteLock.writeLock().lock();
                try {
                    log.info("线程[{}]进入写操作", Thread.currentThread().getName());
                } finally {
                    log.info("线程[{}]退出写操作", Thread.currentThread().getName());
                    readWriteLock.writeLock().unlock();
                }
            }).start();
        }
    }
}
