package yxxy.c_025;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 有界队列，必须指定队列的大小
 *
 */
public class T06_ArrayBlockingQueue {
	
	static BlockingQueue<String> strs = new ArrayBlockingQueue<>(10);
	
	public static void main(String[] args) throws Exception{
		
		
		for (int i = 0; i < 10; i++) {
			strs.put(i + "");
		}
		
//		strs.add("aaa"); // 如果队列满了，再加的话就直接抛异常
//		strs.put("aaa"); // 如果队列满了，再加的话就一直阻塞，等到没满的时候执行
//		
//		strs.offer("aaa"); // 往里加的话直接返回一个布尔值，加成功就返回true，反之false
		strs.offer("aaa", 10, TimeUnit.SECONDS); // 一段时间内往队列加东西，加成功就返回true，反之false
		
		System.out.println(strs);
		
	}

}
