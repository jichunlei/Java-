package com.mall.Concurrency.atomic;


import com.mall.Concurrency.pojo.Person;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicStampedReference;

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

    /**
     * @Description: AtomicIntegerFieldUpdater
     * @return: void
     * @auther: xianzilei
     * @date: 2019/6/26 7:57
     **/
    @Test
    public void test0201() {
        //创建属性原子更新器（Person对象中的name属性）
        //只能修改int类型的字段，对于包装类Integer不适用（包装类使用AtomicReferenceFieldUpdater）
        //AtomicLongFieldUpdater用法类似
        //用法要求：1、字段必须被volatile修饰。2、只能适用于实例变量，不能用于类变量，即字段不能被static关键字修饰
        //3、只能适用可修改的变量，即字段不能被final关键字修饰（final和volatile关键字不可同时存在）
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

    /**
     * @Description: AtomicStampedReference初始
     * @return: void
     * @auther: xianzilei
     * @date: 2019/6/26 8:08
     **/
    @Test
    public void test0301() {
        Person person1 = new Person();
        person1.setId(1);
        person1.setBirthDay(new Date());
        person1.setName("xianzilei1");
        Person person2 = new Person();
        person2.setId(2);
        person2.setBirthDay(new Date());
        person2.setName("xianzilei2");
        AtomicStampedReference<Person> atomicStampedReference = new AtomicStampedReference<>(person1, 1);
        //CAS算法：用原对象引用+版本号去比对内存中对应数据，如果都一样则更新成新值
        boolean b1 = atomicStampedReference.compareAndSet(person1, person2, 1, 2);
        log.info("更新：" + (b1 ? "成功" : "失败"));
        log.info("name:{}", atomicStampedReference.getReference().getName());
        log.info("stamp:{}", atomicStampedReference.getStamp());
        //更新版本号：用原对象引用和内存中数据比较，相同则更新成新版本号值返回true，否则不更新直接返回false
        boolean b2 = atomicStampedReference.attemptStamp(person2, 8);
        log.info("更新：" + (b2 ? "成功" : "失败"));
        log.info("name:{}", atomicStampedReference.getReference().getName());
        log.info("stamp:{}", atomicStampedReference.getStamp());

        boolean b3 = atomicStampedReference.compareAndSet(person1, person2, 1, 2);
        log.info("更新：" + (b3 ? "成功" : "失败"));
        log.info("name:{}", atomicStampedReference.getReference().getName());
        log.info("stamp:{}", atomicStampedReference.getStamp());
        //更新版本号：用原对象引用和内存中数据比较，相同则更新成新版本号值返回true，否则不更新直接返回false
        boolean b4 = atomicStampedReference.attemptStamp(person1, 8);
        log.info("更新：" + (b4 ? "成功" : "失败"));
        log.info("name:{}", atomicStampedReference.getReference().getName());
        log.info("stamp:{}", atomicStampedReference.getStamp());
    }

    /**
     * @Description: AtomicStampedReference解决ABA问题
     * @return: void
     * @auther: xianzilei
     * @date: 2019/6/27 8:34
     **/
    @Test
    public void test0302() {
        AtomicStampedReference<String> atomicStampedReference = new AtomicStampedReference<>("A", 0);
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                Integer stamp=atomicStampedReference.getStamp();
                try {
                    //保证执行A-B-A操作的线程先执行
                    //A-B-A执行完成后虽然又恢复成了A，但是版本号发生了变化，所以A-B操作一直无法执行
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (atomicStampedReference.compareAndSet("A", "B", stamp, stamp + 1)) {
                    log.info("线程{}执行了A-B操作-->reference:{},stamp:{}", Thread.currentThread(), atomicStampedReference.getReference(), atomicStampedReference.getStamp());
                }
            }).start();
            new Thread(() -> {
                atomicStampedReference.compareAndSet("A", "B", atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1);
                atomicStampedReference.compareAndSet("B", "A", atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1);
                log.info("线程{}执行了A-B-A操作-->reference:{},stamp:{}", Thread.currentThread(), atomicStampedReference.getReference(), atomicStampedReference.getStamp());
            }).start();

        }
    }

}
