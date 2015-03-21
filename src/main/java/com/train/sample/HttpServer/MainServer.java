package com.train.sample.HttpServer;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Title: Description: 负责初始化线程池以及启动服务器 Copyright: Copyright (c) 2003 Company:
 * 
 * @author not attributable
 * @version 1.0
 */
public class MainServer {
	// 初始化常量
	public static final int MAX_CLIENT = 100; // 系统最大同时服务客户数
	// 初始化线程池
	static BlockingQueue<Runnable> bqueue = new ArrayBlockingQueue<Runnable>(MAX_CLIENT);
	static ThreadPoolExecutor pool = new ThreadPoolExecutor(5, 10, 2,
			TimeUnit.MILLISECONDS, bqueue);
	
	// 在这里为线程池初始化了一个
	// 长度为10的任务缓冲队列。

	public MainServer() {
		// 设置线程池运行参数
		 // 设置线程池初始容量为5个线程
		// 对于超出队列的请求，使用了抛弃策略。
		 // 在线程池启动的时候，初始化了具有一定生命周期的2个“热”线程
	}

	public static void main(String[] args) {
		MainServer MainServer1 = new MainServer();
		new HTTPListener().start();// 启动服务器监听和处理线程
		//new manageServer().start();// 启动管理线程
	}
}
