package com.mall.Concurrency.atomic;


import com.mall.Concurrency.pojo.Person;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * @Auther: xianzilei
 * @Date: 2019/6/24 18:38
 * @Description: Atomic包
 */
@Slf4j
public class example1 {

    private Integer count = 1;

    /**
     * @Description: AtomicInteger
     * @return: void
     * @auther: xianzilei
     * @date: 2019/6/25 8:53
     **/
    @Test
    public void test0101() {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        ExecutorService executorService = Executors.newCachedThreadPool();
        List<Callable<Integer>> list = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            list.add(() -> {
                log.info(Thread.currentThread() + "--atomicInteger:{}", atomicInteger.incrementAndGet());
                return atomicInteger.get();
            });
        }
        try {
            executorService.invokeAll(list);
            //不管运行多少次，都会返回预期值
            log.info("atomicInteger:{}", atomicInteger.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
            executorService.shutdown();
        }
    }

    /**
     * @Description: 普通自增
     * @return: void
     * @auther: xianzilei
     * @date: 2019/6/25 8:54
     **/
    @Test
    public void test0102() throws InterruptedException {
        for (int i = 0; i < 10000; i++) {
            new Thread(() ->
                    log.info(Thread.currentThread() + "--atomicInteger:{}", ++count)

            ).start();
        }
        //用来保证主线程最后执行
        Thread.sleep(5000);
        //基本返回不了预期值
        log.info("count:{}", count);

    }

    @Test
    public void test0201() {
        //创建属性原子更新器（Person对象中的name属性）
        AtomicIntegerFieldUpdater<Person> ageFieldUpdater = AtomicIntegerFieldUpdater.newUpdater(Person.class, "age");
        Person person = new Person();
        person.setId(0);
        ExecutorService executorService = Executors.newCachedThreadPool();
        List<Callable<Integer>> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(() ->
                    ageFieldUpdater.incrementAndGet(person)
            );
        }
        try {
            executorService.invokeAll(list);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
        log.info("name:{}", person.getAge());
    }


}
