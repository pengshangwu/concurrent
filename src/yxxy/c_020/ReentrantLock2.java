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
 */
public class ReentrantLock2 {
	
	Lock lock = new ReentrantLock();
	
	void m1() {
		try {
			lock.lock();
			for (int i = 0; i < 10; i++) {
				Thread.sleep(1000);
				System.out.println(i);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}
	
	void m2() {
		lock.lock();
		System.out.println("m2....");
		lock.unlock();
	}

	public static void main(String[] args) {
		ReentrantLock2 r1 = new ReentrantLock2();
		new Thread(() -> r1.m1()).start();
		new Thread(() -> r1.m2()).start();
	}
}

