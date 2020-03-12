package yxxy.c_025;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 	原理：写的时候，会将原有的数据复制一份(简称副本)，再在副本上进行相应的操作，然后再将原有数据的引用指向副本(引用的指向是原子性)
 * 	写时复制容器 copy on write
 * 	多线程下，写时效率低，读时效率高
 * 	适合少写多读的情况
 * 
 * 
 * 	不足：
 * 		1、写时效率低(因为要弄出一个副本来)
 *  	2、假如说一个线程正在对容器进行修改，另一个线程正在读取容器的内容，
 *  		这其实是两个容器数组。那么读线程读到就是旧数据
 *  		所以该问题点造就了     能保证数据最终一致性       但       不能保证数据的实时一致性
 */
public class T02_CopyOnWriteList {
	
	public static void main(String[] args) {
		List<String> lists = 
//			new ArrayList<>();
//			new Vector<>();
			new CopyOnWriteArrayList<>();
		
		Thread[] ths = new Thread[100];
		
		for (int i = 0; i < ths.length; i++) {
			ths[i] = new Thread(() -> {
				for (int j = 0; j < 1000; j++) {
					lists.add(j + "");
				}
			}, "t"+i);
		}
		
		long start = System.currentTimeMillis();
		
		Arrays.asList(ths).forEach(t -> t.start());
		Arrays.asList(ths).forEach(t -> {
			try {
				t.join();  // join的作用看JoinTest.java中有说明   就是让主线程等待创建的线程执行完后再执行
				System.out.println(Thread.currentThread().getName());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		
		long end = System.currentTimeMillis();
		System.out.println(end - start);
		System.out.println(lists.size());
	}
}
