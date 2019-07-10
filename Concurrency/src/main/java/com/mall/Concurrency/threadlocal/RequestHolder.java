package com.mall.Concurrency.threadlocal;

/**
 * @Auther: xianzilei
 * @Date: 2019/7/10 08:29
 * @Description:
 */
public class RequestHolder {
    private final static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void add(Long id) {
        threadLocal.set(id);
    }

    public static Long getId() {
        return threadLocal.get();
    }

    public static void remove() {
        threadLocal.remove();
    }
}
