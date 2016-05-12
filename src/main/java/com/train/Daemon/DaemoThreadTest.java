package com.train.Daemon;

public class DaemoThreadTest {
	public static void main(String[] args) {
		Thread t1 = new MyCommon();
		Thread t2 = new Thread(new MyDaemon(t1));
		t2.setDaemon(true); // 设置为守护线程

		t2.start();
		t1.start();
	}
}

class MyCommon extends Thread {
	String time;
	public void run() {
		for (int i = 0; i < 5; i++) {
			//System.out.println("线程1第" + i + "次执行！");
			this.time = String.valueOf(i);
			try {
				Thread.sleep(7);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

class MyDaemon implements Runnable {
	Thread object;
	MyDaemon(Thread object){
		this.object = object;
	}
	public void run() {
		int i=0;
		while (true) {
			System.out.println("后台线程第" + i++ + "次执行！");
			System.out.println("监听到当前Common线程执行了 " +((MyCommon) object).time + "次！");
			try {
				Thread.sleep(7);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
