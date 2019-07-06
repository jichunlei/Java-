package com.mall.Concurrency.publish;

import com.mall.Concurrency.annoations.NotThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * @Auther: xianzilei
 * @Date: 2019/7/5 08:28
 * @Description:
 */
@Slf4j
@NotThreadSafe
public class UnsafePublish {
    private String[] args = {"a", "b", "c", "d"};

    public String[] getArgs() {
        return args;
    }

    public static void main(String[] args) {
        UnsafePublish unsafePublish = new UnsafePublish();
        String[] strings = unsafePublish.getArgs();
        log.info("{}", Arrays.toString(strings));
        strings[0] = "e";
        log.info("{}", Arrays.toString(strings));
    }
}
