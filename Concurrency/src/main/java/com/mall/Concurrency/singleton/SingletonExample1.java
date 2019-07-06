package com.mall.Concurrency.singleton;

import com.mall.Concurrency.annoations.NotThreadSafe;

/**
 * @Auther: xianzilei
 * @Date: 2019/7/6 08:57
 * @Description: 单例模式（懒汉式）：单例实例在第一次使用的时候才进行创建
 */
@NotThreadSafe
public class SingletonExample1 {
    //单例对象
    private static SingletonExample1 singletonExample1 = null;

    //私有构造函数
    private SingletonExample1() {
    }

    //静态的工厂方法
    public static SingletonExample1 getInstance() {
        //在单个线程该写法是线程安全的，但是当多线程时，由于下面的代码不是同步代码，
        //所以可能new多次实例，导致可能的不安全问题
        if (singletonExample1 == null) {
            singletonExample1 = new SingletonExample1();
        }
        return singletonExample1;
    }
}
