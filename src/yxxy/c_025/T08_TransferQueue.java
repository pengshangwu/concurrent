package yxxy.c_025;

import java.util.concurrent.LinkedTransferQueue;

/**
 *	TransferQueue提供一个方法transfer()，是其自己的方法
 *		作用：
 *		生产者线程直接将生产的东西交给消费者线程，不会放到队列中（这样比放在队列里的效率高），
 *		如果此时有消费者线程在等待就会直接给，反之生产者线程就阻塞在那
 *
 *		1.tryTransfer(E)：
 *			将元素立刻给消费者。准确的说就是立刻给一个等待接收元素的线程，
 *			如果没有消费者就会返回false，而不将元素放入队列。
 *		2.transfer(E)：将元素给消费者，如果没有消费者就会等待。
 *		3.tryTransfer(E,long,TimeUnit)：将元素立刻给消费者，如果没有就等待指定时间。给失败返回false。
 *
 *		其他方法和其他的阻塞队列差不多
 */
public class T08_TransferQueue {
	
	public static void main(String[] args) throws InterruptedException {
		
		LinkedTransferQueue<String> str = new LinkedTransferQueue<>();
		
//		str.put("qqqq");
		
		// 模拟分别在生产线程的前后启动消费线程
//		new Thread(() -> {
//			try {
//				System.out.println(str.take());
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}).start();

		str.transfer("aaaa");
//		str.tryTransfer("aaaa");
		
		new Thread(() -> {
			try {
				System.out.println(str.take());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();
		
		System.out.println(str);
		System.out.println("ooooooo");
	}
}
