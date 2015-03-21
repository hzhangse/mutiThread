package com.train.sync;

public class NameListTest {

	public static void main(String[] args) {
		final NameList nl = new NameList();
		
		for (Integer i=1;i<3;i++){
			nl.add(i.toString());
		}
		class NameDropper extends Thread {
			public void run() {
				String name = nl.removeFirst();
				System.out.println(name);
			}
		}

		
		for (int i=0;i<10;i++){
			Thread t = new NameDropper();
			t.start();
		}
//		Thread t1 = new NameDropper();
//		Thread t2 = new NameDropper();
//		t1.start();
//		t2.start();
	}

}
