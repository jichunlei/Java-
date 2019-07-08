package com.mall.Concurrency.singleton;

import com.mall.Concurrency.annoations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

/**
 * @Auther: xianzilei
 * @Date: 2019/7/6 08:57
 * @Description: 单例模式（饿汉式）：单例实例在类装载的时候就进行创建（静态代码块初始化）
 */
@Slf4j
@ThreadSafe
public class SingletonExample6 {
    //单例对象
    private static SingletonExample6 singletonExample6 = null;

    static {
        singletonExample6 = new SingletonExample6();
    }
    //私有构造函数
    private SingletonExample6() {
    }

    //静态的工厂方法
    public static SingletonExample6 getInstance() {
        //因为在类装载的时候就已经创建了实例，即使多线程也不会有线程安全问题
        return singletonExample6;
    }

    public static void main(String[] args) {
        log.info("对象1{}", getInstance());
        log.info("对象2{}", getInstance());
    }

}
