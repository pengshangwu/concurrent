package yxxy.c_024;

import java.util.Vector;

/**
 * 有N张火车票，每张火车票都有一个编号，
 * 同时10个窗口对外销售，倾斜一个模拟程序
 * 
 * 分析如下的程序会产生哪些问题？
 * 重复销售还是超量销售
 * 
 * List不是同步容器，Vector是属于同步容器
 */
public class TicketSeller2 {
	
	static Vector<String> tickets = new Vector<String>();
	
	static {
		for (int i = 0; i < 1000; i++) {
			tickets.add("编号" + i);
		}
	}
	
	public static void main(String[] args) {
		
		for (int i = 0; i < 10; i++) {
			new Thread(() -> {
				while(tickets.size() > 0) {
					
					/**
					 * 虽说vector是同步容器，且方法都是同步的，但是不能保证同步方法之间也是同步的
					 * 这就相当于Ques_7的代码,，如下代码是将上述的问题放大
					 */
					/*
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}*/
					System.out.println("销售了---" + tickets.remove(0));
				}
			}).start();
		}
	}
	
	
	
	
	
	
}
