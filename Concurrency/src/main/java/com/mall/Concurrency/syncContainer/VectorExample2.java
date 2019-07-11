package com.mall.Concurrency.syncContainer;

import com.mall.Concurrency.annoations.NotThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Vector;

/**
 * @Auther: xianzilei
 * @Date: 2019/7/11 07:50
 * @Description: Vector用法(线程不安全的例子)
 */
@Slf4j
@NotThreadSafe
public class VectorExample2 {


    private static List<Integer> vector = new Vector<>();

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            vector.add(i);
        }

        while (true) {
            new Thread(() -> {
                for (int i = 0; i < vector.size(); i++) {
                    vector.remove(i);
                }
            }).start();

            new Thread(() -> {
                for (int i = 0; i < vector.size(); i++) {
                    vector.get(i);
                }
            }).start();
        }
    }

}
