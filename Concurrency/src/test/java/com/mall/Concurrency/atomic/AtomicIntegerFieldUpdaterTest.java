package com.mall.Concurrency.atomic;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * @Auther: xianzilei
 * @Date: 2019/6/20 08:11
 * @Description:AtomicIntegerFieldUpdater介绍
 */
@Slf4j
public class AtomicIntegerFieldUpdaterTest {
    private static AtomicIntegerFieldUpdater<AtomicIntegerFieldUpdaterTest> updater
            =AtomicIntegerFieldUpdater.newUpdater(AtomicIntegerFieldUpdaterTest.class,"count");
    //属性必须用volatile关键字修饰，不可使用static修饰，且包含get方法
    @Getter
    private volatile int count=1000;

    public static void main(String[] args) {
        AtomicIntegerFieldUpdaterTest atomicIntegerFieldUpdaterTest = new AtomicIntegerFieldUpdaterTest();
        if (updater.compareAndSet(atomicIntegerFieldUpdaterTest,1000,2000)) {
            log.info("update successful! count:{}",atomicIntegerFieldUpdaterTest.getCount());
        }

        if (updater.compareAndSet(atomicIntegerFieldUpdaterTest, 3000, 4000)) {
            log.info("update successful! count:{}", atomicIntegerFieldUpdaterTest.getCount());
        } else {
            log.info("update failed! count:{}", atomicIntegerFieldUpdaterTest.getCount());
        }
    }
}
