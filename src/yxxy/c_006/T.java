package yxxy.c_006;

/**
 * 加与不加synchronized输出的结果
 * 
 * 		synchronized代码块是属于原子操作，不可分
 */
public class T implements Runnable{
	
	private int count = 0;

	public synchronized void run() {
		
		count++;
		
		System.out.println(Thread.currentThread().getName() + ", count: " + count);
		
	}
	
	public static void main(String[] args) {
		
		T t = new T();
		
		for (int i = 0; i < 5; i++) {
			
			new Thread(t, "Thread-" + i).start();
			
		}
	}
}
