package Test.thread;

import java.util.concurrent.TimeUnit;

/**
 * 调用run和start的区别
 *      start：
 *          1、会启用一个线程来执行run中的方法，此时会和主线程一起执行
 *      run：
 *          1、不会启用线程执行，只是被当作普通的方法执行
 */
public class RunStart {

    static class T1 extends Thread {
        @Override
        public void run() {
            for(int i = 0; i < 10; i++) {
                try {
                    TimeUnit.MICROSECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("T1");
            }
        }
    }

    public static void main(String[] args) {

        new T1().run();
//        new T1().start();

        for(int i = 0; i < 10; i++) {
            try {
                TimeUnit.MICROSECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("main");
        }
    }



}
