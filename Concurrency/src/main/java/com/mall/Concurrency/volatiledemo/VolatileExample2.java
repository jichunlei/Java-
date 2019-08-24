package com.mall.Concurrency.volatiledemo;

/**
 * @ClassName: VolatileExample2
 * @Description: volatile可见性测试
 * @Author xianzilei
 * @DateTime 2019年8月23日 下午6:12:37
 */
public class VolatileExample2 {
	public static void main(String[] args) {
		MyData data = new MyData();
		new Thread(() -> {
			System.out.println("coming...");
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
			data.add();
			System.out.println("update...");
		}).start();
		while (data.i == 0) {
			//里面不能打印控制台信息或打印日志,否则测试不成功
		}
		System.out.println("done...");
	}

}
