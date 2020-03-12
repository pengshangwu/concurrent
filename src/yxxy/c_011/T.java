package yxxy.c_011;

/**
 * 程序在执行过程中，如果出现异常，默认情况锁会被释放
 * 所以，在并发处理过程中，有异常多加小心，不然可能会发生不一致情况
 * 比如，在一个web app处理过程中，多个servlet线程共同访问一个资源，这是如果处理不合适的话，
 * 在第一个线程中抛出异常，其他线程就会进入同步代码区，有可能会访问到异常产生时的数据
 * 因此就要非常小心处理同步业务逻辑中的异常
 * 如果不想释放锁，则对锁进行try...catch...
 * 
 * 如果不抛出异常的则t2线程不会执行，否则就会执行
 */
public class T {
	
	int count = 0;
	
	public synchronized void m() {
		System.out.println(Thread.currentThread().getName() + ", t...start");
		while(count < 10) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			count++;
//			if(count == 5) {
//				int i = 1/0;
//			}
			System.out.println(count);
		}
	}

	public static void main(String[] args) {
		
		T t = new T();
		
		new Thread(() -> t.m(), "t1").start();
		new Thread(() -> t.m(), "t2").start();
		
	}
}

