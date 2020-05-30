package yxxy.c_020;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 测试ReentrantLock 的 lock 和 lockInterruptibly 区别
 */
public class ReentrantLock6 {

    public static void main(String[] args) {

        Lock lock = new ReentrantLock();

        new Thread(() -> {
            lock.lock();
            try {
                try {
                    System.out.println("Integer.MAX_VALUE.....");
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } finally {
                lock.unlock();
            }
        }).start();

        Thread t1 = new Thread(() -> {
            lock.lock();
            try {
                try {
                    System.out.println("t1 is done.....");
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("t1.....interrupt");
                }
            } finally {
                lock.unlock();
            }
        });
        t1.start();

        Thread t2 = new Thread(() -> {
            try {
                lock.lockInterruptibly();
                System.out.println("t2 is done.....");
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("t2.....interrupt");
            } finally {
                lock.unlock();
            }
        });
        t2.start();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        t1.interrupt();
        t2.interrupt(); // 只有t2被打断
    }
}
