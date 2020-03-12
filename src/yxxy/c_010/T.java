package yxxy.c_010;

/**
 * 一个同步方法可以调用另外一个同步方法，一个线程已经拥有了某个对象的锁，再次申请的时候仍然会得到该对象的锁
 * 也就是synchronized获得的锁可以重入
 * 这里是继承中可能发生的情形，子类调用父类的同步方法
 */
public class T {

	public synchronized void m() {
		System.out.println(Thread.currentThread().getName() + ", T...m...start");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName() + ", T...m.end");
	}
	
	public static void main(String[] args) {
		
		TT tt = new TT();
		
		new Thread(() -> tt.m(), "tt1").start();
		new Thread(() -> tt.m(), "tt2").start();
	}
}

class TT extends T{
	
	@Override
	public synchronized void m() {
		System.out.println(Thread.currentThread().getName() + ", TT...m...start");
		super.m();
		System.out.println("TT...m...end");
	}
	
}
