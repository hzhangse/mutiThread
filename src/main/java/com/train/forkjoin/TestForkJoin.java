
package com.train.forkjoin;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

import org.junit.Test;

public class TestForkJoin {
	@Test
	public void run() throws Exception {
		ForkJoinPool forkJoinPool = new ForkJoinPool();
		Future<Integer> result = forkJoinPool.submit(new Calculator(0, 10000));
		System.out.println(result.get());
		//assertEquals(new Integer(49995000), result.get());
		int sum = 0;
		for (int i=0;i<=10000;i++){
			sum= sum + i;
		}
		System.out.print(sum);
	}
}

class Calculator extends RecursiveTask<Integer> {
	
	private static final int	THRESHOLD	= 100;
	private int	         start;
	private int	         end;
	
	public Calculator(int start, int end) {
		this.start = start;
		this.end = end;
	}
	
	@Override
	protected Integer compute() {
		int sum = 0;
		if ((end - start) < THRESHOLD) {
			for (int i = start; i <=end; i++) {
				sum += i;
			}
			System.out.println("compute start:"+start +" end:"+end +" result:"+sum);
		} else {
			int middle = (start + end) / 2;
			Calculator left = new Calculator(start, middle);
			Calculator right = new Calculator(middle + 1, end);
			left.fork();
			right.fork();
			
			sum = left.join() + right.join();
		}
		return sum;
	}
	
}