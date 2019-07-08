package com.mall.Concurrency.singleton;

import com.mall.Concurrency.annoations.ThreadSafe;

/**
 * @Auther: xianzilei
 * @Date: 2019/7/6 08:57
 * @Description: 单例模式（饿汉式）：单例实例在类装载的时候就进行创建（静态域初始化）
 */
@ThreadSafe
public class SingletonExample2 {
    //单例对象（静态域初始化）
    private static SingletonExample2 singletonExample2 = new SingletonExample2();

    //私有构造函数
    private SingletonExample2() {
    }

    //静态的工厂方法
    public static SingletonExample2 getInstance() {
        //因为在类装载的时候就已经创建了实例，即使多线程也不会有线程安全问题
        return singletonExample2;
    }
    /**
     * 饿汉式虽然可以解决线程安全问题，但是如果单例对象过多或构造函数操作过多会导致类加载时的负担变重，可能会带来一些线程安全问题
     *
     * 懒汉式广泛用于单线程情况下，多线程下使用可能会导致线程不安全问题
     **/
}
