package com.train.student;

import java.util.Random;

public class AccesssStudentThreadDemoObjectLock implements Runnable {
	private Object studentLock = new Object();
	private Object countLock = new Object();
	Student student = new Student();
	int count;

	public static void main(String[] agrs) {
		AccesssStudentThreadDemoObjectLock td = new AccesssStudentThreadDemoObjectLock();
		
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

	public void accessStudent() {
		long startTime = System.currentTimeMillis();
		String currentThreadName = Thread.currentThread().getName();
		System.out.println(currentThreadName + " is running!");
		synchronized (countLock) {
			try {
				this.count++;
				Thread.sleep(5000);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
			System.out.println("thread " + currentThreadName + " read count:"
					+ this.count);
		}
		
		// System.out.println("first  read age is:"+this.student.getAge());
		synchronized (studentLock) {
			Random random = new Random();
			int age = random.nextInt(100);
			System.out.println("thread " + currentThreadName + " set age to:"
					+ age);

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
		}
		
		long endTime = System.currentTimeMillis();
		long spendTime = endTime - startTime;
		System.out.println("thread " + currentThreadName+" 鑺辫垂鏃堕棿锛�" + spendTime + "姣");

	}

}
