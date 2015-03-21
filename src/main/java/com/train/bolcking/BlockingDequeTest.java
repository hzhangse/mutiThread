package com.train.bolcking;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class BlockingDequeTest {

	public static void main(String[] args) throws InterruptedException {
		BlockingDeque bDeque = new LinkedBlockingDeque(20);
		for (int i = 0; i < 30; i++) {
			// 将指定元素添加到此阻塞栈中，如果没有可用空间，将一直等待（如果有必要）。
			bDeque.putFirst(i);
			System.out.println("向阻塞栈中添加了元素:" + i);
		}
		System.out.println("程序到此运行结束，即将退出----");
	}
}
