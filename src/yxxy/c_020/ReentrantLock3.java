package yxxy.c_020;

import java.util.concurrent.TimeUnit;
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
 */
public class ReentrantLock3 {
	
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
	
	/**
	 * 使用trylock进行尝试锁定，不管锁定与否，方法都将继续执行
	 * 可以根据trylock的返回值来判定是否锁定
	 * 也可以指定trylock的时间，由于trylock(time)抛出异常，所以要注意unlock的处理，必须放到finally里
	 */
	void m2() {
		/*
		boolean locked = lock.tryLock();
		System.out.println("m2....");
		if(locked) lock.unlock();
		*/
		
		boolean locked = false;
		try {
			//返回的布尔值来表示是否拿到了锁
//			locked = lock.tryLock(5, TimeUnit.SECONDS); //5s后还没拿到锁就直接执行下面的代码
			locked = lock.tryLock(50, TimeUnit.SECONDS);
			if(locked) {
				System.out.println("m2....拿到了锁");
			}else {
				System.out.println("m2....没有拿到了锁");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(locked) lock.unlock();
		}
	}

	public static void main(String[] args) {
		ReentrantLock3 r1 = new ReentrantLock3();
		new Thread(() -> r1.m1()).start();
		new Thread(() -> r1.m2()).start();
	}
}

