package yxxy.c_020;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * reentrantlock用于替代synchronized
 * 本例中由于m1锁定this，只有m1执行完毕后才会执行m2
 * 这里复习synchronized最原始的语义
 * 
 * 使用reentrantlock可以完成同样的功能
 * 需要注意的是，必须要手动释放锁
 * 使用synchronized锁定的话如果遇到异常，jvm自动释放锁，但是lock必须手动释放锁，因此经常在finally中进行锁的释放
 * 
 * 使用reentrantlock可以进行"尝试锁定"tryLock，这样就无法锁定，或者在指定时间内无法锁定，线程可以决定是否继续等待
 * 
 * 使用reentrantlock还可以调用lockInterruptibly方法，可以对线程interrupt方法做出响应，
 * 在一个线程等待锁的过程中，可以被打断
 * 
 */
public class ReentrantLock4 {
	
	public static void main(String[] args) {
		
		Lock lock = new ReentrantLock();
		
		new Thread(() -> {
			
			lock.lock();
			System.out.println("t1...start");
			try {
				Thread.sleep(Integer.MAX_VALUE);
//				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
			}
			System.out.println("t1...end");
		}, "t1") .start();
		
		
		Thread t2 = new Thread(() -> {
			
			try {
				lock.lockInterruptibly();  //和线程自己的interrupt相呼应，指定时间未得到锁，直接中断等待
				System.out.println("t2...start");
				Thread.sleep(5000);
				System.out.println("t2...end");
			} catch (InterruptedException e) {
				e.printStackTrace();
				System.out.println("t2.....interrupt");
			} finally {
				lock.unlock();
			}
		}, "t2");
		
		t2.start();
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		t2.interrupt();
	}
}

