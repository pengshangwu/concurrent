package Test;

/**
 * join的意思是使得放弃当前线程的执行，并返回对应的线程，例如下面代码的意思就是：
 * 程序在main线程中调用t1线程的join方法，则main线程放弃cpu控制权，并返回t1线程继续执行直到线程t1执行完毕
 * 所以结果是t1线程执行完后，才到主线程执行，相当于在main线程中同步t1线程，t1执行完了，main线程才有执行的机会
 * 
 * 
 * 
 * 总结：在A线程中调用了B线程的join()方法时，表示只有当B线程执行完毕时，A线程才能继续执行。可以叫有顺序的执行
 * 		前提join没有指定时间
 * 
 * 		如果指定了时间则表示 B线程执行了指定的时间后，A和B线程就开始并发的执行
 */
public class JoinTest {
	public static void main(String[] args) throws InterruptedException {
		ThreadJoinTest t1 = new ThreadJoinTest("小明");
		ThreadJoinTest t2 = new ThreadJoinTest("小东");
		
		t1.start();
		
		t1.join(1);
		
		t2.start();
		
//		t2.join(2);
		
//		System.out.println(Thread.currentThread().getName() + "=============");
	}

}

class ThreadJoinTest extends Thread {
	public ThreadJoinTest(String name) {
		super(name);
	}

	@Override
	public void run() {
		for (int i = 0; i < 1000; i++) {
			System.out.println(this.getName() + ":" + i);
		}
	}
}