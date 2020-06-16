package juc.phaser;

import java.util.concurrent.Phaser;

public class PhaserTest2 {


    public static final int PARTIES = 3;
    public static final int PHASES = 4;

    public static void main(String[] args) {

        Phaser phaser = new Phaser(PARTIES) {
            @Override
            protected boolean onAdvance(int phase, int registeredParties) {
                // 本文由公从号“彤哥读源码”原创
                System.out.println("=======phase: " + phase + " finished=============");
                return super.onAdvance(phase, registeredParties);
            }
        };

        for (int i = 0; i < PARTIES; i++) {
            new Thread(() -> {
                for (int j = 0; j < PHASES; j++) {
                    System.out.println(String.format("%s: phase: %d", Thread.currentThread().getName(), j));
                    phaser.arriveAndAwaitAdvance();
                }
            }, "Thread " + i).start();
        }
    }


}
