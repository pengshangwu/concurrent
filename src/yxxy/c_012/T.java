package yxxy.c_012;

/**
 * volatile关键字，使一个变量在多个线程间可见
 * A B线程都用到了一个变量，java默认是A线程中保留一个copy，这样如果线程B修改了变量，则线程A未必知道该变量已变化
 * 使用volatile关键字，会让所有线程都会读取到变量的修改值
 * 
 * 在下面代码中，running是存在于堆内存的t对象中
 * 当线程t1开始运行的时候，会把running值从内存中读取到t1线程的工作区中，在运行过程中直接使用这个copy，并不会每次都去
 * 读取堆内存，这样，当主线程修改running的值后，t1线程感知不到，所以不会停止运行
 * 
 * 使用volatile，将会强制所有线程去堆内存中读取running的值
 * 
 * 可以阅读这篇文章进行更深入的理解
 * http://www.cnblogs.com/nexiyi/p/java_memory_model_and_thread.html
 * 
 * volatile并不能保证多个线程共同修改running变量时所带来的不一致问题，也就是说volatile不能替代synchronized
 * synchronized即保证了原子性，也保证了可见性
 * volatile只保证了可见性
 */
public class T {
	
	public /* volatile */ boolean running = true;
	
	public void m() {
		System.out.println(Thread.currentThread().getName() + ", start");
		while(running) {
			//如下线程睡了一段时间，cpu可能会抽出时间去内存中读取对应的值，
//			try {
//				Thread.sleep(500);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
		}
		System.out.println(Thread.currentThread().getName() + ", end");
	}
	
	public static void main(String[] args) {
		
		T t = new T();
		
		new Thread(() -> t.m(), "t1").start();
		
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//主线程 main 修改running值
		t.running = false;
		
	}
	
}

