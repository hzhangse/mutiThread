package com.train.vola;

import java.util.concurrent.atomic.AtomicLong;

public class VolatileTest {

	public volatile static int count = 0;
	private static AtomicLong atomCount = new AtomicLong(0);
	public static void inc() {

		// �����ӳ�1���룬ʹ�ý������
		try {
			Thread.sleep(1);
			
		} catch (InterruptedException e) {
		}

		count++;
		atomCount.getAndIncrement();
		System.out.println(count+":"+atomCount.get());
	}

	public static void main(String[] args) {

		// ͬʱ����1000���̣߳�ȥ����i++���㣬����ʵ�ʽ��

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
		// ����ÿ�����е�ֵ���п��ܲ�ͬ,����Ϊ1000
		System.out.println("���н��:count=" + VolatileTest.count);
		System.out.println("���н��:atomCount=" + VolatileTest.atomCount.get());
	}
}
