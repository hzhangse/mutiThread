
package com.train.lock;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockTimeCompare {
	
	public volatile static int	count	  = 0;
	public static int	   count1	  = 0;
	private static AtomicLong	atomCount	= new AtomicLong(0);
	static int	           times	  = 1000000;
	static Lock	           lock	          = new ReentrantLock();
	
	public static void inc1() {
		synchronized (LockTimeCompare.class) {
			count1++;
			// System.out.println(count1);
		}
	}
	
	public static void inc4() {
		try {
			lock.lock();
			count1++;
		} finally {
			lock.unlock();
		}
	}
	
	public static void inc2() {
		atomCount.getAndIncrement();
	}
	
	public static void inc3() {
		count++;
	}
	
	public static void testSynSpenttime() throws InterruptedException {
		count =0;
		long timeStar = System.currentTimeMillis();// 得到当前的时间
		final ExecutorService exec = Executors.newFixedThreadPool(10);
		final CountDownLatch end = new CountDownLatch(times);
		for (int i = 0; i < times; i++) {
			exec.submit(
			                new Thread() {
				                public void run() {
					                inc1();
					                end.countDown();
				                }
			                });
		}
		end.await();
		
		long timeEnd = System.currentTimeMillis();// 得到当前的时间
		System.out.println("syn:" + (timeEnd - timeStar) + "  value:" + count1);
		exec.shutdown();
	}
	
	public static void testLockSpenttime() throws InterruptedException {
		count =0;
		long timeStar = System.currentTimeMillis();// 得到当前的时间
		final ExecutorService exec = Executors.newFixedThreadPool(10);
		final CountDownLatch end = new CountDownLatch(times);
		for (int i = 0; i < times; i++) {
			exec.submit(
			                new Thread() {
				                public void run() {
					                inc4();
					                end.countDown();
				                }
			                });
		}
		end.await();
		
		long timeEnd = System.currentTimeMillis();// 得到当前的时间
		System.out.println("retran lock:" + (timeEnd - timeStar) + "  value:" + count1);
		exec.shutdown();
	}
	
	public static void testAtomSpenttime() throws InterruptedException {
		long timeStar = System.currentTimeMillis();// 得到当前的时间
		final ExecutorService exec = Executors.newFixedThreadPool(10);
		final CountDownLatch end = new CountDownLatch(times);
		for (int i = 0; i < times; i++) {
			exec.submit(
			                new Thread() {
				                public void run() {
					                inc2();
					                end.countDown();
				                }
			                });
		}
		end.await();
		
		long timeEnd = System.currentTimeMillis();// 得到当前的时间
		System.out.println("atom inc:" + (timeEnd - timeStar) + "  value:" + atomCount);
		exec.shutdown();
	}
	
	public static void testVolatileSpenttime() throws InterruptedException {
		long timeStar = System.currentTimeMillis();// 得到当前的时间
		final ExecutorService exec = Executors.newFixedThreadPool(10);
		final CountDownLatch end = new CountDownLatch(times);
		for (int i = 0; i < times; i++) {
			exec.submit(
			                new Thread() {
				                public void run() {
					                inc3();
					                end.countDown();
				                }
			                });
		}
		end.await();
		
		long timeEnd = System.currentTimeMillis();// 得到当前的时间
		System.out.println("vilatile inc:" + (timeEnd - timeStar) + "  value:" + count);
		exec.shutdown();
	}
	
	public static void main(String[] args) throws InterruptedException {
		testLockSpenttime();
		testSynSpenttime();
		testAtomSpenttime();
		testVolatileSpenttime();
		
	}
}
