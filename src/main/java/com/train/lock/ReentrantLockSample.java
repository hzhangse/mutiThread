package com.train.lock;

import java.util.concurrent.locks.ReentrantLock;


public class ReentrantLockSample {

	public static void main(String[] args) {
		//testSynchronized();
		testReentrantLock();
	}

	public static void testReentrantLock() {
		final SampleSupport1 support = new SampleSupport1();
		Thread first = new Thread(new Runnable() {
			public void run() {
				try {
					support.doSomething();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});

		Thread second = new Thread(new Runnable() {
			public void run() {
				try {
					support.doSomething();
				} catch (InterruptedException e) {
					System.out
							.println("Second Thread Interrupted without executing counter++,beacuse it waits a long time.");
				}
			}
		});

		executeTest(first, second);
	}

	public static void testSynchronized() {
		final SampleSupport2 support2 = new SampleSupport2();

		Runnable runnable = new Runnable() {
			public void run() {
				support2.doSomething();
			}
		};

		Thread third = new Thread(runnable);
		Thread fourth = new Thread(runnable);

		executeTest(third, fourth);
	}

	/**
	 * Make thread a run faster than thread b, then thread b will be interruted
	 * after about 1s.
	 * 
	 * @param a
	 * @param b
	 */
	public static void executeTest(Thread a, Thread b) {
		a.start();
		try {
			Thread.sleep(100);
			b.start(); // The main thread sleep 100ms, and then start the second
						// thread.

			Thread.sleep(1000);
			// 1s later, the main thread decided not to allow the second thread
			// wait any longer.
			b.interrupt();
			System.out.println("intercept");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

abstract class SampleSupport {

	protected int counter;

	/**
	 * A simple countdown,it will stop after about 5s.
	 */
	public void startTheCountdown() {
		long currentTime = System.currentTimeMillis();
		for (;;) {
			long diff = System.currentTimeMillis() - currentTime;
			if (diff > 5000) {
				break;
			}
		}
	}
}

class SampleSupport1 extends SampleSupport {

	private final ReentrantLock lock = new ReentrantLock();

	public void doSomething() throws InterruptedException {
		lock.lockInterruptibly(); // (1)
		System.out.println(Thread.currentThread().getName()
				+ " will execute counter++.");
		startTheCountdown();
		try {
			counter++;
		} finally {
			lock.unlock();
		}
	}
}

class SampleSupport2 extends SampleSupport {

	public synchronized void doSomething() {
		System.out.println(Thread.currentThread().getName()
				+ " will execute counter++.");
		startTheCountdown();
		counter++;
	}
}
