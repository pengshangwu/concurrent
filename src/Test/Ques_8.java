package Test;

public class Ques_8 {
	
	private Object o1 = new Object();
	private Object o2 = new Object();
	
	public void m1() {
		synchronized(o1) {
			System.out.println(Thread.currentThread().getName() + ", m1......start");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			m2();
			System.out.println(Thread.currentThread().getName() + ", m1......end");
		}
	}
	
	public void m2() {
		synchronized(o2) {
			System.out.println(Thread.currentThread().getName() + ", m2......start");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			m1();
			System.out.println(Thread.currentThread().getName() + ", m2......end");
		}
	}
	
	public static void main(String[] args) {
		Ques_8 lock = new Ques_8();
		
		new Thread(() -> lock.m1(), "t1").start();
		new Thread(() -> lock.m2(), "t2").start();
		
	}
}
