package yxxy.c_026;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * newCachedThreadPool
 * 		特点：1、当有任务来，如果线程池中没有线程或者没有空闲的线程，则会起一个线程来执行该任务
 * 			  2、如果已执行完任务的线程在60s（默认时间，可以指定）内没有执行任务，则会自动销毁
 *
 */
public class T08_CachedPool {
	
	public static void main(String[] args) throws InterruptedException {
		
		ExecutorService service = Executors.newCachedThreadPool();
		
		System.out.println(service);
		
		
		for (int i = 0; i < 2; i++) {
			service.execute(() -> {
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName());
			});
		}
		
		System.out.println(service);
		
		// 睡了80s
		Thread.sleep(80000);
		
		System.out.println(service);
		
	}


}
