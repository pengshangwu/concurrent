package yxxy.c_026;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 	线程池概念
 */
public class T05_ThreadPool {
	
	public static void main(String[] args) throws InterruptedException {
		// 起一个固定（fixed）的线程池，容量为5，相当于起了五个线程
		ExecutorService service = Executors.newFixedThreadPool(5);
		for (int i = 0; i < 6; i++) {
			service.execute(() -> {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName());
			});
		}
		/**
		 * [Running, pool size = 5, active threads = 5, queued tasks = 1, completed tasks = 0]
		 * 		pool size = 5          		线程池的大小
		 * 		active threads = 5			线程池中激活的线程数量
		 * 		queued tasks = 1			排队的任务数量
		 * 		completed tasks = 0			完成的任务数量
		 * 
		 * 		一个线程池维护两个队列：一个用来存放未完成的任务，一个用来维护已完成的任务
		 */
		System.out.println("1...." + service);
		
		service.shutdown();
		System.out.println("2...." + service.isTerminated());
		System.out.println("3...." + service.isShutdown());
		System.out.println("4...." + service);
		
		Thread.sleep(5000);
		System.out.println("5...." + service.isTerminated());
		System.out.println("6...." + service.isShutdown());
		System.out.println("7...." + service);
	}
}
