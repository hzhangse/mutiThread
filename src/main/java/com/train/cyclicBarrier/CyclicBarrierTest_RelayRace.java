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
				System.out.println("���ˣ���ҿ���ȥ�Է��ˡ���");
			}
		});

		System.out.println("Ҫ�Է������������˶����յ㣬oK?");
		System.out.println("��������������");

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
							+ ":�ҵ��յ���");
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
							+ ":���ڿ��ԳԷ�����");

				}
			});

		}
		exec.shutdown();

	}

}
