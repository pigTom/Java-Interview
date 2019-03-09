package com.tang.java.class0;

class Test {
    public void print () {
        System.out.println("Hello world");
    }
}
public class ClassLoadTest {
    public static void main(String[] args) {
        try {
            // 通过类加载器加载class文件，获得该class对象
            Class<?> cla = Class.forName("com.tang.java.class0.Test");
            Test test = (Test) cla.newInstance();
            test.print();
        } catch (ClassNotFoundException | IllegalAccessException| InstantiationException e) {
            e.printStackTrace();
        }


    }
}
