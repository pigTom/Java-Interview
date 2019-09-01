package com.tang.java.Thread;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * tangdunhong
 * 2019/8/22
 * 10:23 PM
 **/
public class ConcurrentTest {
    final int testSize = 10;
    int threadPoolSize = 50;
    int threadSize = 10;
    volatile AtomicInteger id = new AtomicInteger(0);

    public void testSemaphore() {
        Semaphore semaphore = new Semaphore(testSize);

        ExecutorService ex = Executors.newFixedThreadPool(threadPoolSize);

        Runnable r = ()-> {
            try {
                semaphore.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                int myId = id.addAndGet(1);
                System.out.println(myId);
                // sleep for 100 millisecond
                Thread.sleep(100);

            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            finally
            {
                id.decrementAndGet();
                semaphore.release();

            }

        };
        for (int i = 0; i < threadSize; i++) {
            ex.execute(r);
        }

        ex.shutdown();
        System.out.println("finished");
    }

    // 一个或多个线程等 其他线程完成之后执行
    public void testCountDownLatch() {
        CountDownLatch latch = new CountDownLatch(threadSize);
        ExecutorService ex = Executors.newFixedThreadPool(threadPoolSize);
        Runnable r = () -> {
            try {
                int myId = id.incrementAndGet();
                latch.countDown();
                while (true) {
                    System.out.println(myId);
                    latch.await();
                    System.out.println("again----"+myId);
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        };

        for (int i = 0; i < threadSize; i++) {
            ex.execute(r);
        }
        ex.shutdown();
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("finished");

    }

    public void testCyclicBarrier() {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(testSize);

        Runnable r = () -> {
            int myId = id.incrementAndGet();

            while (true) {
                System.out.println(myId + " is ready");
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                System.out.println(myId + " is finished");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

//            while (true) {
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//                try {
//
//                    cyclicBarrier.await();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } catch (BrokenBarrierException e) {
//                    e.printStackTrace();
//                }
//                System.out.println(myId);
//
//                synchronized (cyclicBarrier) {
//                    if (cyclicBarrier.isBroken()) {
//                        System.out.println("------------reset---------");
//                        cyclicBarrier.reset();
//                    }
//                }
//            }
        };

        ExecutorService ex = Executors.newFixedThreadPool(threadPoolSize);

        for (int i = 0; i < threadSize; i++) {
            ex.execute(r);
        }

        ex.shutdown();
//        System.out.println("finished");
    }

    public static void main(String[] args) {
//        ConcurrentTest test = new ConcurrentTest();
////      test semaphore
////      test.testSemaphore();
//
////      test CountDownLatch
//      test.testCountDownLatch();
        Integer i = 127;// 2 ^ 6 - 1
        Integer i1 = 127;
        System.out.println(i==i1);


        // test cyclic barrier
//        test.testCyclicBarrier();
    }
}
