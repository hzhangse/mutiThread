package com.train.theadpool;

import com.train.Utils;

public class MyThread extends Thread {
	@Override
	public void run() {
		//Utils.startTheCountdown(2);
		System.out.println(Thread.currentThread().getName() + "ÕıÔÚÖ´ĞĞ¡£¡£¡£"+ this.getName());
	}
}