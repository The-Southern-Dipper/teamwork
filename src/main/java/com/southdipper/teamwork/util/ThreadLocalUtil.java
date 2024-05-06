package com.southdipper.teamwork.util;

/*
负责人：张永祥
每一个请求都会使用一个线程来处理，ThreadLocal是一个线程变量，且线程安全
什么是线程变量？简单理解为：代码写在同一个地方，但用不同的线程执行它的值会不同。
 */
public class ThreadLocalUtil {
    private static final ThreadLocal Thread_LOCAL = new ThreadLocal();
    public static <T> T get() {
        return (T) Thread_LOCAL.get();
    }

    public static void set(Object obj) {
        Thread_LOCAL.set(obj);
    }

    public static String getUsername() {
        String token = get();
        return JwtUtil.getUsernameFromToken(token);
    }

    public static Integer getId() {
        String token = get();
        return JwtUtil.getIdFromToken(token);
    }

    public static void remove() {
        Thread_LOCAL.remove();
    }
}
