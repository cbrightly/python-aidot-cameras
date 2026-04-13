package com.blankj.utilcode.util;

import android.annotation.SuppressLint;
import androidx.annotation.NonNull;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/* compiled from: TimeUtils */
public final class d0 {
    private static final ThreadLocal<Map<String, SimpleDateFormat>> a = new a();
    private static final String[] b = {"猴", "鸡", "狗", "猪", "鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊"};
    private static final int[] c = {20, 19, 21, 21, 21, 22, 23, 23, 23, 24, 23, 22};
    private static final String[] d = {"水瓶座", "双鱼座", "白羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "魔羯座"};

    /* compiled from: TimeUtils */
    public static final class a extends ThreadLocal<Map<String, SimpleDateFormat>> {
        a() {
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public Map<String, SimpleDateFormat> initialValue() {
            return new HashMap();
        }
    }

    private static SimpleDateFormat a() {
        return e("yyyy-MM-dd HH:mm:ss");
    }

    @SuppressLint({"SimpleDateFormat"})
    public static SimpleDateFormat e(String pattern) {
        Map<String, SimpleDateFormat> sdfMap = a.get();
        SimpleDateFormat simpleDateFormat = sdfMap.get(pattern);
        if (simpleDateFormat != null) {
            return simpleDateFormat;
        }
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(pattern);
        sdfMap.put(pattern, simpleDateFormat2);
        return simpleDateFormat2;
    }

    public static String f(long millis, @NonNull DateFormat format) {
        if (format != null) {
            return format.format(new Date(millis));
        }
        throw new NullPointerException("Argument 'format' of type DateFormat (#1 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static long b() {
        return System.currentTimeMillis();
    }

    public static String c() {
        return f(System.currentTimeMillis(), a());
    }

    public static String d(@NonNull DateFormat format) {
        if (format != null) {
            return f(System.currentTimeMillis(), format);
        }
        throw new NullPointerException("Argument 'format' of type DateFormat (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }
}
