package yxxy.c_009;

/**
 * 一个同步方法可以调用另外一个同步方法，一个线程已经拥有了某个对象的锁，再次申请的时候仍然会得到该对象的锁
 * 也就是synchronized获得的锁可以重入
 */
public class T {
	
	public synchronized void m1() {
		System.out.println("m1......");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		m2();
	}

	public synchronized void m2() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName() + ", m2......");
	}
	
	public static void main(String[] args) {
		T t = new T();
		new Thread(() -> t.m1(), "t1").start();
		new Thread(() -> t.m2(), "t2").start();
	}
}
