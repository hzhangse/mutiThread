package com.train.bolcking;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class BlockingDequeTest {

	public static void main(String[] args) throws InterruptedException {
		BlockingDeque bDeque = new LinkedBlockingDeque(20);
		for (int i = 0; i < 30; i++) {
			// ��ָ��Ԫ����ӵ�������ջ�У����û�п��ÿռ䣬��һֱ�ȴ�������б�Ҫ����
			bDeque.putFirst(i);
			System.out.println("������ջ�������Ԫ��:" + i);
		}
		System.out.println("���򵽴����н����������˳�----");
	}
}
