package juc.cyclicBarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarrier
 */
public class CyclicBarrierTest {

    private static int times = 1;
    private static CyclicBarrier cyclicBarrier = new CyclicBarrier(10, new Work());

    public static void main(String[] args) {

        Thread[] threads = creatThreads();
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

    private static Thread[] creatThreads() {
        Thread[] threads = new Thread[100];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                System.out.println(Thread.currentThread().getName());
                try {
                    cyclicBarrier.await(); // 可重复使用
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }, "t" + i);
        }
        return threads;
    }
}
