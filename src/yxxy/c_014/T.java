package yxxy.c_014;

import java.util.ArrayList;
import java.util.List;

/**
 * 对比上一个程序，可以用synchronized解决，synchronized可以保证原子性和可见性，volatile只能保证可见性，
 * 但是synchronized的效率比volatile要低很多
 */
public class T {
	
	public /* volatile */ int count = 0;
	
	public synchronized void m() {
		for (int i = 0; i < 10000; i++) {
			count++;
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

