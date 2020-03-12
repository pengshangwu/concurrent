package yxxy.c_007;

/**
 * 同步方法和非同步方法是否可以同时被调用
 * new Thread(() -> t.m1()).start();
 * new Thread(() -> t.m2()).start();
 * 第一个线程执行的时候，会获得当前对象的锁，并执行 m1 方法
 * 第二个线程执行m2方法时是不需要锁的
 */
public class T {
	
	public synchronized void m1() {
		System.out.println("m1.......start");
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}		
		System.out.println("m1.......end");
	}
	
	public void m2() {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}		
		System.out.println("m2.......");
	}
	
	public static void main(String[] args) {
		T t = new T();
		//java8的特有表达式，lambda表达式  new一个Runnable对象，在run方法里面执行t.m1() 和 t.m2()方法
		new Thread(() -> t.m1()).start();
		new Thread(() -> t.m2()).start();
		
//		new Thread(t::m1).start();
//		new Thread(t::m2).start();
		
		//以上两种写法是一致
		
		/*等同于如下
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				t.m1();
			}
		}).start();
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				t.m2();
			}
		}).start();
		*/
		
	}
	
	
	
}
