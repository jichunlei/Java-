package com.mall.Concurrency.singleton;

import com.mall.Concurrency.annoations.NotRecommend;
import com.mall.Concurrency.annoations.ThreadSafe;

/**
 * @Auther: xianzilei
 * @Date: 2019/7/6 08:57
 * @Description: 单例模式（懒汉式）：线程安全解决方法其一-->使用synchronized
 */
@ThreadSafe
@NotRecommend
public class SingletonExample3 {
    //单例对象
    private static SingletonExample3 singletonExample3 = null;

    //私有构造函数
    private SingletonExample3() {
    }

    //静态的工厂方法
    public static synchronized SingletonExample3 getInstance() {
        //synchronized保证获取实例时线程互斥，从而保证线程安全，但是会带来性能问题，不推荐
        if (singletonExample3 == null) {
            singletonExample3 = new SingletonExample3();
        }
        return singletonExample3;
    }
}
