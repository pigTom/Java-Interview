package com.tang.java.Thread;

public class JoinTest extends Thread {
    private String name;

    public JoinTest(String name) {
        this.name = name;
    }
    @Override
    public void run() {
        System.out.println(name);
    }

    public static void main(String[] args) {
        int size = 10;
        Thread[] threads = new Thread[10];
        for (int i = 0; i < size; i++) {
            threads[i] = new JoinTest("Thread test " + i );
        }
        for (Thread thread : threads) {
            thread.start();
            try {
                // 当前线程等待thread，执行完后执行
                thread.join();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
