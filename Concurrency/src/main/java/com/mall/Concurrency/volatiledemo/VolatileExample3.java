package com.mall.Concurrency.volatiledemo;

/**
 * @ClassName: VolatileExample3
 * @Description: 指令重排导致线程安全问题案例
 * @Author xianzilei
 * @DateTime 2019年8月24日 上午9:07:59
 */
public class VolatileExample3 {
	int i=0;
	boolean flag=false;
	
	public void method1() {
		//语句1和语句2没有依赖关系，编译器可以进行指令重排
		//实际执行的顺序可能为：1-2或2-1
		i=1;//语句1
		flag=true;//语句2
	}
	
	public int method2() {
		//当执行顺序为1-2时，多线程情况下可能返回2
		//当执行顺序为2-1时，多线程情况下可能返回1
		if(flag) {
			i++;
		}
		return i;
	}
}
