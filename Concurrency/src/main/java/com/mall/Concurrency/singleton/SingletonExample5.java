package com.mall.Concurrency.singleton;

import com.mall.Concurrency.annoations.ThreadSafe;

/**
 * @Auther: xianzilei
 * @Date: 2019/7/6 08:57
 * @Description: 单例模式（懒汉式）：双重同步锁单例模式
 */
@ThreadSafe
public class SingletonExample5 {
    //单例对象，必须加volatile，否则会有指令重排问题
    private static volatile SingletonExample5 singletonExample5 = null;

    //私有构造函数
    private SingletonExample5() {
    }

    //静态的工厂方法
    public static SingletonExample5 getInstance() {
        //volatile+双重检测机制
        if (singletonExample5 == null) {
            synchronized (SingletonExample5.class) {
                if (singletonExample5 == null) {
                    singletonExample5 = new SingletonExample5();
                }
            }
        }
        return singletonExample5;
    }

    /**
     * 使用volatile关键字，禁止指令重排，从而保证线程安全
     **/
}
