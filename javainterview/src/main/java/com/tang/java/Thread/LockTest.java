package com.tang.java.Thread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/***
 * wait 使线程进入等待状态，等待结束之后会变成阻塞状态或者运行状态。
 */
public class LockTest implements Runnable{
    class A {
        public void sayHello() {
            String threadName = Thread.currentThread().getName();
            System.out.println(this);
            System.out.println(threadName + ": Hello");
            System.out.println(threadName + ": Hello");
            System.out.println(threadName + ": Hello");
        }
    }

    private A a;

    public void setA(A a) {
        this.a = a;
    }

    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();
        System.out.println("in this thread: ->" + threadName);;
        a.sayHello();
        System.out.println("in this thread again: ->" + threadName);;
        a.sayHello();
    }

    public static void main(String[] args) {

//        LockTest test = new LockTest();
//        A a = test.new A();
//        test.setA(a);
//        Thread thread = new Thread(test);
//        Thread thread1 = new Thread(test);
//        thread.start();
//        thread1.start();

        testinterrupt();
    }

    public static void testinterrupt() {
        Lock lock = new ReentrantLock();

        Thread t = new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                lock.lock();
                try {
                    System.out.println("t get the reentrant Lock...");
                    wait(1000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } finally {
                    System.out.println("t release the reentrant lock...");
                    lock.unlock();
                }
            }

        });

        Thread t1 = new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                try {
                    lock.lockInterruptibly();
                    System.out.println("t2 get the reentrant lock");
                    lock.unlock();
                    System.out.println("t2 release the reentrant lock");
                } catch (InterruptedException e1) {
                    // TODO Auto-generated catch block
                    //e1.printStackTrace();

                    System.out.println("t2 got interrupted ... ");
                }

            }

        });

        t.start();
        t1.start();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
//
//        System.out.println("to interrupt ");
//        t1.interrupt();
    }
}
