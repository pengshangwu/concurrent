package yxxy.c_024;

import java.util.ArrayList;
import java.util.List;

/**
 * 有N张火车票，每张火车票都有一个编号，
 * 同时10个窗口对外销售，倾斜一个模拟程序
 * 
 * 分析如下的程序会产生哪些问题？
 * 重复销售还是超量销售
 */
public class TicketSeller3 {
	
	static List<String> tickets = new ArrayList<>();
	
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
					 * 锁住票的容器对象，但是缺点是：每次获取锁都是锁所有的票
					 */
					synchronized(tickets) { 
						
						if(tickets.size() == 0) break; // 此处如果不在做一次的判断的话，如果容量为0，获得锁的线程执行就会报错
						
						System.out.println(Thread.currentThread().getName() + "销售----" + tickets.remove(0));
					}
				}
			}).start();
		}
	}
}
