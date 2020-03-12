package yxxy.c_024;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 有N张火车票，每张火车票都有一个编号，
 * 同时10个窗口对外销售，倾斜一个模拟程序
 * 
 * 分析如下的程序会产生哪些问题？
 * 重复销售还是超量销售
 */
public class TicketSeller4 {
	
	static Queue<String> tickets = new ConcurrentLinkedQueue<>();
	
	static {
		for (int i = 0; i < 1000; i++) {
			tickets.add("票号" + i);
		}
	}
	
	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			new Thread(() -> {
				while(true) {
					/**
					 * poll方法是原子性的，每次拿的话，如果拿到了就是拿到了，没拿到就是空
					 * 如下的程序不会出问题，只不过出票的顺序是变化的
					 * 
					 * 相比之前的三个类，以前的是先判断了之后又对队列做了修改的的操作，所以才会引发不同的问题
					 * 而这里先获取之后在做判断
					 * 效率会高很多 
					 */
					String s = tickets.poll();
					
					if(s == null) {
						break; 
					}else {
						System.out.println(Thread.currentThread().getName() + "销售----" + s);
					}
				}
			}).start();
		}
	}
}
