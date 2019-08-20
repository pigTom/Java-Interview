package com.tang.java.annotation;

import java.lang.reflect.Method;

/**
 * tangdunhong
 * 2019/8/14
 * 11:12 AM
 **/
@AnnoForClass
public class NewAnnotation {

    @AnnoForField(age = 4, size = 8)
    private NewAnnotation newAnnotation = new NewAnnotation();

    @AnnoForMethod("hahah")
    private void sayHello() {
        System.out.println("Hello world");
    }

    public static void main(String[] args) {

        try {
            AnnoForClass annoForClass = (AnnoForClass)NewAnnotation.class.getAnnotations()[0];
            AnnoForField forField = (AnnoForField) NewAnnotation.class.getDeclaredField("newAnnotation").getAnnotations()[0];
            System.out.println(annoForClass);
            System.out.println(""+forField.age() + forField.size());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Method method = NewAnnotation.class.getDeclaredMethod("sayHello");
            System.out.println(method.getDeclaredAnnotations()[0]);;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
