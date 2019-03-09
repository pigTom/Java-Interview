package com.tang.java.Thread;

import org.junit.Test;

public class NotifyTest extends Thread {
    // monitor lock
    private final static Object lock = new NotifyTest();

    @Override
    public void run() {
//        print0();
        print2();
//        print1();

    }

    // synchronized on the same lock so they only can execute one by one
    // except call wait method to give up the monitor lock
    private void print0() {

        try {
            synchronized (lock) {
                System.out.println("come in -- " + currentThread().getName());
                // wake up all thread that waiting on lock
                // if the thread can go on after notify all
                lock.notify();
                Thread curThread = currentThread();
                System.out.println(curThread.getName() + ", I got the lock, and then I released it");
                System.out.println(curThread.getName() + ", I got the lock again");
                // if the lock do not call notify method, then current thread will wait forever.
                // after call lock.wait. current thread will become WAITING state
                // and when other thread get the monitor lock on lock call notify, and current thread waken
                // by this thread then current thread will become BLOCKED
                lock.wait();

                // current lock will given in the current monitor lock and become waiting
                // util the lock of
                // lock.wait(100)
                System.out.println("|********| " + curThread.getName() + " finished. Bye bye");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    // synchronized on itself, so this thread can execute interactively
    private synchronized void print1 () {

        System.out.println(currentThread().getName() + " Here I come by Object lock - o");
        try {
            // wait for itself monitor lock
//            this.wait();
                Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        System.out.println(currentThread().getName() + " Here I leave by Object lock - o");

    }

    // wake up itself
    private synchronized void wake() {
        notify();
    }

    // synchronized on class0 lock, thread of this class0 can only execute one by one
    private static synchronized void print2() {
        System.out.println(Thread.currentThread().getName() + " Here I come by class0 lock - c");

        // static synchronized block can`t call wait nor notify

        try {
            // sleep for 100 millisecond test if other thread will got the lock and execute
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // so once the static synchronized block get monitor lock then
        System.out.println(Thread.currentThread().getName() + " Here I leave by class0 lock - c");
    }
    // test when thread got the monitor lock and release the lock, what will happened
    // current thread will continue util current call wait to relinquishes the lock,
    // call wait will come into that result.
    @Test
    public void test() {
        int size = 30;
        NotifyTest[] threadArr = new NotifyTest[size];
        for (int i = 0; i < threadArr.length; i++) {
            threadArr[i] = new NotifyTest();
        }

        // start thread
        for (NotifyTest test : threadArr)
            test.start();
        // wait for some time and notify the last waiting thread to wake up
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (NotifyTest test : threadArr) {
            test.wake();
        }
        // test for static method of that class0 will got the monitor lock
//        staticPrint(threadArr);
    }

    // question 1:  if a class0 lock be taken by a thread ,
    //              whether other thread on the class0 object will be got the monitor lock.
    // answer   1:  yes, they may got monitor lock with serious possibility

    // question 2:  if a object lock be taken by a thread, then the class0 lock will be take?
    // answer   2:  thread on that object class0 may got the lock and execute with low possibility
}
