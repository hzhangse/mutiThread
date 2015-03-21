package com.train.selfThreadPool;

import java.util.LinkedList;

public class WorkQueue {
	private final int nThreads;
	private final PoolWorker[] threads;
	private final LinkedList queue;

	public WorkQueue(int nThreads) {
		this.nThreads = nThreads;
		queue = new LinkedList();
		threads = new PoolWorker[nThreads];

		for (int i = 0; i < nThreads; i++) {
			threads[i] = new PoolWorker(i);
			threads[i].start();
		}
	}

	public void execute(Runnable r) {
		synchronized (queue) {
			queue.addLast(r);
			queue.notify();
		}
	}

	class PoolWorker extends Thread {
		int i;

		PoolWorker(int i) {
			super("worker:" + String.valueOf(i));
		}

		public void run() {
			Runnable r;

			while (true) {
				synchronized (queue) {
					if (queue.isEmpty()) {
						try {
							queue.wait();
						} catch (InterruptedException ignored) {
						}
					}

					r = (Runnable) queue.removeFirst();
				}

				// If we don't catch RuntimeException,
				// the pool could leak threads
				try {
					new Thread(r).start();
					System.out.println("using  Thread "
							+ Thread.currentThread().getName()
							+ " to run Thread " + r.getClass());
				} catch (RuntimeException e) {
					System.out.println(e);
				}
			}
		}
	}

	public static void main(String args[]) {
		WorkQueue wq = new WorkQueue(3);

		for (int i = 0; i < 100; i++) {

			Client client = new Client(wq);
			Thread clientThread = new Thread(client);
			clientThread.start();
		}
	}

}

class Client implements Runnable {
	WorkQueue wq;

	public Client(WorkQueue wq) {
		super();
		this.wq = wq;
	}

	@Override
	public void run() {
		wq.execute(new Runnable() {
			public void run() {

				try {
					Thread.sleep(1000);
				} catch (Exception e) {
				}
				System.out.println("execute Thread: " + this.getClass());
			}

		});
	}

}
