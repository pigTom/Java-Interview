package com.tang.java.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * tangdunhong
 * 2019/8/14
 * 2:17 PM
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)

public @interface AnnoForMethod {
    String value() default "annotation for method";
}
