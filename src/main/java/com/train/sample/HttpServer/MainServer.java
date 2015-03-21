package com.train.sample.HttpServer;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Title: Description: �����ʼ���̳߳��Լ����������� Copyright: Copyright (c) 2003 Company:
 * 
 * @author not attributable
 * @version 1.0
 */
public class MainServer {
	// ��ʼ������
	public static final int MAX_CLIENT = 100; // ϵͳ���ͬʱ����ͻ���
	// ��ʼ���̳߳�
	static BlockingQueue<Runnable> bqueue = new ArrayBlockingQueue<Runnable>(MAX_CLIENT);
	static ThreadPoolExecutor pool = new ThreadPoolExecutor(5, 10, 2,
			TimeUnit.MILLISECONDS, bqueue);
	
	// ������Ϊ�̳߳س�ʼ����һ��
	// ����Ϊ10�����񻺳���С�

	public MainServer() {
		// �����̳߳����в���
		 // �����̳߳س�ʼ����Ϊ5���߳�
		// ���ڳ������е�����ʹ�����������ԡ�
		 // ���̳߳�������ʱ�򣬳�ʼ���˾���һ���������ڵ�2�����ȡ��߳�
	}

	public static void main(String[] args) {
		MainServer MainServer1 = new MainServer();
		new HTTPListener().start();// ���������������ʹ����߳�
		//new manageServer().start();// ���������߳�
	}
}
