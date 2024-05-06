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
        Map<String, Object> map = get();
        return (String)map.get("username");
    }

    public static Integer getId() {
        Map<String, Object> map = get();
        return (Integer)map.get("id");
    }

    public static void remove() {
        Thread_LOCAL.remove();
    }
}
