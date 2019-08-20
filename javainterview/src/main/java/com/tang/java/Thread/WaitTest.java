package com.tang.java.Thread;

import org.junit.Test;

public class WaitTest extends Thread {
    private Object object;

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public void setMyName(String name) {
        this.myName = name;
    }

    private String myName;
    @Override
    public void run() {

        if (object != null) {
            synchronized (object) {
                System.out.println(myName + " 获得该锁");
                try {
                    // 释放该监控锁, 进入 WAITING_TIMED 状态
                    object.wait(3000);


                    // 睡眠后该线程不会释放该监控锁，所以别的想要拿到该锁的线程只能等待
                    // 睡眠后该线程变成WAITING_TIMED 状态
//                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(myName + " 释放该锁");
            }
        }

        System.out.println(myName + "结束");
    }

    @Test
    public void test1() {
        WaitTest a = new WaitTest();
        WaitTest b = new WaitTest();
        Object o = new Object();
        // thread a cannot be wakened
        a.setObject(o);
        a.start();
        b.start();
        try {
            a.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("current thread: " + Thread.activeCount());
        ThreadGroup group = Thread.currentThread().getThreadGroup();
        ThreadGroup topGroup = group;
        // 激活的线程数再加一倍，防止枚举时有可能刚好有动态线程生成
        int slackSize = topGroup.activeCount() * 2;
        Thread[] slackThreads = new Thread[slackSize];
        // 获取根线程组下的所有线程，返回的actualSize便是最终的线程数
        int actualSize = topGroup.enumerate(slackThreads);
        Thread[] atualThreads = new Thread[actualSize];
        // 复制slackThreads中有效的值到atualThreads
        System.arraycopy(slackThreads, 0, atualThreads, 0, actualSize);
        System.out.println("Threads size is " + atualThreads.length);
        for (Thread thread : atualThreads) {
            System.out.println("Thread name : " + thread.getName());
        }
    }


    @Test
    public void testSleep() {
        //
        int size = 5;
        WaitTest[] tests = new WaitTest[size];
        Object lock = new Object();
        for (int i = 0; i < size; i ++) {
            tests[i] = new WaitTest();
            tests[i].setObject(lock);
            tests[i].setMyName(i+"");
        }

        for (Thread thread : tests) {
            thread.start();
        }
    }

    public static void main(String[] args) {
        //
        int size = 5;
        WaitTest[] tests = new WaitTest[size];
        Object lock = new Object();
        for (int i = 0; i < size; i ++) {
            tests[i] = new WaitTest();
            tests[i].setMyName(""+i);
            tests[i].setObject(lock);
        }

        for (Thread thread : tests) {
            thread.start();
        }
    }



}
