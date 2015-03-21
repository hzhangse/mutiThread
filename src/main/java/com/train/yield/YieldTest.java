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
			System.out.println("�߳�1��" + i + "��ִ�У�");
		}
	}
}

class MyRunnable implements Runnable {
	public void run() {
		for (int i = 0; i < 10; i++) {
			System.out.println("�߳�2��" + i + "��ִ�У�");
			Thread.yield();
		}
	}
}
