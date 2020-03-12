package yxxy.c_002;

/**
 * synchronized关键字
 *	对某个对象进行加锁，锁的是堆内存中的对象（new Object()），而不是引用（o）
 *	锁的信息是记录在堆内存对象里，如果此时o指向了另一个对象的话，那么锁的对象就会跟着变
 */
public class T {
	
	private int count = 10;
	
	public void m() {
		synchronized (this) {//相比上一个不用直接new一个对象用来当锁，直接锁定当前对象
			count--;
			System.out.println(Thread.currentThread().getName() + " count = " + count);
		}
	}
}
