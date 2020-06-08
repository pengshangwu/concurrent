package juc.countdownlatch;

import java.util.concurrent.CountDownLatch;

/**
 * 演示了CountDownLatch 和 join的区别，前者比后者更加灵活
 */
public class CountDownLatchTest {
    public static void main(String[] args) throws InterruptedException {

//        CountDownLatch latch = new CountDownLatch(10);
//        for(int i = 0; i < 10; i ++) {
//
//            new Thread(() -> {
//                System.out.println(Thread.currentThread().getName());
//
//                latch.countDown();
//
//            }, "t" + i).start();
//        }
//        // 当不采用await时，下面的代码会在上面执行完之前执行
//        try {
//            latch.await();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println("终于执行完了");
//        System.out.println("======================================");

//        CountDownLatch latch = new CountDownLatch(1);
//        new Thread(new TestCountDownLatch(latch)).start();
//        latch.await();
//        System.out.println("终于执行完了");

        Thread t1 = new Thread(new TestJoin());
        t1.start();
        t1.join();
        System.out.println("终于执行完了");
    }
}

class TestCountDownLatch implements Runnable {
    private CountDownLatch latch;
    public TestCountDownLatch(CountDownLatch latch) {
        this.latch = latch;
    }
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + ": TestCountDownLatch before");
        latch.countDown();
        try {
            /**
             * 模拟执行后续逻辑，上述逻辑执行完之后，latch.await();之后的线程可以不用等待当前线程执行完逻辑代码
             * 这就是CountDownLatch和join的区别
             */

            Thread.sleep(10000);

            System.out.println(Thread.currentThread().getName() + ": TestCountDownLatch after");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
class TestJoin implements Runnable {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + ": TestJoin before");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + ": TestJoin after");
    }
}
