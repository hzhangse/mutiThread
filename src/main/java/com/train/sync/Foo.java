package com.train.sync;

public class Foo {

	private int x = 100;

	public int getX() {
		return x;
	}

	public synchronized int fix(int y) {
		x = x - y;
		System.out.println(Thread.currentThread().getName() + " : 当前foo对象的x值= "
				+ getX());
		return x;
	}

	public static synchronized void syncMethod1(int i) {
		
			
			System.out.println(Thread.currentThread().getName()
					+ ": syncMethod1 execute " + Integer.valueOf(i) + " times");
		

	}

	public static void syncMethod2(int i) {
		synchronized (Foo.class) {
			System.out.println(Thread.currentThread().getName()
					+ ": syncMethod2 execute " + Integer.valueOf(i) + " times");
		}
		try {
			
			Thread.sleep(5000);
			System.out.println(Thread.currentThread().getName()
					+ ": syncMethod2 execute "+ Integer.valueOf(i) + " times,"+" need waiting 5 seconds...");
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
