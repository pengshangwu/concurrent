package yxxy.c_008;

/**
 * 对业务写方法加锁
 * 对业务读方法不加锁
 * 容易产生脏读问题
 */
public class Account {
	
	String name;
	double balance;
	
	public synchronized void set(String name, double balance) {
		this.name = name;
		//Thread.sleep(2000);模拟脏读现象
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.balance = balance;
	}
	
	public /* synchronized */ double getBalance(String name) {
		return this.balance;
	}
	
	public static void main(String[] args) {
		Account account = new Account();
		new Thread(() -> account.set("张三", 100.0)).start();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("第一次获取值：" + account.getBalance("张三"));
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("第二次获取值：" + account.getBalance("张三"));
	}
}
