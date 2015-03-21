package com.train.selfThreadPool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.train.theadpool.MyThread;

public class SelfDefineThreadPool {

	public static void main(String[] args) {
		// �����ȴ�����
		BlockingQueue<Runnable> bqueue = new ArrayBlockingQueue<Runnable>(20);
		// ����һ�����߳�ִ�г������ɰ����ڸ����ӳٺ�����������߶��ڵ�ִ�С�
		ThreadPoolExecutor pool = new ThreadPoolExecutor(2, 3, 5000,
				TimeUnit.MILLISECONDS, bqueue);
		// ����ʵ����Runnable�ӿڶ���Thread����ȻҲʵ����Runnable�ӿ�
		for (int i=0;i<23;i++){
			pool.execute(new MyThread());
		}
		// �ر��̳߳�
		pool.shutdown();
	}
}
