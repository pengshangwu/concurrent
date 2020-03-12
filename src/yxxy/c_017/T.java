package yxxy.c_017;

/**
 * 锁定某对象o，如果o对象属性发生改变，不影响锁的使用
 * 但是o如果变成了另外一个对象，则锁的对象就会发生变化
 * 应该避免该问题的出现
 * 
 * 
 * 如下更能证明synchronized锁的是堆内存中的对象，而不是引用
 * 		t1锁定对象的引用o虽说指向另一个对象，但不影响t1执行锁定的代码
 * 		t1，t2都能执行
 * 
 * 总结：synchronized锁定的是堆内存中对象而不是引用o
 */
public class T {
	
	Object o = new Object();
	
	public void m() {
		synchronized(o) {
			while(true) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName());
			}
		}
	}
	
	public static void main(String[] args) {
		T t = new T();
		
		new Thread(() -> t.m(), "t1").start();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		t.o = new Object();
		
		new Thread(() -> t.m(), "t2").start();
	}
}

