package com.tang.java.annotation;

public class SingletonTest {
    private static SingletonTest ourInstance = new SingletonTest();

    public static SingletonTest getInstance() {
        return ourInstance;
    }

    private SingletonTest() {
    }
}
