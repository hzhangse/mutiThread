package com.train.vola;

import java.util.concurrent.atomic.AtomicLong;

public class VolatileTest {

	public volatile static int count = 0;
	private static AtomicLong atomCount = new AtomicLong(0);
	public static void inc() {

		// 这里延迟1毫秒，使得结果明显
		try {
			Thread.sleep(1);
			
		} catch (InterruptedException e) {
		}

		count++;
		atomCount.getAndIncrement();
		System.out.println(count+":"+atomCount.get());
	}

	public static void main(String[] args) {

		// 同时启动1000个线程，去进行i++计算，看看实际结果

		for (int i = 0; i < 1000; i++) {
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					
					VolatileTest.inc();
				}
			});
			t.start();

		}
		try {
			Thread.sleep(5000);
			
		} catch (InterruptedException e) {
		}
		// 这里每次运行的值都有可能不同,可能为1000
		System.out.println("运行结果:count=" + VolatileTest.count);
		System.out.println("运行结果:atomCount=" + VolatileTest.atomCount.get());
	}
}
