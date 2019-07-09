package com.mall.Concurrency.immutable;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mall.Concurrency.annoations.ThreadSafe;

/**
 * @Auther: xianzilei
 * @Date: 2019/7/9 08:45
 * @Description: Guava提供的不可变对象创建工具
 */
@ThreadSafe
public class ImmutableExample2 {
    private static ImmutableList<Integer> list = ImmutableList.of(1, 2, 3, 4);

    private static ImmutableMap<Integer, Integer> map = ImmutableMap.of(1, 1, 2, 2, 3, 3);
    private static ImmutableMap<Integer, Integer> map2 = ImmutableMap.<Integer, Integer>builder().put(1, 2).put(2, 3).build();

    public static void main(String[] args) {
        //会抛出UnsupportedOperationException异常
        //list.add(5);

        //会抛出UnsupportedOperationException异常
        //map.put(1,2);
        System.out.println(map.get(1));
        System.out.println(map2.get(1));
    }
}
