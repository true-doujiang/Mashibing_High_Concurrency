package com.mark.concurrent26;

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * ForkJoin非常经典的算法
 * @author 18009
 *
 */
public class T12_ForkJoinPool {
	static int[] nums = new int[1000000];
	static final int MAX_NUM = 50000;
	static Random r = new Random();
	
	static {
		for (int i=0; i<nums.length; i++) {
			nums[i] = r.nextInt(100);
		}
		System.out.println(Arrays.stream(nums).sum());
	}
	
	static class AddTask extends RecursiveAction {

		int start, end;
		
		public AddTask(int start, int end) {
			this.start = start;
			this.end = end;
		}

		@Override
		protected void compute() {
			if (end - start <= MAX_NUM) {
				long sum = 0L;
				for(int i=start; i<end; i++) {
					sum += nums[i];
				}
				System.out.println("from:" + start + " to:" + end + "=" + sum);
			} else {
				int middle = start + (end - start) / 2;
				
				AddTask subTask1 = new AddTask(start, middle);
				AddTask subTask2 = new AddTask(middle, end);
				subTask1.fork();
				subTask2.fork();
			}
		}
		
	}
	
//	static class AddTask extends RecursiveTask<Long> {
//
//		int start, end;
//		
//		public AddTask(int start, int end) {
//			this.start = start;
//			this.end = end;
//		}
//
//		@Override
//		protected Long compute() {
//			if (end - start <= MAX_NUM) {
//				long sum = 0L;
//				for(int i=start; i<end; i++) {
//					sum += nums[i];
//				}
//				return sum;
//			}
//			
//			int middle = start + (end - start) / 2;
//			
//			AddTask subTask1 = new AddTask(start, middle);
//			AddTask subTask2 = new AddTask(middle, end);
//			subTask1.fork();
//			subTask2.fork();
//			
//			return subTask1.join() + subTask2.join();
//		}
//		
//	}
	
	public static void main(String[] args) throws InterruptedException, IOException {
		ForkJoinPool fjp = new ForkJoinPool();
		AddTask task = new AddTask(0, nums.length);
		fjp.execute(task);
		// long result = task.join();
		// System.out.println(result);
		
		System.in.read();
	}
}