package com.tang.java.test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * tangdunhong
 * 2019/8/19
 * 9:16 PM
 **/
public class VolatileTest {

    private AtomicInteger a = new AtomicInteger(0);
    volatile int b = 0;
    final int SIZE = 10000;
    int threadSize = 100;
    Semaphore semaphore = new Semaphore(threadSize);
    CountDownLatch latch = new CountDownLatch(threadSize);
    public static void main(String[] args) throws Exception {
        new VolatileTest().seeVolatile();
    }

    public void seeVolatile(){
        Runnable r1 = () -> {
            int count = 0;
            while (++count <= SIZE) {
                a.addAndGet(1);
                b++;
            }
            try {
                semaphore.acquire();
                latch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        Thread[] threads = new Thread[threadSize];
        for (int i = 0; i < threadSize; i++) {
            threads[i] = new Thread(r1);
        }

        for (int i = 0; i < threadSize; i++) {
            threads[i].start();
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("when end, active thread : " + semaphore.availablePermits());
        System.out.println("a: " + a + ", b: " + b);
    }
}
