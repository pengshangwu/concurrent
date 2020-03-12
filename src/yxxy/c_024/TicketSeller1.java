package yxxy.c_024;

import java.util.ArrayList;
import java.util.List;

/**
 * 有N张火车票，每张火车票都有一个编号，
 * 同时10个窗口对外销售，写一个模拟程序
 * 
 * 分析如下的程序会产生哪些问题？
 * 重复销售还是超量销售
 */
public class TicketSeller1 {
	
	static List<String> tickets = new ArrayList<String>();
	
	static {
		for (int i = 0; i < 1000; i++) {
			tickets.add("编号" + i);
		}
	}
	
	public static void main(String[] args) {
		
		for (int i = 0; i < 10; i++) {
			new Thread(() -> {
				while(tickets.size() > 0) {
					System.out.println("销售了---" + tickets.remove(0));
				}
			}).start();
		}
	}
	
	
	
	
}
