package com.mall.Concurrency.sync;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Auther: xianzilei
 * @Date: 2019/6/21 07:58
 * @Description: synchronized使用
 */
@Slf4j
public class SynchronizedDemo1 {

    /**
     * @Description: 修饰一个代码块：使用大括号括起来，作用于调用的对象
     * @return: void
     * @auther: xianzilei
     * @date: 2019/6/21 8:01
     **/
    public void method1() {
        //被修饰的代码称为同步语句块
        synchronized (this) {
            for (int i = 0; i < 5; i++) {
                //log.info("test1-{}", i);
                System.out.println(Thread.currentThread().getName() + "======i=====" + i);
            }
        }
        //未被synchronized修饰的代码块
        for (int j = 0; j < 5; j++) {
            System.out.println(Thread.currentThread().getName() + "======j=====" + j);
        }
    }

    /**
     * @Description: 修饰一个方法：作用于当前调用的对象，对不同的对象不影响
     * @return: void
     * @auther: xianzilei
     * @date: 2019/6/21 8:00
     **/
    //被修饰的方法称为同步方法
    public synchronized void method2() {
        for (int i = 0; i < 5; i++) {
            System.out.println(Thread.currentThread().getName() + "======i=====" + i);
        }
    }

    /**
     * @Description: 未被synchronized修饰的方法
     * @return: void
     * @auther: xianzilei
     * @date: 2019/6/22 8:31
     **/
    public void method3() {
        for (int i = 0; i < 5; i++) {
            System.out.println(Thread.currentThread().getName() + "======i=====" + i);
        }
    }

    /**
     * @Description: synchronized修饰一个类
     * @return: void
     * @auther: xianzilei
     * @date: 2019/6/22 8:54
     **/
    public static void method4() {
        //修饰类
        synchronized (SynchronizedDemo1.class) {
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName() + "======i=====" + i);
            }
        }
    }

    /**
     * @Description: synchronized修饰静态方法
     * @return: void
     * @auther: xianzilei
     * @date: 2019/6/22 8:57
     **/
    //修饰静态方法
    public static synchronized void method5() {
        for (int i = 0; i < 5; i++) {
            System.out.println(Thread.currentThread().getName() + "======i=====" + i);
        }
    }


    /**======================分============割===============线=======================*/
    /**======================分============割===============线=======================*/

    /**
     * @Description: 测试synchronized修饰代码块（同一个对象）
     * @return: void
     * @auther: xianzilei
     * @date: 2019/6/22 8:26
     **/
    @Test
    public void test1() {
        SynchronizedDemo1 synchronizedDemo1 = new SynchronizedDemo1();
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 3; i++) {
            executorService.execute(() -> {
                synchronizedDemo1.method1();
            });
        }
        executorService.shutdown();
    }

    /**
     * @Description: 测试synchronized修饰代码块（不同对象）--互不影响
     * @return: void
     * @auther: xianzilei
     * @date: 2019/6/22 8:26
     **/
    @Test
    public void test2() {
        SynchronizedDemo1 synchronizedDemo1 = new SynchronizedDemo1();
        SynchronizedDemo1 synchronizedDemo2 = new SynchronizedDemo1();
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(() -> {
            synchronizedDemo1.method1();
        });
        executorService.execute(() -> {
            synchronizedDemo2.method1();
        });
        executorService.shutdown();
    }

    /**
     * @Description: 测试synchronized修饰方法
     * @return: void
     * @auther: xianzilei
     * @date: 2019/6/22 8:32
     **/
    @Test
    public void test3() {
        SynchronizedDemo1 synchronizedDemo1 = new SynchronizedDemo1();
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 3; i++) {
            executorService.execute(() -> {
                synchronizedDemo1.method2();
            });
        }
        executorService.shutdown();
    }

    /**
     * @Description: 测试synchronized修饰类
     * @return: void
     * @auther: xianzilei
     * @date: 2019/6/22 9:00
     **/
    @Test
    public void test4() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 4; i++) {
            SynchronizedDemo1 synchronizedDemo1 = new SynchronizedDemo1();
            executorService.execute(() -> {
                synchronizedDemo1.method4();
            });
        }
        executorService.shutdown();
    }
}
