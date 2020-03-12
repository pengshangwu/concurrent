package yxxy.c_026;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 	WorkStealingPool
 * 		线程执行完，会去窃取其它线程队列中的任务来执行，主动去拿任务执行
 * 		本质是采用ForkJoinPool来实现的
 *
 */
public class T11_WorkStealingPool {
	
	public static void main(String[] args) throws IOException {
		
		// 默认起的线程数是根据自身的cpu的核数来起的 Runtime.getRuntime().availableProcessors()
		ExecutorService service = Executors.newWorkStealingPool();
		
		service.execute(new R(1500));
		service.execute(new R(2000));
		service.execute(new R(3000));
		service.execute(new R(3000));
		service.execute(new R(3000));
		service.execute(new R(3000));
		//查看如下两个是哪个线程得到的
		service.execute(new R(3000));
		service.execute(new R(3000));
		
		
		// 由于产生的是精灵线程(后台线程，守护线程)，主线程不阻塞的话，将看不到精灵线程的输出
		System.in.read();
		
	}
	
	static class R implements Runnable {
		
		int time;
		
		public R(int time) {
			this.time = time;
		}
		
		@Override
		public void run() {
			try {
				Thread.sleep(time);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName());
		}
	}
}
