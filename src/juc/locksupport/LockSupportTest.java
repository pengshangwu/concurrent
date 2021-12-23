package juc.locksupport;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.LockSupport;

public class LockSupportTest {

    volatile List<Integer> list = new ArrayList<>();
    static Thread t1, t2;

    public void add(int i) {
        list.add(i);
    }

    public int size() {
        return list.size();
    }

    public static void main(String[] args) {

        LockSupportTest c = new LockSupportTest();

        Thread t1 = new Thread(() -> {
            System.out.println("t1...启动");
            if(c.size() != 5) {
                LockSupport.park();
            }
            System.out.println("t2...结束");
            LockSupport.unpark(t2);
        });
        t1.start();

        t2 = new Thread(() -> {
            for(int i = 0; i < 10; i++) {
                c.add(i);
                System.out.println(i + 1);
                if(c.size() == 5) {
                    LockSupport.unpark(t1);
                    LockSupport.park();
                }
//                try {
//                    Thread.sleep(1);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            }
        });
        t2.start();
    }
}
