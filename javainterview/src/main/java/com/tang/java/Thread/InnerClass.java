package com.tang.java.Thread;

import org.junit.Test;

public class InnerClass {
    class InnerA implements Runnable {
        @Override
        public void run() {
            System.out.println("come in");

            while (true) {
                try {
                    Thread.sleep(500);
                    System.out.println("while loop");

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

   @Test
    // test can not support thread of inner class
    public void startThread () {
        InnerClass.InnerA innerA = new InnerA();
        Thread thread = new Thread(innerA);
        thread.start();
    }

    public static void main(String[] args) {
        new InnerClass().startThread();
    }


    // 静态类只加载一次
    private static class Singleton {
        private static InnerClass instance;
        private static InnerClass getInstance() {
            if (Singleton.instance != null) {
                return Singleton.instance;
            }
            instance = new InnerClass();
            return instance;
        }
    }

    public static InnerClass getInstance() {
        if (Singleton.instance != null) {
            return Singleton.instance;
        }
        return Singleton.getInstance();
    }

    @Test
    public void testSingleton() {
        int size = 10;
        Run0 run0 = new Run0();
        Thread[] threadA = new Thread[size];
        for (int i = 0; i < threadA.length; i++) {
            threadA[i] = new Thread(run0);
        }
        for (Thread a : threadA) {
            a.start();
        }
    }
}

