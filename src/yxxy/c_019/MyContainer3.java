package yxxy.c_019;

import java.util.ArrayList;
import java.util.List;

/**
 * 面试题：
 * 实现一个容器，提供两个方法：add，size
 * 写两个线程，线程1添加10个元素到容器中，线程2对容器的元素个数进行监控，当个数到达了5时，线程2给一个提示并结束
 *
 * 线程二代码执行不了，list的修改对线程二不可见
 * 
 * 加volatile，通知线程2读取相应的值
 * 
 * 
 * 存在的问题点：
 * 				t2线程的死循环很浪费cpu，如果不用死循环，该如何做？
 * 
 * 		做法：使用wait和notify做到，wait释放锁，notify不会释放锁，
 * 			  需要注意的是，运用这种方法，必须要保证t2先执行，也就是先让t2监听才可以
 * 
 * 阅读如下代码，并分析输出结果
 * 可以看到输出结果并不是当size==5时t2退出，而是t1结束时t2才接收到通知后退出，想想为什么？
 * 
 * 		由于wait释放锁，notify不会释放锁，notify虽说唤醒了之前wait的线程，但是并没有释放那把锁，所以才会出现如上的问题
 */
public class MyContainer3 {
	
	volatile List<Object> list = new ArrayList<>();
	
	public void add(Object i) {
		list.add(i);
	}
	
	public int size() {
		return list.size();
	}
	
	public static void main(String[] args) {
		
		MyContainer3 c = new MyContainer3();
		
		//如果需要使用wait和notify方法时，必须将该对象锁定，否则不能使用该对象的wait和notify方法
		final Object lock = new Object();
		
		new Thread(() -> {
			synchronized(lock) {
				System.out.println("t2启动");
				if(c.size() != 5) {
					try {
						lock.wait();//会释放锁并等待
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				System.out.println("t2结束");
			}
		}, "t2").start();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		new Thread(() -> {
			System.out.println("t1启动");
			synchronized(lock) {
				for (int i = 0; i < 10; i++) {
					c.add(new Object());
					System.out.println(i);
					
					if(c.size() == 5) {
						lock.notify();//不会释放lock这把锁
					}
					
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}, "t1").start();
		
		
	}
}
