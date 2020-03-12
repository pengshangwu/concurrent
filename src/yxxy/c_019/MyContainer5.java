package yxxy.c_019;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

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
 * notify之后，t1必须释放锁，t2退出后，也必须notify，通知t1继续执行
 * 整个通信过程比较繁琐
 * 
 * 使用Latch（门闩）替代wait和notify来进行通知
 * 好处时通信方式简单，同时也可以指定等待时间
 * 使用await和countdown方法替代wait和notify
 * CountDownLatch不涉及锁定，当count的值为零时，会让调用了await线程全部启动继续运行
 * 当不涉及同步，只是涉及线程通信的时候，用synchronized+wait/notify就显得太重了
 * 这时应该考虑countdownlatch/cyclicharrier/semaphore
 */
public class MyContainer5 {
	
	volatile List<Object> list = new ArrayList<>();
	
	public void add(Object i) {
		list.add(i);
	}
	
	public int size() {
		return list.size();
	}
	
	public static void main(String[] args) {
		
		MyContainer5 c = new MyContainer5();
		
		CountDownLatch latch = new CountDownLatch(1);
		System.out.println("1-----" + Thread.currentThread().getName());
		
		new Thread(() -> {
			System.out.println("t2启动");
			System.out.println("2-----" + Thread.currentThread().getName());
			if(c.size() != 5) {
				try {
					
					latch.await();
					
					//也可以指定等待时间
//					latch.await(5000, TimeUnit.MILLISECONDS);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println("t2结束");
		}, "t2").start();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		new Thread(() -> {
			try {
				latch.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("3-----" + Thread.currentThread().getName());
		}, "t3").start();
		
		new Thread(() -> {
			System.out.println("t1启动");
			for (int i = 0; i < 10; i++) {
				c.add(new Object());
				System.out.println(i);
				
				if(c.size() == 5) {
					latch.countDown();//执行一次就减一，定义的时候CountDownLatch latch = new CountDownLatch(1);
				}
				
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}, "t1").start();
		
//		try {
//			latch.await();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		System.out.println("main......");
		
		
	}
}
