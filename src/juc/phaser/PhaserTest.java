package juc.phaser;

import java.util.concurrent.Phaser;

/**
 * phaser：阶段的意思，各个阶段执行的结果看呢过不一致，但必须等待其他的线程到来才能执行下一步操作
 * java多线程技术提供了Phaser工具类，Phaser表示“阶段器”，用来解决控制多个线程分阶段共同完成任务的情景问题。
 * 其作用相比CountDownLatch和CyclicBarrier更加灵活，
 * 例如有这样的一个题目：
 *          5个学生一起参加考试，一共有三道题，要求所有学生到齐才能开始考试，
 *          全部同学都做完第一题，学生才能继续做第二题，全部学生做完了第二题，才能做第三题，
 *          所有学生都做完的第三题，考试才结束。
 *    分析这个题目：这是一个多线程（5个学生）分阶段问题（考试考试、第一题做完、第二题做完、第三题做完），
 *    所以很适合用Phaser解决这个问题。
 */
public class PhaserTest {

    public static void main(String[] args) throws Exception {

        Phaser phaser = new Phaser(3);
        for(int i = 0; i < 3; i++) {
            Thread.sleep(1000);

            new Thread(new Student(phaser)).start();

        }
    }
}

class Student implements Runnable {

    private Phaser phaser;

    public Student(Phaser phaser) {
        this.phaser = phaser;
    }

    @Override
    public void run() {

        // 开始做第一道题目
        System.out.println(Thread.currentThread().getName() + ": 开始做一道题目");
        phaser.arriveAndAwaitAdvance();

        if("Thread-0".equals(Thread.currentThread().getName())) {
            phaser.arriveAndDeregister();
        }
        // 开始做第二道题目
        System.out.println(Thread.currentThread().getName() + ": 开始做二道题目");
        phaser.arriveAndAwaitAdvance();

        // 开始做第三道题目
        System.out.println(Thread.currentThread().getName() + ": 开始做三道题目");
        phaser.arriveAndAwaitAdvance();

    }
}
