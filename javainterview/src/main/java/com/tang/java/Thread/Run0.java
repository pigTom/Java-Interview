package com.tang.java.Thread;

public class Run0 implements Runnable {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " ---- " + InnerClass.getInstance());
    }
}
