package juc.locksupport;

import java.util.concurrent.locks.LockSupport;

/**
 * 熟悉LockSupport的用法，使用unpark
 */
public class T2 {

    public static void main(String[] args) throws InterruptedException {

        Thread t = new Thread(() -> {
            for(int i = 0; i < 10; i++) {
                System.out.println("当前的i值为: " + i);

                if(i == 6) {
                    LockSupport.park();
                    System.out.println("已经解锁");
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();

//        Thread.sleep(20000); 模拟park之后，等待unpark唤醒，执行park后续的操作

        // 如果上述没有添加休眠时间，则unpark会优先于park，但是好像执行到park就自动解锁了，
        LockSupport.unpark(t);
    }

}
