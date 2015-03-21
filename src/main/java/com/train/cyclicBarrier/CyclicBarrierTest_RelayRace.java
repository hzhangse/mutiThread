package com.train.cyclicBarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 
 * @author HardPass
 * 
 */
public class CyclicBarrierTest_RelayRace {

	public static void main(String[] args) throws InterruptedException {
		ExecutorService exec = Executors.newCachedThreadPool();
		final CyclicBarrier barrier = new CyclicBarrier(4, new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("好了，大家可以去吃饭了……");
			}
		});

		System.out.println("要吃饭，必须所有人都到终点，oK?");
		System.out.println("不放弃不抛弃！");

		for (int i = 0; i < 4; i++) {
			exec.execute(
					new Runnable() {

				@Override
				public void run() {
					System.out
							.println(Thread.currentThread().getName() + ":Go");
					try {
						Thread.sleep((long) (2000 * Math.random()));
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					System.out.println(Thread.currentThread().getName()
							+ ":我到终点了");
					try {
						barrier.await();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (BrokenBarrierException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					System.out.println(Thread.currentThread().getName()
							+ ":终于可以吃饭啦！");

				}
			});

		}
		exec.shutdown();

	}

}
