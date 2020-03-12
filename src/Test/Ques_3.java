package Test;

/**
 * 线程抛出异常会释放锁吗？  会
 */
public class Ques_3 {
	
	synchronized void m() {
		System.out.println("m......start");
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
//		int i = 1/0;
		System.out.println("m......end");
	}
	
	synchronized void m1() {
		System.out.println("m1......");
	}
	
	public static void main(String[] args) {
		Ques_3 ques_1 = new Ques_3();
		
		new Thread(() -> ques_1.m()).start();
		new Thread(() -> ques_1.m1()).start();
		
	}
	
	

}
