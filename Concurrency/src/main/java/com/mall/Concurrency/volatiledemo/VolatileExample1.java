package com.mall.Concurrency.volatiledemo;

import java.util.concurrent.atomic.AtomicInteger;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class VolatileExample1 {
	public volatile int i = 0;

	public void add() {
		// i++操作不是原子性的
		/*
		 * 查看i++的字节码，发现i++被拆解为四行指令 2 getfield : int [25] -->获取成员i 5 iconst_1 -->获取常量1 6
		 * iadd -->执行i+1操作 7 putfield : int [25] -->将计算完的值写入成员i中
		 */
		i++;
	}

	// 解决线程安全问题方式一:使用原子保证类
	AtomicInteger atomicInteger = new AtomicInteger();
	
	int j=0;
	//解决线程安全问题方式二:使用synchronized关键字
	public synchronized void addAtomic() {
		j++;
	}

	public static void main(String[] args) {
		VolatileExample1 example = new VolatileExample1();
		for (int i = 0; i < 100; i++) {
			new Thread(() -> {
				for (int j = 0; j < 100; j++) {
					example.add();
					example.atomicInteger.incrementAndGet();
					example.addAtomic();
				}
			}).start();
		}
		while (Thread.activeCount() > 2) {
			Thread.yield();
		}
		log.info("i:{}" + example.i);
		log.info("atomicInteger:{}" + example.atomicInteger);
		log.info("j:{}" + example.j);
	}
}
