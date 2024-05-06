package com.southdipper.teamwork.util;

import java.util.Map;

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
