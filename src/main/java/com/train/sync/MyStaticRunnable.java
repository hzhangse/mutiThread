package com.train.sync;

class MyStaticRunnable implements Runnable {
	// private Foo foo = new Foo();

	public void run() {
		for (int i = 0; i < 3; i++) {
			this.fix(i);
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}

	public void fix(int y) {
		if (Thread.currentThread().getName().equalsIgnoreCase("Thread-A")) {
			Foo.syncMethod2(y);
			
		} else {
			Foo.syncMethod1(y);
		}

	}
}
