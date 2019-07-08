package com.mall.Concurrency.singleton;

import com.mall.Concurrency.annoations.NotThreadSafe;

/**
 * @Auther: xianzilei
 * @Date: 2019/7/6 08:57
 * @Description: 单例模式（懒汉式）：双重同步锁单例模式
 */
@NotThreadSafe
public class SingletonExample4 {
    //单例对象
    private static SingletonExample4 singletonExample4 = null;

    //私有构造函数
    private SingletonExample4() {
    }

    //静态的工厂方法
    public static SingletonExample4 getInstance() {
        //双重检测机制(但是并不是线程安全的)
        if (singletonExample4 == null) {
            synchronized (SingletonExample4.class) {
                if (singletonExample4 == null) {
                    singletonExample4 = new SingletonExample4();
                }
            }
        }
        return singletonExample4;
    }

    /**
     * 分析该类为什么是线程不安全的
     * 一、new SingletonExample4()解析
     *  a、memory=allocate()：分配对象的内存空间
     *  b、ctorInstance()：初始化对象
     *  c、instance=memory：设置instance指向刚分配的内存
     * 二、JVM和CPU优化，发生了指令重排，其中只有b和c步骤可以重排，因为b、c步骤不相关
     *  重排后的执行顺序为：a->c->b
     *  a、memory=allocate()：分配对象的内存空间
     *  c、instance=memory：设置instance指向刚分配的内存
     *  b、ctorInstance()：初始化对象
     * 三、结果可想而知：对象的初始化还未完成就返回
     **/
}
