package com.train.Daemon;

public class DaemoThreadTest {
	public static void main(String[] args) {
		Thread t1 = new MyCommon();
		Thread t2 = new Thread(new MyDaemon(t1));
		t2.setDaemon(true); // ����Ϊ�ػ��߳�

		t2.start();
		t1.start();
	}
}

class MyCommon extends Thread {
	String time;
	public void run() {
		for (int i = 0; i < 5; i++) {
			//System.out.println("�߳�1��" + i + "��ִ�У�");
			this.time = String.valueOf(i);
			try {
				Thread.sleep(7);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

class MyDaemon implements Runnable {
	Thread object;
	MyDaemon(Thread object){
		this.object = object;
	}
	public void run() {
		int i=0;
		while (true) {
			System.out.println("��̨�̵߳�" + i++ + "��ִ�У�");
			System.out.println("��������ǰCommon�߳�ִ���� " +((MyCommon) object).time + "�Σ�");
			try {
				Thread.sleep(7);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
