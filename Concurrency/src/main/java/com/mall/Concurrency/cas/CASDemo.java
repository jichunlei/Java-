/**
 * @Title: CASDemo.java
 * @Package com.mall.Concurrency.cas
 * @Description: 
 * Copyright: Copyright (c) 2018
 * 
 * @Author xianzilei
 * @DateTime 2019年8月25日 上午9:28:20
 * @version V1.0
 */

package com.mall.Concurrency.cas;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName: CASDemo
 * @Description: CAS操作
 * @Author xianzilei
 * @DateTime 2019年8月25日 上午9:28:55
 */
public class CASDemo {
	public static void main(String[] args) {
		//底层原理：Unsafe类中（CAS算法+自旋锁）
		AtomicInteger atomicInteger=new AtomicInteger(5);
		boolean b1 = atomicInteger.compareAndSet(5, 10);
		System.out.println(b1);
		System.out.println(atomicInteger.get());
		/**
		 * CAS缺点
		 * 1.自旋锁导致CPU开销大
		 * 2.只能保证一个共享变量的原子操作
		 * 3.无法解决ABA问题
		 */
	}
}
