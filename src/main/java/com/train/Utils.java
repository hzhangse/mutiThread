package com.train;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

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
	public static void computeTime(int timewindow,Date time){
		long currentTime = System.currentTimeMillis();
		long diff = currentTime - time.getTime();
		long sec = diff/1000;
		long minute = sec/60;
		long hour = minute/60;
		if (diff<timewindow*1000*3600){
			
			System.out.println("aaa");
		}
	}
	public static void main(String[] args){
		Date time = new Date();
		time.setHours(14);
		System.out.println(time.toLocaleString());
		//time.getTime().
		computeTime(2,time);
		
		List a = new ArrayList();
		a.add("a");
		a.add("b");
		a.add("c");
		
		Iterator iter = a.iterator();
		while (iter.hasNext()){
			String obj = (String) iter.next();
			if (obj.equalsIgnoreCase("a")){
				iter.remove();
			}
		}
		System.out.println(a);
	}
}
