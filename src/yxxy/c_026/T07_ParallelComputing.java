package yxxy.c_026;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 	素数个数的计算，
 * 	
 * 	1、Future的用法及线程池的用法
 * 
 * 	2、newFixedThreadPool 固定个数线程的线程池
 * 
 * 	后续都是将线程池的用法，总共有6中线程池
 */
public class T07_ParallelComputing {
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		long start = System.currentTimeMillis();
		
		List<Integer> list = getPrime(1, 200000);
		
		int size = list.size();
		
		long end = System.currentTimeMillis();
		System.out.println("花费时间："+ (end - start) + " ,总的个数为：" + size);
		
		// 该电脑CPU是6核，故起6个线程数
		ExecutorService service = Executors.newFixedThreadPool(6);
		Future<List<Integer>> f1 = service.submit(new MyTask(1, 80000));
		Future<List<Integer>> f2 = service.submit(new MyTask(80001, 110000));
		Future<List<Integer>> f3 = service.submit(new MyTask(110001, 140000));
		Future<List<Integer>> f4 = service.submit(new MyTask(140001, 160000));
		Future<List<Integer>> f5 = service.submit(new MyTask(160001, 180000));
		Future<List<Integer>> f6 = service.submit(new MyTask(180001, 200000));
		
		long start1 = System.currentTimeMillis();
		
		List<Integer> list1 = f1.get();
		List<Integer> list2 = f2.get();
		List<Integer> list3 = f3.get();
		List<Integer> list4 = f4.get();
		List<Integer> list5 = f5.get();
		List<Integer> list6 = f6.get();
		
		int size1 = list1.size() + list2.size() + 
				list3.size() + list4.size() + list5.size() + list6.size();
		
		long end1 = System.currentTimeMillis();
		System.out.println("花费时间："+ (end1 - start1) + " ,总的个数为：" + size1);
		
	}
	
	static class MyTask implements Callable<List<Integer>> {
		
		private int start;
		private int end;
		
		public MyTask(int start, int end) {
			this.start = start;
			this.end = end;
		}
		
		public List<Integer> call() throws Exception {
			
			return getPrime(start, end);
		}
	}
	
	// 计算是否为质数
	static boolean isPrime(int num) {

		for (int i = 2; i <= num / 2; i++) {
			if(num % i == 0) return false; 
		}
		
		return true;
	}

	// 计算一定范围之内有多少个质数
	static List<Integer> getPrime(int start, int end) {
		
		List<Integer> list = new ArrayList<Integer>();
		for (int i = start; i <= end; i++) {
			if(isPrime(i)) {
				list.add(i);
			}
		}
		return list;
	}
}
