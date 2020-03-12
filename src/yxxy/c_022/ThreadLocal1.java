package yxxy.c_022;

/**
 * ThreadLocal线程局部变量
 */
public class ThreadLocal1 {
	
	volatile static Person p = new Person();
	
	public static void main(String[] args) {
		
		new Thread(() -> {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(p.name);
		}).start();
		
		new Thread(() -> {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			p.name = "lisi";
		}).start();
	}
}
