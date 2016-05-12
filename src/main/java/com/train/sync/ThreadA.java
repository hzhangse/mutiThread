
package com.train.sync;

public class ThreadA {
	public static void main(String[] args) {
		Calculator b = new Calculator();
		// ���������߳�
		b.start();
		
		// �߳�Aӵ��b�����ϵ������߳�Ϊ�˵���wait()��notify()���������̱߳������Ǹ���������ӵ����
//		try {
//			System.out.println("�ȴ�����b��ɼ��㡣����");
//			synchronized (b) { // ��ǰ�߳�A�ȴ�
//				b.wait();
//			}
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		try {
			//b.join();
			synchronized (b) {
				while (b.isAlive()) 
				{
					b.wait(0);
				}
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("b���������ܺ��ǣ�" + b.total);
	}
	
}
