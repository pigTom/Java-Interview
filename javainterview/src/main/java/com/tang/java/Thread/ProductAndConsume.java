package com.tang.java.Thread;

public class ProductAndConsume {

    // product and consume have the same lock, so they can notify each other
    // and execute one by one sequential
    public static void main(String[] args) {
        Product product = new Product();
        Consume consume = new Consume();

        // new a lock
        Object lock = new Object();
        product.setMyLock(lock);
        consume.setMyLock(lock);

        Thread productThread = new Thread(product);
        Thread consumeThread = new Thread(consume);

        productThread.start();
        while (productThread.getState().equals(Thread.State.NEW)) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        consumeThread.start();
    }

}

class Product implements Runnable {
    private Object myLock;


    public void setMyLock(Object lock) {
        this.myLock = lock;
    }

    @Override
    public void run() {
        while (true) {

            synchronized (myLock) {
                try {
                    myLock.notify();
                    System.out.println("I am product.");
                    Thread.sleep(500);
                    myLock.wait();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}


class Consume implements Runnable {
    private Object lock;

    @Override
    public void run() {
        while (true) {
            synchronized (lock) {
                lock.notify();
                System.out.println("I am Consume");
                try {
                    Thread.sleep(500);
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }

    }

    public void setMyLock(Object lock) {
        this.lock = lock;
    }
}
