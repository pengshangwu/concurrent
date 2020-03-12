package yxxy.c_023;

/**
 * 线程安全的单例模式
 * 
 * 如下采用获取的单例既不用加锁也能实现懒加载
 *
 */
public class Singleton {
	
	private Singleton() {}
	
	private static class inner{
		private static Singleton s = new Singleton();
	}
	
	public static Singleton getSingle() {
		return inner.s;
	}
	
	//最终获取到是同一个对象
	public static void main(String[] args) {
		
		new Thread(() -> {
			System.out.println(Singleton.getSingle());
		}).start();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		new Thread(() -> {
			System.out.println(Singleton.getSingle());
		}).start();
		
	}
}
