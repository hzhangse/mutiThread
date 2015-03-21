package com.train.yield;

public class YieldTest {

	public static void main(String[] args) {
		Thread t1 = new MyThread1();
		Thread t2 = new Thread(new MyRunnable());

		t2.start();
		t1.start();
	}
}

class MyThread1 extends Thread {
	public void run() {
		for (int i = 0; i < 10; i++) {
			System.out.println("线程1第" + i + "次执行！");
		}
	}
}

class MyRunnable implements Runnable {
	public void run() {
		for (int i = 0; i < 10; i++) {
			System.out.println("线程2第" + i + "次执行！");
			Thread.yield();
		}
	}
}
