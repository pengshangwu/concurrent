package Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 写一个程序，证明AtomXXX类比synchronized更高效
 */
public class Ques_5 {
	
	int count = 0;
	AtomicInteger atomicInteger = new AtomicInteger(0);
	
	synchronized void m1() {
		for (int i = 0; i < 10000; i++) {
			count++;
		}
	}
	
	void m2() {
		for (int i = 0; i < 10000; i++) {
			atomicInteger.incrementAndGet();
		}
	}
	
	public void syn(Ques_5 ques_5) {
		
		long start = System.currentTimeMillis();
		
		List<Thread> threads = new ArrayList<>();
		
		for (int i = 0; i < 10; i++) {
			threads.add(new Thread(() -> ques_5.m1(), "thread-" + i));
		}
		
		threads.forEach((o) -> o.start());
		
		threads.forEach((o) -> {
			try {
				o.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		
		System.out.println(ques_5.count);
		
		System.out.println(System.currentTimeMillis() - start);
	}
	
	public void atom(Ques_5 ques_5) {
		
		long start = System.currentTimeMillis();
		
		List<Thread> threads = new ArrayList<>();
		
		for (int i = 0; i < 10; i++) {
			threads.add(new Thread(() -> ques_5.m2(), "thread-" + i));
		}
		
		threads.forEach((o) -> o.start());
		
		threads.forEach((o) -> {
			try {
				o.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		
		System.out.println(ques_5.atomicInteger.get());
		
		System.out.println(System.currentTimeMillis() - start);
	}
	
	
	
	public static void main(String[] args) {
		
		Ques_5 ques_5 = new Ques_5();
		ques_5.syn(ques_5);
		ques_5.atom(ques_5);
	}

}
