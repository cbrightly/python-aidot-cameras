package com.blankj.utilcode.util;

import androidx.annotation.NonNull;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: GsonUtils */
public final class k {
    private static final Map<String, Gson> a = new ConcurrentHashMap();

    public static Gson f() {
        Map<String, Gson> map = a;
        Gson gsonDelegate = map.get("delegateGson");
        if (gsonDelegate != null) {
            return gsonDelegate;
        }
        Gson gsonDefault = map.get("defaultGson");
        if (gsonDefault != null) {
            return gsonDefault;
        }
        Gson gsonDefault2 = a();
        map.put("defaultGson", gsonDefault2);
        return gsonDefault2;
    }

    public static String j(Object object) {
        return i(f(), object);
    }

    public static String i(@NonNull Gson gson, Object object) {
        if (gson != null) {
            return gson.toJson(object);
        }
        throw new NullPointerException("Argument 'gson' of type Gson (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static <T> T d(String json, @NonNull Class<T> type) {
        if (type != null) {
            return b(f(), json, type);
        }
        throw new NullPointerException("Argument 'type' of type Class<T> (#1 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static <T> T e(String json, @NonNull Type type) {
        if (type != null) {
            return c(f(), json, type);
        }
        throw new NullPointerException("Argument 'type' of type Type (#1 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static <T> T b(@NonNull Gson gson, String json, @NonNull Class<T> type) {
        if (gson == null) {
            throw new NullPointerException("Argument 'gson' of type Gson (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (type != null) {
            return gson.fromJson(json, type);
        } else {
            throw new NullPointerException("Argument 'type' of type Class<T> (#2 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static <T> T c(@NonNull Gson gson, String json, @NonNull Type type) {
        if (gson == null) {
            throw new NullPointerException("Argument 'gson' of type Gson (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (type != null) {
            return gson.fromJson(json, type);
        } else {
            throw new NullPointerException("Argument 'type' of type Type (#2 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static Type h(@NonNull Type type) {
        if (type != null) {
            return TypeToken.getParameterized(List.class, type).getType();
        }
        throw new NullPointerException("Argument 'type' of type Type (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    static Gson g() {
        Map<String, Gson> map = a;
        Gson gson4LogUtils = map.get("logUtilsGson");
        if (gson4LogUtils != null) {
            return gson4LogUtils;
        }
        Gson gson4LogUtils2 = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        map.put("logUtilsGson", gson4LogUtils2);
        return gson4LogUtils2;
    }

    private static Gson a() {
        return new GsonBuilder().serializeNulls().disableHtmlEscaping().create();
    }
}
