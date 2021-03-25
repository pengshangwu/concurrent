package yxxy.c_020;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 测试ReentrantLock 的 lock 和 lockInterruptibly 区别
 */
public class ReentrantLock7 {

    private volatile List<String> list = new ArrayList<>(10);

    private Lock consumer = new ReentrantLock();
    private Lock product = new ReentrantLock();
    private Condition notFull = consumer.newCondition(); // 相同条件的队列，持有锁的线程执行代码时，若条件不符合，则进入条件队列
    private Condition notEmpty = product.newCondition();

    public void take() {
        consumer.lock();
        try {
            while (list.size() == 0) {
                notEmpty.await();
            }
            String remove = list.remove(0);
            System.out.println("消费者: " + Thread.currentThread().getName() + ": " + remove);
            notFull.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            consumer.unlock();
        }
    }

    public void put(String value) {
        product.lock();
        try {
            while (list.size() == 10) {
                notFull.await();
            }
            list.add(value);
            System.out.println("生产者: " + Thread.currentThread().getName() + ": " + value);
            notEmpty.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            product.unlock();
        }
    }

    public static void main(String[] args) {

        ReentrantLock7 reentrantLock7 = new ReentrantLock7();

        for (int i = 0; i < 2; i++) {

            new Thread(() -> {
                for (int j = 0; j < 25; j++) {
                    reentrantLock7.put(Thread.currentThread().getName() + " " + j);
                }
            }, "p" + i).start();
        }

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 5; j++) {
                    reentrantLock7.take();
                }
            }, "c" + i).start();
        }



    }
}
