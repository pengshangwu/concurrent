package yxxy.c_025;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class T01_ConcurrentMap {
	
	public static void main(String[] args) {
		/**
		 * 效率
		 * 	ConcurrentHashMap > ConcurrentSkipListMap > Hashtable(或者Collections.SynchronizedXXX)
		 */
//		Map<String, String> map = new ConcurrentHashMap<>(); 线程安全，效率高
//		Map<String, String> map = new Hashtable<>(); 线程安全，但是效率低
//		Map<String, String> map = new ConcurrentSkipListMap<>();  线程安全并且对插入的数据进行排序(插入效率低，查询效率高)
		Map<String, String> map = new HashMap<>();  // 线程不安全，须采用 Collections.SynchronizedXXX来进行相应的锁定
		
//		Map<String, String> synchronizedMap = Collections.synchronizedMap(map);  synchronizedMap所有的方法都上了锁
		
		Random r = new Random();
		Thread[] ths = new Thread[100];
		
		CountDownLatch latch = new CountDownLatch(ths.length);
		
		long start = System.currentTimeMillis();
		
		for (int i = 0; i < ths.length; i++) {
			ths[i] = new Thread(() -> {
				for (int j = 0; j < 10000; j++) {
					map.put("a" + r.nextInt(100000), "a" + r.nextInt(100000));
				}
				latch.countDown();
			});
		}
		
		Arrays.asList(ths).forEach(t -> t.start());

		// 让主线程等所有线程执行完才能往下执行
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		long end = System.currentTimeMillis();
		
		System.out.println(start - end);
		
	}

}
