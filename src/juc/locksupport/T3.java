package juc.locksupport;

import java.util.concurrent.locks.LockSupport;

/**
 * 熟悉LockSupport的用法，使用多次park
 */
public class T3 {

    public static void main(String[] args) throws InterruptedException {

        Thread t = new Thread(() -> {
            for(int i = 0; i < 10; i++) {
                System.out.println("当前的i值为: " + i);

                if(i == 3) {
                    LockSupport.park();
                    System.out.println("已经解锁1, 此时的i值为: " + i);
                }
                if(i == 7) {
                    LockSupport.park();
                    System.out.println("已经解锁2, 此时的i值为: " + i);
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

//        LockSupport.unpark(t);
//        当有两个park的时候，一个unpark只能处理一个park，后续的park就会被阻塞，
//        但是此时如果添加相等数量的unpark，似乎不起效
    }

}
