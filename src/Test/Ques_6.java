package Test;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * AtomXXX类可以保证可见性吗？请写一个程序来证明  可以保证可见性
 */
public class Ques_6 {
	
	AtomicBoolean atomicBoolean = new AtomicBoolean(true);
	
	void m() {
		System.out.println("m......start");
		while(atomicBoolean.get()) {
			
		}
		System.out.println("m......end");
	}
	
	public static void main(String[] args) {
		
		Ques_6 ques_6 = new Ques_6();
		
		new Thread(() -> ques_6.m()).start();
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		ques_6.atomicBoolean.set(false);
		
	}
}
