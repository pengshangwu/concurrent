package yxxy.c_021;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 面试题：写一个固定容量的容器，拥有put和get方法，以及getCount方法，
 * 能够支持2个生产者线程以及10个消费者线程的阻塞调用
 * 
 * 
 * put 和  get 方法中不用if来判断的容器数量的原因： 
 * 				假设当等待的两个生产者线程被唤醒的时候(此时容器刚好还差一个就达到满容器状态)，
 * 				如果使用if的话，唤醒之后一个线程1执行到了list.add(o);前一处停止了，此时刚好
 * 				另一个线程2获取到了对象锁，执行完往容器中添加的操作的代码后，被线程1获取到了
 * 				锁后接着上一次往下执行就会出问题，所以while的好处就是循环的检查容器的大小以防止出现类似的问题
 * 				effective java 一书中写道：wait 99%是和 while一起使用
 * 
 * 可以使用lock和Condition来实现
 * 对比两种方式，Condition的方式可以更加精确的指定哪些线程被唤醒
 */
public class MyContainer2 {
	
	volatile LinkedList<String> list = new LinkedList<String>();
	private final int MAX = 10;
	
	private Lock lock = new ReentrantLock();
	private Condition consumer = lock.newCondition();
	private Condition producer = lock.newCondition();
	
	public void put(String o) {
		
		try {
			lock.lock();
			while(list.size() == MAX) {
				producer.await();//让所有的生产者线程等待
			}

			list.add(o);
			System.out.println("生产后容器容量为：" + list.size());
			consumer.signalAll();//通知所有消费者线程进行消费
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}
	
	public String get() {
		String str = null;
		try {
			lock.lock();
			while(list.size() == 0) {
				consumer.await();
			}
			str = list.removeFirst();
			System.out.println("消费后容器容量为：" + list.size());
			producer.signalAll();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
		return str;
	}
	
	public int getCount() {
		return list.size();
	}
	
	public static void main(String[] args) {
		
		MyContainer2 c = new MyContainer2();
		
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
//					System.out.println(c.get() + " " + Thread.currentThread().getName());
					c.get();
				}
			}, "c" + i).start();
		}
	}
}
