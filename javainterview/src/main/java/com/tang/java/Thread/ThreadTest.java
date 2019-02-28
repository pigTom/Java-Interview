package com.tang.java.Thread;

import org.junit.Test;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class ThreadTest extends Thread {

    private String threadName;
    private Thread thread;

    public ThreadTest() {
    }

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }

    @Override
    public void run() {
        // do something
        System.out.println(threadName);
        if (thread != null) {
            thread.start();
        }
    }

    // the foolish implement of execution sequence A -> B -> C by thread.
    // Thread A call Thread B and Thread B call Thread C
    // but if Thread B execute before Thread B, then got wrong
    @Test
    public void test1() {
        ThreadTest a = new ThreadTest();
        ThreadTest b = new ThreadTest();
        ThreadTest c = new ThreadTest();
        a.setThread(b);
        a.setThreadName("A");
        b.setThreadName("B");
        c.setThreadName("C");
        b.setThread(c);
        c.setThread(null);
        a.start();
    }


    class Thread2 extends Thread {

        private int sequence;
        private Thread child;

        Thread2(int sequence, Thread child) {
            this.sequence = sequence;
            this.child = child;
        }

        @Override
        public void run() {
            try {
                enterSequenceList.add(sequence);
                while (true) {
                    if (child == null || child.getState().equals(State.TERMINATED)) {
                        break;
                    }

                    if (child.getState().equals(State.NEW)) {
                        this.sleep(100);
                    }

                    if (child.isAlive()){
                        child.join();
                        break;
                    }

                }
                executeSequenceList.add(sequence);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    // implement execution sequence a -> b -> c using join

    Set<Integer> enterSequenceList = new CopyOnWriteArraySet<>();
    Set<Integer> executeSequenceList = new CopyOnWriteArraySet<>();
    @Test
    public void test2() {
        int len1 = 0;
        int len2 = 0;
        int times = 0;
        int const_time = 30;
        while (len1 == len2 && times < const_time) {
            times ++;
            int size = 100;
            Thread2[] th_array = new Thread2[size];
            for (int i = 1; i < size; i++) {
                th_array[i] = new Thread2(i,th_array[i-1]);
            }
            for (int i = size -1; i > 0; i--) {
                th_array[i].start();
            }
            try {
                th_array[size - 1].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            System.out.println("current thread: " + Thread.activeCount());
            ThreadGroup group = Thread.currentThread().getThreadGroup();
            ThreadGroup topGroup = group;
            // 遍历线程组树，获取根线程组
//        while (group != null) {
//            topGroup = group;
//            group = group.getParent();
//        }
//            // 激活的线程数再加一倍，防止枚举时有可能刚好有动态线程生成
//            int slackSize = topGroup.activeCount() * 2;
//            Thread[] slackThreads = new Thread[slackSize];
//            // 获取根线程组下的所有线程，返回的actualSize便是最终的线程数
//            int actualSize = topGroup.enumerate(slackThreads);
//            Thread[] atualThreads = new Thread[actualSize];
//            // 复制slackThreads中有效的值到atualThreads
//            System.arraycopy(slackThreads, 0, atualThreads, 0, actualSize);
//            System.out.println("Threads size is " + atualThreads.length);
//            for (Thread thread : atualThreads) {
//                System.out.println("Thread name : " + thread.getName());
//            }


            System.out.println("enter sequence");
//        System.out.println(Arrays.toString(enterSequenceList.toArray()));
//        System.out.println(Arrays.toString(enterSequenceList.toArray()));
//        System.out.println(Arrays.toString(executeSequenceList.toArray()));
            len1 = executeSequenceList.size();
            len2 = enterSequenceList.size();
            System.out.println("execute size\t" + len1);
            System.out.println("enter   size\t" + len2);
        }

        System.out.println("----------over------");
        assert len1 == len2;
    }

}
