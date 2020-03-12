package yxxy.c_018;

/**
 * 不要以字符串常量作为锁定对象
 * 在如下中，m1和m2其实锁定的是同一个对象
 * 这种情况还会发生诡异的现象，比如你用到一个类库，在该类库中代码锁定了字符串“hello”
 * 但是你都不到源码，所以你在自己的代码中也锁定了“hello”，这时候就有可能发生非常诡异的死锁阻塞
 * 因为你的代码和你用到的类库不经意间使用了同一把锁
 */
public class T {
	
	//hello其实就是同一个对象，因为是一个常量，堆内存中只保存一份
	String s1 = "hello";
	String s2 = "hello";
	
	void m1() {
		synchronized(s1) {
			while(true) {
				System.out.println(Thread.currentThread().getName());
			}
		}
	}
	
	void m2() {
		synchronized(s2) {
			while(true) {
				System.out.println(Thread.currentThread().getName());
			}
		}
	}
	
	public static void main(String[] args) {
		
		T t = new T();
		
		new Thread(() -> t.m1(), "t1").start();
		new Thread(() -> t.m2(), "t2").start();
	}
}

