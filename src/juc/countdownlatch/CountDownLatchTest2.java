package juc.countdownlatch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class CountDownLatchTest2 {

    volatile List<Integer> list = new ArrayList<>();

    public void add(int i) {
        list.add(i);
    }

    public int size() {
        return list.size();
    }

    public static void main(String[] args) {

        CountDownLatchTest2 c = new CountDownLatchTest2();
        CountDownLatch latch = new CountDownLatch(1);

        new Thread(() -> {
            System.out.println("t1...启动");
            if(c.size() != 5) {
                try {
                    latch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("t2...结束");
        }, "t1").start();

        new Thread(() -> {
            for(int i = 0; i < 10; i++) {
                c.add(i);
                System.out.println(i + 1);
                if(c.size() == 5) {
                    latch.countDown();
//                    try {
//                        latch.await();
//                        System.out.println("await....");
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                }
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t1").start();
    }
}
