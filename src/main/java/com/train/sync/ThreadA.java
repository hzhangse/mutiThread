
package com.train.sync;

public class ThreadA {
	public static void main(String[] args) {
		Calculator b = new Calculator();
		// 启动计算线程
		b.start();
		
		// 线程A拥有b对象上的锁。线程为了调用wait()或notify()方法，该线程必须是那个对象锁的拥有者
//		try {
//			System.out.println("等待对象b完成计算。。。");
//			synchronized (b) { // 当前线程A等待
//				b.wait();
//			}
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		try {
			//b.join();
			synchronized (b) {
				while (b.isAlive()) 
				{
					b.wait(0);
				}
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("b对象计算的总和是：" + b.total);
	}
	
}
