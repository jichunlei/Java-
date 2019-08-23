package com.mall.Concurrency.volatiledemo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class VolatileExample1 {
	public volatile int i = 0;

	public void add() {
		i++;
	}

	public static void main(String[] args) {
		VolatileExample1 example = new VolatileExample1();
		for (int i = 0; i < 100; i++) {
			new Thread(() -> {
				for (int j = 0; j < 100; j++) {
					example.add();
				}
			}).start();
		}
		while (Thread.activeCount() > 2) {
			Thread.yield();
		}
		log.info("i:{}" + example.i);
	}
}
