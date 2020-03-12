package yxxy.c_026;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * 	我们通常都是开启一个新的子线程去执行比较耗时的代码，这使用起来非常简单，
 * 	只需要将耗时的代码封装在Runnable中的run()方法里面，然后调用thread.start()就行。
 * 	但是我相信很多人有时候都有这样的需求，就是获取子线程运行的结果，比如客户端远程调用服务（耗时服务），
 * 	我们有需要得到该调用服务返回的结果，这该怎么办呢？
 * 	很显然子线程运行的run()方法是没有返回值。这个时候Future的作用就发挥出来了。
 *
 */
public class T06_Future {

	public static void main(String[] args) throws Exception {
		
		FutureTask<Integer> task = new FutureTask<>(() -> {
			Thread.sleep(1000);
			return 1000;
		});
		
		new Thread(task).start();
		
		System.out.println(task.get()); // 阻塞方法
		
		// ====================================
		
		ExecutorService service = Executors.newFixedThreadPool(6);
		
		Future<Integer> future = service.submit(() -> {
			Thread.sleep(1000);
			return 1;
		});
		
		System.out.println(future.get());
		System.out.println(future.isDone());  // 如果在该方法之前没有阻塞的话，肯定是没有执行完
		
	}

}
