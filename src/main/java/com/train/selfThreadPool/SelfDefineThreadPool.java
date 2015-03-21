package com.train.selfThreadPool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.train.theadpool.MyThread;

public class SelfDefineThreadPool {

	public static void main(String[] args) {
		// 创建等待队列
		BlockingQueue<Runnable> bqueue = new ArrayBlockingQueue<Runnable>(20);
		// 创建一个单线程执行程序，它可安排在给定延迟后运行命令或者定期地执行。
		ThreadPoolExecutor pool = new ThreadPoolExecutor(2, 3, 5000,
				TimeUnit.MILLISECONDS, bqueue);
		// 创建实现了Runnable接口对象，Thread对象当然也实现了Runnable接口
		for (int i=0;i<23;i++){
			pool.execute(new MyThread());
		}
		// 关闭线程池
		pool.shutdown();
	}
}
