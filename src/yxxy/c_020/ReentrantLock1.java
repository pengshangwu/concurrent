package yxxy.c_020;

/**
 * reenteantlock用于替代synchronized
 * 本例中由于m1锁定this，只有m1执行完毕后才会执行m2
 * 这里复习synchronized最原始的语义
 * 
 */
public class ReentrantLock1 {
	
	synchronized void m1() {
		for (int i = 0; i < 10; i++) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(i);
		}
	}
	
	synchronized void m2() {
		System.out.println("m2....");
	}

	public static void main(String[] args) {
		ReentrantLock1 r1 = new ReentrantLock1();
		new Thread(() -> r1.m1()).start();
		new Thread(() -> r1.m2()).start();
	}
}

