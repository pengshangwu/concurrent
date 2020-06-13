package juc.cyclicBarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarrier：等待指定数量的parties完成后，
 * 要么执行回调方法barrierAction，然后再执行cyclicBarrier.await();之后的逻辑
 */
public class CyclicBarrierTest {

    private static int times = 1;
    private static CyclicBarrier cyclicBarrier = new CyclicBarrier(10, new Work());

    public static void main(String[] args) {

        Thread[] threads = creatThreads(10);
        for(Thread thread : threads) {
            thread.start();
        }
    }

    public static class Work implements Runnable{
        @Override
        public void run() {
            System.out.println("已经执行完"+ times++ +"轮");
        }
    }

    private static Thread[] creatThreads(int size) {
        Thread[] threads = new Thread[size];
        for (int i = 0; i < size; i++) {
            threads[i] = new Thread(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + "开始执行第一轮");
                    cyclicBarrier.await(); // 可重复使用

                    System.out.println(Thread.currentThread().getName() + "开始执行第二轮");
                    cyclicBarrier.await(); // 可重复使用

                    System.out.println(Thread.currentThread().getName() + "开始执行第三轮");
                    cyclicBarrier.await(); // 可重复使用

                    /**
                     * 感觉上述操作和Phaser一个道理，待验证其中的不同之处
                     * // 开始做第一道题目
                     * System.out.println(Thread.currentThread().getName() + ": 开始做一道题目");
                     *         phaser.arriveAndAwaitAdvance();
                     *
                     *         if("Thread-0".equals(Thread.currentThread().getName())) {
                     *             phaser.arriveAndDeregister();
                     *         }
                     *         // 开始做第二道题目
                     *         System.out.println(Thread.currentThread().getName() + ": 开始做二道题目");
                     *         phaser.arriveAndAwaitAdvance();
                     *
                     *         // 开始做第三道题目
                     *         System.out.println(Thread.currentThread().getName() + ": 开始做三道题目");
                     *         phaser.arriveAndAwaitAdvance();
                     */

                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }, "t" + i);
        }
        return threads;
    }
}
