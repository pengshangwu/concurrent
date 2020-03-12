package Test;

/**
 * A线程正在执行一个对象中的同步方法，B线程是否可以同时执行同一个对象中的非同步方法？ 可以
 */
public class Ques_1 {
	
	synchronized void m() {
		System.out.println("m......start");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("m......end");
	}
	
	void m1() {
		System.out.println("m1......");
	}
	
	public static void main(String[] args) {
		Ques_1 ques_1 = new Ques_1();
		
		new Thread(() -> ques_1.m()).start();
		new Thread(() -> ques_1.m1()).start();
		
	}
	
	

}
