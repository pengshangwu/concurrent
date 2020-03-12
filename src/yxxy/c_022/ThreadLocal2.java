package yxxy.c_022;

/**
 * ThreadLocal是使用空间换时间，synchronized是时间换空间
 * 
 * 运行下面的代码，理解ThreadLocal（线程本地变量）
 * ThreadLocal是每个线程自身的变量，修改了值不影响其他的线程使用
 * 
 */
public class ThreadLocal2 {
	
	static ThreadLocal<Person> tl = new ThreadLocal<>();
	
	public static void main(String[] args) {
		
		new Thread(() -> {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("t1----" + tl.get());
		}).start();
		
		
		new Thread(() -> {
			tl.set(new Person());
			
			tl.get().name = "t2";
			
			System.out.println("t2----" + tl.get().name);
			
		}).start();
		
	}
}


