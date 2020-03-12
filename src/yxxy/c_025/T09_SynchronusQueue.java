package yxxy.c_025;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

/**
 * 	容量为0的队列，意思是说，生产的东西必须要消费者立马消费，否则会出异常
 * 	put添加方法内部使用了TransferQueue，生产线程必须要消费者来消费，否则就阻塞等待
 * 	由于容量为0，所以add方法添加就会报错
 */
public class T09_SynchronusQueue { // 

	public static void main(String[] args) throws InterruptedException {
		BlockingQueue<String> str = new SynchronousQueue<>();
		
		new Thread(() -> {
			try {
				System.out.println(str.take());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();
		
		str.put("aaa");
//		str.add("bbb");
		
		System.out.println(str);
		
		
		
	}
	

}
