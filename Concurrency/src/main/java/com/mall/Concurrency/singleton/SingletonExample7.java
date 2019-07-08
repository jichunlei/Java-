package com.mall.Concurrency.singleton;

import com.mall.Concurrency.annoations.Recommend;
import com.mall.Concurrency.annoations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

/**
 * @Auther: xianzilei
 * @Date: 2019/7/6 08:57
 * @Description: 枚举模式：最安全
 */
@Slf4j
@Recommend
@ThreadSafe
public class SingletonExample7 {

    //私有构造函数
    private SingletonExample7() {
    }

    public static SingletonExample7 getInstance() {
        return Singleton.SINGLETON.getInstance();
    }

    private enum Singleton {
        SINGLETON;
        private SingletonExample7 singleton;

        //JVM保证这个方法只调用一次
        Singleton() {
            singleton = new SingletonExample7();
        }

        private SingletonExample7 getInstance() {
            return singleton;
        }
    }

    public static void main(String[] args) {
        log.info("对象1{}", getInstance());
        log.info("对象2{}", getInstance());
    }
    /**
     * 枚举模式：均具备单例模式的懒汉式和饿汉式的优点
     *          使用枚举保证线程安全，同时只在调用时才会实例化对象，不会造成性能问题，所以最推荐的写法
     *
     *
     *
     **/
}
