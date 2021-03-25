package Test;

import java.util.concurrent.locks.ReentrantLock;

public class AQSTest {

    public static void main(String[] args) {
        AQSTest aqs = new AQSTest();
        for (int i = 0; i < 10; i++) {
            new Thread(aqs::add, "t" + i).start();
        }
    }

    public void add() {
        ReentrantLock lock = new ReentrantLock(true);
        lock.tryLock();
        try {
            System.out.println("==============================");
            System.out.println(Thread.currentThread().getName());
        }finally {
            lock.unlock();
        }
    }

}
