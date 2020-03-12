package yxxy.c_026;

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

/**
 * 	ForkJoinPool
 * 	先分fork叉拆分成一个个小小的模块，然后join汇总成最终的结果
 */
public class T12_ForkJoinPool {
	
	static int[] nums = new int[1000000];
	static final int MAX_NUM = 50000;
	
	static Random r = new Random();
	
	static {
		
		// 传统的累加方式
		for (int i = 0; i < nums.length; i++) {
			nums[i] = r.nextInt(100);
		}
		
		System.out.println(Arrays.stream(nums).sum());
	}
	
	public static void main(String[] args) throws IOException {
		
		// Runtime.getRuntime().availableProcessors()  6核---6线程数（默认）
		ForkJoinPool forkJoinPool = new ForkJoinPool();
		
//		forkJoinPool.execute(new MyRecursiveAction(0, nums.length));
		
		MyRecursiveTask myRecursiveTask = new MyRecursiveTask(0, nums.length);
		forkJoinPool.execute(myRecursiveTask);
		Long result = myRecursiveTask.join();
		System.out.println(result);
		
		System.in.read();
		
	}
	
	static class MyRecursiveAction extends RecursiveAction {

		private static final long serialVersionUID = 1L;
		int start;
		int end;

		public MyRecursiveAction(int start, int end) {
			this.start = start;
			this.end = end;
		}

		@Override
		protected void compute() {
			if((end - start) <= MAX_NUM) {
				long sum = 0;
				for (int i = start; i < end ; i++) {
					sum += nums[i];
				}
				System.out.println("开始：" + start + ",结束：" + end + ",总数：" + sum);
			}else {
				int middle = start + (end - start) / 2;
				
				//其实就是采用递归的方式
				MyRecursiveAction action1 = new MyRecursiveAction(start, middle);
				MyRecursiveAction action2 = new MyRecursiveAction(middle, end);
				
				action1.fork();
				action2.fork();
				
			}
		}
	}
	
	static class MyRecursiveTask extends RecursiveTask<Long> {

		private static final long serialVersionUID = 1L;
		int start;
		int end;

		public MyRecursiveTask(int start, int end) {
			this.start = start;
			this.end = end;
		}

		@Override
		protected Long compute() {
			if((end - start) <= MAX_NUM) {
				long sum = 0;
				for (int i = start; i < end ; i++) {
					sum += nums[i];
				}
				return sum;
			}
			int middle = start + (end - start) / 2;
			
			//其实就是采用递归的方式
			MyRecursiveTask action1 = new MyRecursiveTask(start, middle);
			MyRecursiveTask action2 = new MyRecursiveTask(middle, end);
			
			action1.fork();
			action2.fork();
				
			return action1.join() + action2.join();
		}
	}
}
