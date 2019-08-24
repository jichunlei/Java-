package com.mall.Concurrency.volatiledemo;

public class MyData {
	// 使用volatile修饰
	  int i = 0;

	// 修改i操作
	public void add() {
		this.i+=1;
	}
}
