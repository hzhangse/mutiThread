package com.train.selfThreadPool;

import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 
 */
public class ModelSample {
	/** 线程池提交的任务�? */
	private final int taskNum = Runtime.getRuntime().availableProcessors() + 1;
	/** 用于多线程间存取产品的队�? */
	private final LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<String>(
			16);
	/** 记录产量 */
	private final AtomicLong output = new AtomicLong(0);
	/** 记录�?�? */
	private final AtomicLong sales = new AtomicLong(0);
	/** �?单的线程起步�?�? */
	private final CountDownLatch latch = new CountDownLatch(1);
	/** 停产后是否售完队列内的产品的选项 */
	private final boolean clear;
	/** 用于提交任务的线程池 */
	private final ExecutorService pool;
	/** �?陋的命令发�?�器 */
	private Scanner scanner;

	public ModelSample(boolean clear) {
		this.pool = Executors.newCachedThreadPool();
		this.clear = clear;
	}

	/**
	 * 提交生产和消费任务给线程�?,并在准备完毕后等待终止命�?
	 */
	public void service() {
		doService();
		waitCommand();
	}

	/**
	 * 提交生产和消费任务给线程�?,并在准备完毕后同时执�?
	 */
	private void doService() {
		for (int i = 0; i < taskNum; i++) {
			if (i == 0) {
				pool.submit(new Worker(queue, output, latch));
			} else {
				pool.submit(new Seller(queue, sales, latch, clear));
			}
		}
		latch.countDown();// �?闸放�?,线程池内的线程正式开始工�?
	}

	/**
	 * 接收来自终端输入的终止命�?
	 */
	private void waitCommand() {
		scanner = new Scanner(System.in);
		while (!scanner.nextLine().equals("q")) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		scanner.close();
		destory();
	}

	/**
	 * 停止�?切生产和�?售的线程
	 */
	private void destory() {
		pool.shutdownNow(); // 不再接受新任�?,同时试图中断池内正在执行的任�?
		while (clear && queue.size() > 0) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Products:" + output.get() + "; Sales:"
				+ sales.get());
	}

	public static void main(String[] args) {
		ModelSample model = new ModelSample(true);
		model.service();
	}
}

/**
 * 生产�?
 * 
 * @author: yanxuxin
 * @date: 2010-1-25
 */
class Worker implements Runnable {

	/** 假想的产�? */
	private static final String PRODUCT = "Thinkpad";
	private final LinkedBlockingQueue<String> queue;
	private final CountDownLatch latch;
	private final AtomicLong output;

	public Worker(LinkedBlockingQueue<String> queue, AtomicLong output,
			CountDownLatch latch) {
		this.output = output;
		this.queue = queue;
		this.latch = latch;
	}

	public void run() {
		try {
			latch.await(); // 放闸之前老实的等待着
			for (;;) {
				doWork();
				Thread.sleep(100);
			}
		} catch (InterruptedException e) {
			System.out.println("Worker thread will be interrupted...");
		}
	}

	private void doWork() throws InterruptedException {
		boolean success = queue.offer(PRODUCT, 100, TimeUnit.MILLISECONDS);
		if (success) {
			output.incrementAndGet(); // 可以声明long型的参数获得返回�?,作为日志的参�?
			// 可以在此处生成记录日�?
		}
	}
}

/**
 * 消费�?
 * 
 * @author: yanxuxin
 * @date: 2010-1-25
 */
class Seller implements Runnable {

	private final LinkedBlockingQueue<String> queue;
	private final AtomicLong sales;
	private final CountDownLatch latch;
	private final boolean clear;

	public Seller(LinkedBlockingQueue<String> queue, AtomicLong sales,
			CountDownLatch latch, boolean clear) {
		this.queue = queue;
		this.sales = sales;
		this.latch = latch;
		this.clear = clear;
	}

	public void run() {
		try {
			latch.await(); // 放闸之前老实的等待着
			for (;;) {
				sale();
				Thread.sleep(500);
			}
		} catch (InterruptedException e) {
			if (clear) { // 响应中断请求�?,如果有要求则�?售完队列的产品后再终止线�?
				cleanWarehouse();
			} else {
				System.out.println("Seller Thread will be interrupted...");
			}
		}
	}

	private void sale() throws InterruptedException {
		String item = queue.poll(50, TimeUnit.MILLISECONDS);
		if (item != null) {
			sales.incrementAndGet(); // 可以声明long型的参数获得返回�?,作为日志的参�?
			// 可以在此处生成记录日�?
		}
	}

	/**
	 * �?售完队列剩余的产�?
	 */
	private void cleanWarehouse() {
		try {
			while (queue.size() > 0) {
				sale();
			}
		} catch (InterruptedException ex) {
			System.out.println("Seller Thread will be interrupted...");
		}
	}
}