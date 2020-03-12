package yxxy.c_004;

/**
 * synchronized关键字
 *	对某个对象进行加锁，锁的是堆内存中的对象（new Object()），而不是引用（o）
 *	锁的信息是记录在堆内存对象里，如果此时o指向了另一个对象的话，那么锁的对象就会跟着变
 *
 *	当锁定的是静态方法时，其实锁定就是当前的类对象
 */
public class T {
	
	private static int count = 10;
	
	public static synchronized void m() {//和synchronized (T.class)一样
		count--;
		System.out.println(Thread.currentThread().getName() + " count = " + count);
	}

	//此处是不能用synchronized(this)，由于static是属于类对象范围
	public static void mm() {
		synchronized (T.class) {
			count--;
		}
		
	}
}
