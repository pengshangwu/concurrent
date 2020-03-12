package Test.HB;

public class TestJoin {
	
	static int a = 0;
	
	public static void main(String[] args) throws InterruptedException {
		
		Thread t = new Thread(() -> {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			a = 12;
		});
		
		t.start();
		t.join();
		System.out.println(a);
	}
}
