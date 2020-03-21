package yxxy.c_027;

import java.util.concurrent.Semaphore;

/**
 * 信号量
 * 具体参看  java并发编程.md
 */
public class T01_Semaphore {

    private Semaphore semaphore = new Semaphore(1);
    private int i = 0;

    public void test() {

        try {
            // 当semaphore小于0的时候，所有线程都得在等待
            semaphore.acquire();

            System.out.println(Thread.currentThread().getName() + ", " + i++);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 将semaphore的值加1
            semaphore.release();
        }
    }

    public static void main(String[] args) {

        T01_Semaphore semaphore = new T01_Semaphore();
        for (int i = 0; i < 10; i++) {
            new Thread(semaphore::test).start();
        }


    }
}
