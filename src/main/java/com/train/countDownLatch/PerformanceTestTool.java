package com.train.countDownLatch;

import java.util.concurrent.CountDownLatch;

public class PerformanceTestTool {

	public long timecost(final int times, final Runnable task)
			throws InterruptedException {
		if (times <= 0)
			throw new IllegalArgumentException();
		final CountDownLatch startLatch = new CountDownLatch(1);
		final CountDownLatch overLatch = new CountDownLatch(times);
		for (int i = 0; i < times; i++) {
			new Thread(new Runnable() {
				public void run() {
					try {
						startLatch.await();
						//
						task.run();
					} catch (InterruptedException ex) {
						Thread.currentThread().interrupt();
					} finally {
						overLatch.countDown();
					}
				}
			}).start();
		}
		//
		long start = System.nanoTime();
		startLatch.countDown();
		overLatch.await();
		long all = System.nanoTime() - start;
		System.out.println(" run thread using time:"+all);
		return System.nanoTime() - start;
	}
	
	
	public static void main(String[] args) throws InterruptedException{
		PerformanceTestTool tool = new PerformanceTestTool();
		tool.timecost(10, new Runnable() {
				public void run() {
					//System.out.print(j+"-");
					try {
						Thread.sleep(1000L);
						System.out.println(" run thread");
					} catch (Exception e) {
						return;
					}
				}
		});
		
	}
}