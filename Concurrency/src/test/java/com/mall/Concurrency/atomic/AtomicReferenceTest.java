package com.mall.Concurrency.atomic;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @Auther: xianzilei
 * @Date: 2019/6/20 08:03
 * @Description:AtomicReference介绍
 */
@Slf4j
public class AtomicReferenceTest {
    private static AtomicReference<Integer> count=new AtomicReference<>(0);

    public static void main(String[] args) {
        count.compareAndSet(0,1);
        count.compareAndSet(1,2);
        count.compareAndSet(3,4);
        count.compareAndSet(2,9);
        count.compareAndSet(6,8);
        log.info("count:{}",count.get());
    }
}
