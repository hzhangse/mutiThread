package com.train.student;

import java.util.Random;

public class AccesssStudentThreadDemoSynLock implements Runnable {

	Student student = new Student();
	int count;

	public static void main(String[] agrs) {
		AccesssStudentThreadDemoSynLock td = new AccesssStudentThreadDemoSynLock();
		Thread t1 = new Thread(td, "a");
		Thread t2 = new Thread(td, "b");
		t1.start();
		t2.start();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		
		
		accessStudent();
	
	}

	public synchronized void accessStudent() {
		long startTime = System.currentTimeMillis();
		String currentThreadName = Thread.currentThread().getName();
		System.out.println(currentThreadName + " is running!");
		try {
			this.count++;
			Thread.sleep(5000);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
		System.out.println("thread " + currentThreadName + " read count:"
				+ this.count);
		// System.out.println("first  read age is:"+this.student.getAge());
		Random random = new Random();
		int age = random.nextInt(100);
		System.out
				.println("thread " + currentThreadName + " set age to:" + age);

		this.student.setAge(age);
		System.out.println("thread " + currentThreadName
				+ " first  read age is:" + this.student.getAge());
		try {
			Thread.sleep(5000);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
		System.out.println("thread " + currentThreadName
				+ " second read age is:" + this.student.getAge());
		long endTime = System.currentTimeMillis();
		long spendTime = endTime - startTime;
		System.out.println("thread " + currentThreadName+"  花费时间�?" + spendTime + "毫秒");
	}

}
