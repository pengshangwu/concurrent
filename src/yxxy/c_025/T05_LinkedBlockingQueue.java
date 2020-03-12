package yxxy.c_025;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 和之前的消费者生产者一个道理
 * LinkedBlockingQueue是一个无界队列，也就是如果没有指定队列的大小时，就是一个无界队列，直到把内存撑满为止
 */
public class T05_LinkedBlockingQueue {

	// LinkedBlockingQueue默认的大小为 Integer.MAX_VALUE
	static BlockingQueue<String> strs = new LinkedBlockingQueue<>();

	public static void main(String[] args) {

		Random random = new Random();

		new Thread(() -> {
			for (int i = 0; i < 100; i++) {
				try {
					strs.put("a" + i);// 如果满了就会等待
					Thread.sleep(random.nextInt(1000));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}, "p1").start();

		for (int i = 0; i < 5; i++) {
			new Thread(() -> {
				for(;;) {
					try {
						// 如果空了就会等待
						System.out.println(Thread.currentThread().getName() + " take " + strs.take()); 
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}, "c" + i).start();

		}

	}

}
