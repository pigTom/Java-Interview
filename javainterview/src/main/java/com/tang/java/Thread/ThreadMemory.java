package com.tang.java.Thread;


/**
 * tangdunhong
 * 2019/8/21
 * 10:13 AM
 **/
public class ThreadMemory{
    class MyObject {
        String name = "MyObject";
        Integer size = Integer.MAX_VALUE;
        Boolean keepRunning[] ;
        {
            keepRunning = new Boolean[10];
            for (int i = 0; i < keepRunning.length; i++) {
                keepRunning[i] = Boolean.FALSE;
            }
        }
    }


    MyObject[] objects;

    {
        objects = new MyObject[10];
        for (int i = 0; i < 10; i++) {
            objects[i] = new MyObject();
        }

    }
    Runnable r1 = () -> {
        int x = 1;
        while (!objects[4].keepRunning[0]) {
            x++;
        }
        System.out.println(x);
    };

    Runnable r2 = () -> objects[4].keepRunning[0] = Boolean.TRUE;

    public static void main(String[] args) throws InterruptedException {
        ThreadMemory tm = new ThreadMemory();
        Thread t1 = new Thread(tm.r1);
        Thread t2 = new Thread(tm.r2);

        t1.start();
        Thread.sleep(1000);
        t2.start();


        System.out.println("start: " + tm.objects[4].keepRunning[0]);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("end: " + tm.objects[4].keepRunning[0]);

    }
}
