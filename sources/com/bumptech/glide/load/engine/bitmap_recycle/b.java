package com.bumptech.glide.load.engine.bitmap_recycle;

/* compiled from: ArrayPool */
public interface b {
    void a(int i);

    void d();

    <T> T e(int i, Class<T> cls);

    <T> T f(int i, Class<T> cls);

    @Deprecated
    <T> void g(T t, Class<T> cls);

    <T> void put(T t);
}
