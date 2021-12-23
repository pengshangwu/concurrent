package juc.semaphore;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class SemaphoreTest {
    volatile List<Integer> list = new ArrayList<>();

    public void add(int i) {
        list.add(i);
    }

    public int size() {
        return list.size();
    }

    public static void main(String[] args) throws InterruptedException {

        SemaphoreTest c = new SemaphoreTest();
        Semaphore semaphore = new Semaphore(1);
        new Thread(() -> {
            for(int i = 0; i < 10; i++) {
                c.add(i);
                System.out.println(i + 1);
                if(c.size() == 5) {
                    try {
                        semaphore.acquire();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "t2").start();

        Thread.sleep(100);

        new Thread(() -> {
            System.out.println("t1...启动");
            if(c.size() != 5) {

            }
            System.out.println("t2...结束");
        }, "t1").start();
    }
}
