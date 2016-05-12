package com.train.list;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConcurrentHashMapDemo {
	/**
	 * 读线程
	 * @author 
	 *
	 */
	private static class ReadTask implements Runnable {
		Map<String,String>	map;
		
		public ReadTask(Map<String,String> list) {
			this.map = list;
		}
		
		public void run() {
			for (String key : map.keySet()) {
				System.out.println(map.get(key));
			}
		}
	}
	
	/**
	 * 写线程
	 * @author 
	 *
	 */
	private static class WriteTask implements Runnable {
		Map<String,String>	map;
		int		index;
		
		public WriteTask(Map<String,String> list, int index) {
			this.map = list;
			this.index = index;
		}
		
		public void run() {
			map.remove(String.valueOf(index));
			map.put(String.valueOf(index), "write_" + index);
		}
	}
	
	public void run() {
		final int NUM = 100;
		// will throw java.util.ConcurrentModificationException
		//Map<String,String> map = new HashMap<String,String>();
		
		
		
		ConcurrentHashMap<String,String> map = new ConcurrentHashMap<String,String>();
		for (int i = 0; i < NUM; i++) {			
			map.put(String.valueOf(i),"main_" + i);
		}
		ExecutorService executorService = Executors
		                .newFixedThreadPool(NUM);
		for (int i = 0; i < NUM; i++) {
			executorService.execute(new ReadTask(map));
			executorService.execute(new WriteTask(map, i));
		}
		executorService.shutdown();
	}
	
	public static void main(String[] args) {
		new ConcurrentHashMapDemo().run();
	}
}
