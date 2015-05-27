package com.train;

import net.sourceforge.groboutils.junit.v1.MultiThreadedTestRunner;
import net.sourceforge.groboutils.junit.v1.TestRunnable;

public class MutiTestRunnable extends TestRunnable {
	Runnable runnable;
	
	public MutiTestRunnable(Runnable runnable) {
		this.runnable = runnable;
	}

	@Override
	public void runTest() throws Throwable {
//		if (runnable instanceof Thread) {
//			((Thread) runnable).start();
//		} else
//			new Thread(runnable).start();
		runnable.run();
	}

	public static void Execute(Runnable[] runnableArr) {
		// Rnner数组，想当于并发多少个。
		TestRunnable[] trs = new TestRunnable[runnableArr.length];
		for (int i = 0; i < runnableArr.length; i++) {
			trs[i] = new MutiTestRunnable(runnableArr[i]);
		}
		// 用于执行多线程测试用例的Runner，将前面定义的单个Runner组成的数组传入
		MultiThreadedTestRunner mttr = new MultiThreadedTestRunner(trs);
		try {
			// 开发并发执行数组里定义的内容
			mttr.runTestRunnables();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public Runnable getRunnable() {
		return runnable;
	}

	public void setRunnable(Runnable runnable) {
		this.runnable = runnable;
	}

}
