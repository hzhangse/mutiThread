package com.train.sample.HttpServer;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * Title: Description: ��������˿��Լ������񽻸��̳߳ش��� Copyright: Copyright (c) 2003 Company:
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
			server = new ServerSocket(8008);// �����׽��ּ���ĳ��ַ�˿ڶ�
			while (true) {// ����ѭ��
				clientconnection = server.accept();
				System.out.println("Client connected in!");
				// ʹ���̳߳���������
			//	MainServer.pool.execute(new HTTPRequest(clientconnection));// ����յ�һ����������̳߳���ȡһ���߳̽��з���������ɺ󣬸��߳��Զ������̳߳�
			}
		} catch (Exception e) {
			System.err
					.println("Unable to start serve listen:" + e.getMessage());
			e.printStackTrace();
		}
	}
}
