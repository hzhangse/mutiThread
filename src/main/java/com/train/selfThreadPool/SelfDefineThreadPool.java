package com.train.selfThreadPool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.train.theadpool.MyThread;

 class AbortPolicy1 implements RejectedExecutionHandler {
	
	 BlockingQueue<Runnable> failureQueue = new LinkedBlockingQueue<Runnable>();
	 public BlockingQueue<Runnable> getFailureQueue() {
		return failureQueue;
	}

	/**
	 * Creates an <tt>AbortPolicy</tt>.
	 */
	public AbortPolicy1() {
	}

	public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
		
		failureQueue.offer(r);
		
		throw new RejectedExecutionException (r.toString() +" add to pool failed");
	}
	
	
}

public class SelfDefineThreadPool {

	public static void main(String[] args) {
		// 创建等待队列
		   RejectedExecutionHandler defaultHandler =
		        new AbortPolicy1();

		String a = new String("");
		BlockingQueue<Runnable> bqueue = new ArrayBlockingQueue<Runnable>(3);
		// 创建一个单线程执行程序，它可安排在给定延迟后运行命令或者定期地执行。
		ThreadPoolExecutor pool = new ThreadPoolExecutor(2, 3, 5000,
				TimeUnit.MILLISECONDS, bqueue,defaultHandler);
		// 创建实现了Runnable接口对象，Thread对象当然也实现了Runnable接口
		for (int i = 0; i < 10; i++) {
			try {
				pool.execute(new MyThread());
			} catch (RejectedExecutionException ex) {
				ex.printStackTrace();
			}
		}
		
		for (Runnable r: ((AbortPolicy1)defaultHandler).getFailureQueue()){
			pool.execute(r);
		}
		
		// 关闭线程池
		pool.shutdown();
	}
}
