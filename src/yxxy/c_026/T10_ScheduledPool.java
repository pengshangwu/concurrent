package yxxy.c_026;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 	ScheduledPool
 * 		定时执行任务的线程池，线程执行完可以复用，如果不够则任务等待空闲的线程来执行
 */
public class T10_ScheduledPool {
	
	public static void main(String[] args) {
		
		ScheduledExecutorService service = Executors.newScheduledThreadPool(4);
		
		for (int i = 0; i < 5; i++) {
			final int j = i;
			service.scheduleAtFixedRate(new Runnable() {
				public void run() {
					System.out.println(Thread.currentThread().getName() + ".....start" + j);
					try {
						Thread.sleep(new Random().nextInt(100000000));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println(Thread.currentThread().getName() + ".....end" + j);
				}
			}, 0, 500, TimeUnit.MILLISECONDS);
		}
		
		
		
		
		
		
		
		
	}
}
