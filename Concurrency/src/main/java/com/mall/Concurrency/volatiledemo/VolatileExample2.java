package com.mall.Concurrency.volatiledemo;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: VolatileExample2
 * @Description: volatile可见性测试
 * @Author xianzilei
 * @DateTime 2019年8月23日 下午6:12:37
 */
@Slf4j
public class VolatileExample2 {
	// 使用volatile修饰
	public  int i = 0;

	//修改i操作
	public void modify() {
		i=10;
	}

	public static void main(String[] args) {
		VolatileExample2 example = new VolatileExample2();
		new Thread(() -> {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
			example.modify();
			log.info("自增完成>>>i:{}",example.i);
		}).start();
		while (example.i==0) {
			log.info("自增未完成>>>");
		}
		log.info("主线程完毕>>>i:{}", example.i);
	}
}
