package com.train.sample.HttpServer;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * Title: Description: 负责监听端口以及将任务交给线程池处理 Copyright: Copyright (c) 2003 Company:
 * 
 * @author not attributable
 * @version 1.0
 */
public class HTTPListener extends Thread {
	public HTTPListener() {
	}

	public void run() {
		try {
			ServerSocket server = null;
			Socket clientconnection = null;
			server = new ServerSocket(8008);// 服务套接字监听某地址端口对
			while (true) {// 无限循环
				clientconnection = server.accept();
				System.out.println("Client connected in!");
				// 使用线程池启动服务
			//	MainServer.pool.execute(new HTTPRequest(clientconnection));// 如果收到一个请求，则从线程池中取一个线程进行服务，任务完成后，该线程自动返还线程池
			}
		} catch (Exception e) {
			System.err
					.println("Unable to start serve listen:" + e.getMessage());
			e.printStackTrace();
		}
	}
}
