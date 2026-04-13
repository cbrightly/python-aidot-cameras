package com.telink.util;

import com.meituan.robust.ChangeQuickRedirect;

/* compiled from: Event */
public class c<T> {
    public static ChangeQuickRedirect changeQuickRedirect;
    protected Object a;
    protected T b;
    protected a c;

    /* compiled from: Event */
    public enum a {
        Background,
        Main,
        Default;
        
        public static ChangeQuickRedirect changeQuickRedirect;
    }

    public c(Object sender, T type) {
        this(sender, type, a.Default);
    }

    public c(Object sender, T type, a threadMode) {
        this.c = a.Default;
        this.a = sender;
        this.b = type;
        this.c = threadMode;
    }

    public T b() {
        return this.b;
    }

    public a a() {
        return this.c;
    }

    public c<T> c(a mode) {
        this.c = mode;
        return this;
    }
}
