package com.alibaba.android.arouter.utils;

import java.util.Map;

/* compiled from: MapUtils */
public class c {
    public static boolean b(Map<?, ?> map) {
        return !a(map);
    }

    public static boolean a(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }
}
