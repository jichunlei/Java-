package com.mall.Concurrency.syncContainer;

import com.mall.Concurrency.annoations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @Auther: xianzilei
 * @Date: 2019/7/11 07:50
 * @Description: 关于for循环中的调用remove操作
 */
@Slf4j
@ThreadSafe
public class ListExample {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("b");
        list.add("c");
        list.add("a");
        test3(list);
        log.info("{}", list);
    }

    private static void test1(List<String> list) {
        for (String s : list) {
            if (s.equals("c")) {
                list.remove(s);
            }
        }
    }

    private static void test2(List<String> list) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals("a")) {
                list.remove(i);
            }

        }
    }

    private static void test3(List<String> list) {
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            String s = iterator.next();
            if (s.equals("c")) {
                iterator.remove();
            }
        }
    }
}
