package com.mall.Concurrency.threadlocal;

import com.mall.Concurrency.pojo.Person;

/**
 * @Auther: xianzilei
 * @Date: 2019/7/9 18:50
 * @Description:
 */
public class ThreadLocalExample1 {
    private ThreadLocal<Person> threadLoca = new ThreadLocal<>();

    public ThreadLocal<Person> getThreadLoca() {
        return threadLoca;
    }

    public ThreadLocalExample1(Person person) {
        threadLoca.set(person);
    }

    public static void main(String[] args) {
        Person person = new Person();
        person.setName("xianzilei");
        ThreadLocalExample1 threadLocalExample1 = new ThreadLocalExample1(person);
        ThreadLocal<Person> threadLocal = threadLocalExample1.getThreadLoca();
        System.out.println("name:" + threadLocal.get());
        person.setName("zhangsan");
        System.out.println("name:" + threadLocal.get());
        threadLocal.remove();
        System.out.println("name:" + threadLocal.get());
    }
}
