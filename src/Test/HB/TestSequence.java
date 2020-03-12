package Test.HB;

public class TestSequence {
	
	static int a = 0;
	static volatile boolean b = false;

	public static void main(String[] args) {
		
		new Thread(() -> {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			b = true;
			a = 40;
		}).start();
		
		new Thread(() -> {
			if(b) {
				System.out.println(a + "  " + b);
			}else {
				System.out.println(a + "  " + b);
			}
		}).start();
	}
}
