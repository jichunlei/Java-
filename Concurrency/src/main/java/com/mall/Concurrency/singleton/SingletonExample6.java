package com.mall.Concurrency.singleton;

import com.mall.Concurrency.annoations.ThreadSafe;

/**
 * @Auther: xianzilei
 * @Date: 2019/7/6 08:57
 * @Description: 单例模式（饿汉式）：单例实例在类装载的时候就进行创建（）
 */
@ThreadSafe
public class SingletonExample6 {
    //单例对象
    private static SingletonExample6 singletonExample6 = new SingletonExample6();

    //私有构造函数
    private SingletonExample6() {
    }

    //静态的工厂方法
    public static SingletonExample6 getInstance() {
        //因为在类装载的时候就已经创建了实例，即使多线程也不会有线程安全问题
        return singletonExample6;
    }
    /**
     * 饿汉式虽然可以解决线程安全问题，但是如果单例对象过多或构造函数操作过多会导致类加载时的负担变重，可能会带来一些线程安全问题
     *
     * 懒汉式广泛用于单线程情况下，多线程下使用可能会导致线程不安全问题
     **/
}
