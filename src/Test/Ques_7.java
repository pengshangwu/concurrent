package Test;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 写一个程序证明AtomXXX类的多个方法并不构成原子性
 */
public class Ques_7 {
	
	AtomicInteger atomicInteger = new AtomicInteger(0);
	
	void m() {
		for (int i = 0; i < 5; i++) {
			
			atomicInteger.incrementAndGet();
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			System.out.println(atomicInteger.get());
			
		}
	}
	
	public static void main(String[] args) {
		Ques_7 ques_7 = new Ques_7();
		
		new Thread(() -> ques_7.m()).start();
		new Thread(() -> ques_7.m()).start();
		
	}
}
