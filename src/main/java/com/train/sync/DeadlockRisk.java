package com.train.sync;

public class DeadlockRisk {
	private static class Resource {
		public int value;
	}

	private Resource resourceA = new Resource();
	private Resource resourceB = new Resource();

	public int read() {
		synchronized (resourceA) {
			synchronized (resourceB) {
				return resourceB.value + resourceA.value;
			}
		}
	}

	public void write(int a, int b) {
		synchronized (resourceB) {
			synchronized (resourceA) {
				resourceA.value = a;
				resourceB.value = b;
			}
		}
	}

	public static void main(String[] args) {
		DeadlockRisk deadlock = new DeadlockRisk();
		Runnable w = new WriteThread(deadlock);
		Runnable r = new ReadThread(deadlock);
		for (int i = 0; i < 10; i++) {
			Thread t = new Thread(w);
			t.start();
		}
		for (int i = 0; i < 10; i++) {
			Thread t = new Thread(r);
			t.start();
		}
	}
}

class WriteThread implements Runnable {
	DeadlockRisk deadlock;

	WriteThread(DeadlockRisk deadlock) {
		this.deadlock = deadlock;
	}

	public void run() {
		deadlock.write(1, 2);
	}
}

class ReadThread implements Runnable {
	DeadlockRisk deadlock;

	ReadThread(DeadlockRisk deadlock) {
		this.deadlock = deadlock;
	}

	public void run() {
		deadlock.read();
	}
}