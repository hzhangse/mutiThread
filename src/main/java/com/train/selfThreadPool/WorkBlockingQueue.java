package com.train.selfThreadPool;
import java.util.concurrent.LinkedBlockingQueue;

public class WorkBlockingQueue {
	private  int nThreads ;
	private final PoolWorker[] threads;
	// private final LinkedList queue;
	final LinkedBlockingQueue<Thread> queue = new LinkedBlockingQueue<Thread>(
			100);

	public WorkBlockingQueue(int nThreads) {
		this.nThreads = nThreads;
		// queue = new LinkedList();
		threads = new PoolWorker[nThreads];

		for (int i = 0; i < nThreads; i++) {
			threads[i] = new PoolWorker(i);
			threads[i].start();
		}
	}

	public void execute(Thread r) {
		try {
			
			queue.put(r);
			System.out.println(" queue.put "
					+ r.getName());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}


	private class PoolWorker extends Thread {
		int i;

		PoolWorker(int i) {
			super("worker:" + String.valueOf(i));
		}

		public void run() {
			Thread r;

			while (true) {

				// If we don't catch RuntimeException,
				// the pool could leak threads
				try {
					r = queue.take();
					System.out.println(" queue.take "
							+ r.getName());
					r.start();
					System.out.println("this is a thread "
							+ Thread.currentThread().getName());
				} catch (RuntimeException e) {
					System.out.println(e);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static class BussinessThread  extends Thread{
		
		public BussinessThread(String i){
			super(i);
		}
		public void run() {

			try {
				Thread.sleep(1000);
			} catch (Exception e) {
			}
			System.out.println("this is a Bussiness Thread :" + this.getName());
		}
	}
	
	public static void main(String args[]) {
		WorkBlockingQueue wq = new WorkBlockingQueue(3);
		for (int i = 0; i < 10; i++) {
			wq.execute(new BussinessThread("Bussiness "+String.valueOf(i)));
		}
	}
}
