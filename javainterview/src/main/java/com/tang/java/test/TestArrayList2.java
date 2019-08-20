package com.tang.java.test;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

/**
 * tangdunhong
 * 2019/8/16
 * 8:32 AM
 **/
public class TestArrayList2 {

    private Semaphore semaphore = new Semaphore(2);
    private CountDownLatch latch = new CountDownLatch(2);
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
                i ++;
            }
            latch.countDown();
        }
    }

    @Test()
    public void multiThread() throws InterruptedException {
        List<Integer> list = new ArrayList<>();
        Runnable r1 = new R1(list);
        Runnable r2 = new R1(list);
        Thread a = new Thread(r1);
        Thread b = new Thread(r2);
        a.start();
        b.start();
        latch.await();
        Object[] integers = list.toArray();
//        System.out.println(integers.length);
        if (integers.length > 0)
            Arrays.sort(integers);
        System.out.println(Arrays.toString(integers));
    }

    @Test
    public void test_Sign() {
        int i = 0;
        for (; i < 798799797; i++) {

            printI(i);
        }

//        System.out.println( 1 & 0);
    }

    public void printI (int i) {
        i = i - 1;/// ^
        i |= i >>> 1;
        i |= i >>> 2;
        i |= i >>> 4;
        i |= i >>> 8;
        i |= i >>> 16;

        i++;
        System.out.println(i);
    }
}
/***
 * 100
 * 110
 * 110
 *
 * 100000
 * 110000
 * 1111
 * 11111111 111111111112

1111 1111 1111 1111 1111 1111 1111 1111 1111 1111 1111 1111 1111 1111 1111 1111


 ***/