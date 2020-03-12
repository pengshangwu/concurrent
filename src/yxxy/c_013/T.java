package yxxy.c_013;

import java.util.ArrayList;
import java.util.List;

/**
 * volatile并不能保证多个线程共同修改变量所带来的不一致问题，也就是说volatile不能替代synchronized
 * 运行如下程序并分析结果
 */
public class T {
	
	public volatile int count = 0;
	
	public void m() {
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

