package com.train.theadpool;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolTest {
	public static void main(String[] args) {
		// ����һ���̳߳أ����ɰ����ڸ����ӳٺ�����������߶��ڵ�ִ�С�
		ScheduledExecutorService pool = Executors.newScheduledThreadPool(2);
		// ����ʵ����Runnable�ӿڶ���Thread����ȻҲʵ����Runnable�ӿ�
		Thread t1 = new MyThread();
		Thread t2 = new MyThread();
		Thread t3 = new MyThread();
		Thread t4 = new MyThread();
		Thread t5 = new MyThread();
		// ���̷߳�����н���ִ��
		pool.execute(t1);
		pool.execute(t2);
		pool.execute(t3);
		// ʹ���ӳ�ִ�з��ķ���
		pool.schedule(t4, 2000, TimeUnit.MILLISECONDS);
		pool.schedule(t5, 100, TimeUnit.MILLISECONDS);
		// �ر��̳߳�
		pool.shutdown();
	}
}


