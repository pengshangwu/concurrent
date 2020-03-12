package yxxy.c_019;

import java.util.ArrayList;
import java.util.List;

/**
 * 面试题：
 * 实现一个容器，提供两个方法：add，size
 * 写两个线程，线程1添加10个元素到容器中，线程2对容器的元素个数进行监控，当个数到达了5时，线程2给一个提示并结束
 *
 * 线程二代码执行不了，list的修改对线程二不可见
 */
public class MyContainer1 {
	
	List<String> list = new ArrayList<>();
	
	public void add(String i) {
		list.add(i);
	}
	
	public int size() {
		return list.size();
	}
	
	public static void main(String[] args) {
		
		MyContainer1 c = new MyContainer1();
		
		new Thread(() -> {
			for (int i = 0; i < 10; i++) {
				c.add(i + "");
				System.out.println(i);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}, "t1").start();
		
		new Thread(() -> {
			while(true) {
				if(c.size() == 5) {
					System.out.println("数量已到达5");
					break;
				}
			}
		}, "t2").start();
	}
}
