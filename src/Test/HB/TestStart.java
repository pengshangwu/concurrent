package Test.HB;

public class TestStart {
	
	static int a = 0;

	public static void main(String[] args) {
		
		Thread t = new Thread(() -> {
			System.out.println(a);
		});
		
		a = 10;
		
		t.start();
		
	}
}
