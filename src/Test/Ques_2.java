package Test;

/**
 * 同上，B线程是否可以同时执行同一个对象中的另一个同步方法？ 不可以
 */
public class Ques_2 {
	
	synchronized void m() {
		System.out.println("m......start");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("m......end");
	}
	
	synchronized void m1() {
		System.out.println("m1......");
	}
	
	public static void main(String[] args) {
		Ques_2 ques_1 = new Ques_2();
		
		new Thread(() -> ques_1.m()).start();
		new Thread(() -> ques_1.m1()).start();
		
	}
	
	

}
