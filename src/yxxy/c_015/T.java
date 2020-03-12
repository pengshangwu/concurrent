package yxxy.c_015;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 解决同样问题更高效的方法，使用Atomxxx类
 * Atomxxx类本身方法都是原子性，但不能保证多个方法连续调用是原子性
 */
public class T {
	
	/* public volatile int count = 0; */
	
	AtomicInteger count = new AtomicInteger(0);
	
	public /* synchronized */ void m() {
		for (int i = 0; i < 10000; i++) {
			count.incrementAndGet();//原子方法，替代count++
		}
	}
	
	public static void main(String[] args) {
		
		T t = new T();
		
		List<Thread> threads = new ArrayList<>();
		
		//创建10个线程
		for (int i = 0; i < 10; i++) {
			threads.add(new Thread(() -> t.m(), "thread-" + i));
		}
		
		//10个线程启动
		threads.forEach((thread) -> thread.start());
		
		threads.forEach((o) -> {
			try {
				o.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		
		System.out.println(t.count);
		
	}
	
}

