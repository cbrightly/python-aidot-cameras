package com.blankj.utilcode.util;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import java.util.HashMap;
import java.util.Map;

@SuppressLint({"ApplySharedPref"})
/* compiled from: SPUtils */
public final class w {
    private static final Map<String, w> a = new HashMap();
    private SharedPreferences b;

    public static w a(String spName) {
        return b(spName, 0);
    }

    public static w b(String spName, int mode) {
        if (e(spName)) {
            spName = "spUtils";
        }
        Map<String, w> map = a;
        w spUtils = map.get(spName);
        if (spUtils == null) {
            synchronized (w.class) {
                spUtils = map.get(spName);
                if (spUtils == null) {
                    spUtils = new w(spName, mode);
                    map.put(spName, spUtils);
                }
            }
        }
        return spUtils;
    }

    private w(String spName, int mode) {
        this.b = f0.a().getSharedPreferences(spName, mode);
    }

    public void f(@NonNull String key, String value) {
        if (key != null) {
            g(key, value, false);
            return;
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public void g(@NonNull String key, String value, boolean isCommit) {
        if (key == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (isCommit) {
            this.b.edit().putString(key, value).commit();
        } else {
            this.b.edit().putString(key, value).apply();
        }
    }

    public String c(@NonNull String key) {
        if (key != null) {
            return d(key, "");
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public String d(@NonNull String key, String defaultValue) {
        if (key != null) {
            return this.b.getString(key, defaultValue);
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    private static boolean e(String s) {
        if (s == null) {
            return true;
        }
        int len = s.length();
        for (int i = 0; i < len; i++) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
