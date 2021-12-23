package juc.locksupport;

import java.util.concurrent.locks.LockSupport;

/**
 * 熟悉LockSupport的用法，不使用unpark，会一直阻塞
 */
public class T1 {

    public static void main(String[] args) {

        new Thread(() -> {
            for(int i = 0; i < 10; i++) {
                System.out.println("当前的i值为: " + i);

                if(i == 5) {
                    LockSupport.park();
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
