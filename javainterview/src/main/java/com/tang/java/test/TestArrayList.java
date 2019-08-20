package com.tang.java.test;

import org.junit.Test;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

/**
 * tangdunhong
 * 2019/8/16
 * 8:32 AM
 **/
public class TestArrayList {

    private int threadSize = 10;
    private Semaphore semaphore = new Semaphore(threadSize);
    private CountDownLatch latch = new CountDownLatch(threadSize);
    @Test
    public void deleteWhenTravel() {
        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            list.add(i);
        }


        Iterator<Integer> integerIterator = list.iterator();
        while (integerIterator.hasNext()) {
            int next = integerIterator.next();
            System.out.println(next);

            if (next == 4) {
                integerIterator.remove();
            }
        }

    }

    class R1 implements Runnable {

        private List<Integer> list;
        R1(List<Integer> list) {
            this.list = list;
        }
        @Override
        public void run() {
            int i =  0;
            while (i < 1000) {
                list.add(i);
                i++;
            }
            latch.countDown();
        }
    }

    class R2 implements Runnable {
        private List<Integer> list;

        R2(List<Integer> list) {
            this.list = list;
        }

        @Override
        public void run () {
            for (int i = 0; i < 1000; i++) {
                if (list.contains(i)) {
                    list.remove(i);
                }
            }
        }
    }
    @Test()
    public void multiThread() throws InterruptedException {
//        List<Integer> list =  new ArrayList<>();
        List<Integer> list =  Collections.synchronizedList(new ArrayList<>());
//        List<Integer> list = new CopyOnWriteArrayList<>();

        Runnable r2 = new R2(list);
        Thread t2 = new Thread(r2);

        Thread[] threads = new Thread[threadSize];
        Runnable[] rs = new Runnable[threadSize];


        for (int i = 0; i < threadSize; i++) {
            rs[i] = new R1(list);
            threads[i] = new Thread(rs[i]);
        }
        for (Thread t : threads) {
            t.start();
        }
        latch.await();

        Object[] integers = list.toArray();
        System.out.println(integers.length);
        if (integers.length > 0)
            Arrays.sort(integers);
        System.out.println(Arrays.toString(integers));
    }
}

class NoVisibility{
    private static boolean ready;
    private static int number;

    private static class Reader extends Thread{
        public void run(){
            while(!ready){
                Thread.yield();
            }
            System.out.println(number);
        }
    }
    public static void main(String[] args){
        new Reader().start();
        number = 42;
        ready = true;
    }
}
