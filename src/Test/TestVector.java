package Test;

import java.util.Vector;

public class TestVector {

//	void addIfNotExist(Vector v, Object o) {
//		synchronized (v) {
//			if (!v.contains(o)) {
//				v.add(o);
//			}
//		}
//	}
	
	synchronized void addIfNotExist(Vector v, Object o) {
		if (!v.contains(o)) {
			v.add(o);
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		
		TestVector t = new TestVector();
		Vector v = new Vector<>(1);
		
		Object object = new Object();
		
		for (int i = 0; i < 10; i++) {
			new Thread(() -> {
				t.addIfNotExist(v, object);
			}).start();
		}
		
		Thread.sleep(5000);
		System.out.println(v.size());
		
		
		
	}

}
