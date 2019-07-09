package com.mall.Concurrency.immutable;

import com.google.common.collect.Maps;
import com.mall.Concurrency.annoations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.Map;

/**
 * @Auther: xianzilei
 * @Date: 2019/7/8 08:54
 * @Description:
 */
@Slf4j
@ThreadSafe
public class ImmutableExample1 {
    public static Map<Integer, Integer> map = Maps.newHashMap();

    static {
        map.put(1, 1);
        map.put(2, 2);
        map.put(3, 3);
        //该map不可修改其中的元素，但是可以调用put等修改元素的方法，只是会抛出UnsupportedOperationException异常
        map = Collections.unmodifiableMap(map);
    }

    public static void main(String[] args) {
        //抛出UnsupportedOperationException异常，即map内部元素不可被修改
        map.put(1, 2);
        log.info("{}", map.get(1));
    }
}
