package com.train.sync;

class MyRunnable implements Runnable {
	private Foo foo = new Foo();

	public void run() {
		for (int i = 0; i < 3; i++) {
			this.fix(30);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}

	public  int  fix(int y) {
		
		return foo.fix(y);
	}
}
