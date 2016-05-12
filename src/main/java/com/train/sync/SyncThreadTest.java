package com.train.sync;

import org.junit.Test;

import com.train.MutiTestRunnable;

class MyThread1 extends Thread {
	public void run() {
		for (int i = 0; i < 10; i++) {
			System.out.println("�߳�1��" + i + "��ִ�У�");
		}
	}
}
public class SyncThreadTest {
	
	
	@Test
	public  void testSyncMethod() {
		MyRunnable r = new MyRunnable();
		Thread ta = new Thread(r, "Thread-A");
		Thread tb = new Thread(r, "Thread-B");
//		ta.start();
//		tb.start();
		Runnable[] arr = {ta,tb};
		MutiTestRunnable.Execute(arr);
	}

	//@Test
	public  void testJoin() {

		Thread t1 = new MyThread1();
		t1.start();

		for (int i = 0; i < 20; i++) {
			System.out.println("���̵߳�" + i + "��ִ�У�");
			if (i > 2)
				try {
					// t1�̺߳ϲ������߳��У����߳�ִֹͣ�й��̣�ת��ִ��t1�̣߳�ֱ��t1ִ����Ϻ������
					t1.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		}
	}


	//@Test
	public  void testStaticSyncMethod() {
		MyStaticRunnable ra = new MyStaticRunnable("Thread-A");
		MyStaticRunnable rb = new MyStaticRunnable("Thread-B");
		Thread ta = new Thread(ra);
		Thread tb = new Thread(rb);
//		ta.start();
//		tb.start();
		Runnable[] arr = {ta,tb};
		MutiTestRunnable.Execute(arr);
	}

	public static void main(String[] args){
		//testStaticSyncMethod();
	}
}
