package yxxy.c_001;

/**
 * synchronized关键字（互斥锁）
 *	对某个对象进行加锁，锁的是堆内存中的对象（new Object()），而不是引用（o），也不是代码
 *	锁的信息是记录在堆内存对象里，如果此时o指向了另一个对象的话，那么锁的对象就会跟着变
 */
public class T {
	
	private int count = 10;
	
	private Object o = new Object();
	
	public void m() {
		synchronized (o) {
			count--;
			System.out.println(Thread.currentThread().getName() + " count = " + count);
		}
	}
}
