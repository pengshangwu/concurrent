package yxxy.c_021;

import java.util.LinkedList;

/**
 * 面试题：写一个固定容量的容器，拥有put和get方法，以及getCount方法，
 * 能够支持2个生产者线程以及10个消费者线程的阻塞调用
 * 
 * 
 * put 和  get 方法中不用if来判断的容器数量的原因： 
 * 				假设当等待的两个生产者线程被唤醒的时候(此时容器刚好还差一个就达到满容器状态)，
 * 				如果使用if的话，唤醒之后一个生产线程1，执行先前的代码(先前所有生产线程都在this.wait();等候);
 * 				此时容器已经达到MAX状态，这时候线程1执行完释放锁，如果恰巧碰到又一个生产线程获取到锁则从this.wait()
 * 				那里往下执行，执行到list.add(o);就会抛异常，
 * 				所以while的好处就是循环的检查容器的大小以防止出现类似的问题
 * 				effective java 一书中写道：wait 99%是和 while一起使用
 */
public class MyContainer1 {
	
	volatile LinkedList<String> list = new LinkedList<String>();
	private final int MAX = 10;
	
	public synchronized void put(String o) {
		//如果容器中的数量等最大数，就停止让生产线程生产
		while(list.size() == MAX) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		//如果容器中的数量小于最大数，就让生产线程生产
		list.add(o);
		
		//同时唤醒所有的线程（包括消费线程）来消费
//		this.notify(); 不用这个的理由是怕唤醒的是一个生产者线程，最终会造成死锁
		this.notifyAll();
	}
	
	public synchronized String get() {
		String str = null;
		//如果容器中的数量等于0，就停止让消费线程消费
		while(list.size() == 0) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		//如果容器中的数量大于0，就让消费线程消费
		str = list.removeFirst();
				
		//同时唤醒所有的线程（包括生产线程）来生产
//		this.notify(); 不用这个的理由是怕唤醒的是一个消费者线程，最终会造成死锁
		this.notifyAll();
		return str;
	}
	
	public synchronized int getCount() {
		return list.size();
	}
	
	public static void main(String[] args) {
		
		MyContainer1 c = new MyContainer1();
		
		for (int i = 0; i < 2; i++) {
			
			new Thread(() -> {
				for (int j = 0; j < 25; j++) {
					c.put(Thread.currentThread().getName() + " " + j);
				}
			}, "p" + i).start();
		}
		
		for (int i = 0; i < 10; i++) {
			new Thread(() -> {
				for (int j = 0; j < 5; j++) {
					System.out.println(c.get() + " " + Thread.currentThread().getName());
				}
			}, "c" + i).start();
		}
	}
}
