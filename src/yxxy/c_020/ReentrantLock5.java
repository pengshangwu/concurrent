package yxxy.c_020;

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
 * reentrantlock还可以指定为公平锁，谁等的时间越久谁就先获得那把锁
 * synchronized默认是非公平锁
 */
public class ReentrantLock5 extends Thread {
	
//	private ReentrantLock lock = new ReentrantLock();无参是属于非公平锁
	private ReentrantLock lock = new ReentrantLock(true); //属于公平锁
	
	public void run() {
		for (int i = 0; i < 100; i++) {
			lock.lock();
			try {
				System.out.println(Thread.currentThread().getName() + "获得锁");
			} finally {
				lock.unlock();
			}
		}
	}
	
	public static void main(String[] args) {
		ReentrantLock5 lock5 = new ReentrantLock5();
		new Thread(lock5).start();
		new Thread(lock5).start();
		new Thread(lock5).start();
	}
	
	
	
}

