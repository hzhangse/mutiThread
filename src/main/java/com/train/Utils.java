package com.train;

public class Utils {
	public static void startTheCountdown(int second) {
		long currentTime = System.currentTimeMillis();
		for (;;) {
			long diff = System.currentTimeMillis() - currentTime;
			if (diff > second*1000) {
				break;
			}
		}
	}
}
