package com.alibaba.android.arouter.facade.model;

import java.util.Map;
import javax.lang.model.element.Element;

/* compiled from: RouteMeta */
public class a {
    private com.alibaba.android.arouter.facade.enums.a a;
    private Element b;
    private Class<?> c;
    private String d;
    private String e;
    private int f = -1;
    private int g;
    private Map<String, Integer> h;
    private String i;

    public a() {
    }

    public static a a(com.alibaba.android.arouter.facade.enums.a type, Class<?> destination, String path, String group, Map<String, Integer> paramsType, int priority, int extra) {
        return new a(type, (Element) null, destination, (String) null, path, group, paramsType, priority, extra);
    }

    public a(com.alibaba.android.arouter.facade.enums.a type, Element rawType, Class<?> destination, String name, String path, String group, Map<String, Integer> paramsType, int priority, int extra) {
        this.a = type;
        this.i = name;
        this.c = destination;
        this.b = rawType;
        this.d = path;
        this.e = group;
        this.h = paramsType;
        this.f = priority;
        this.g = extra;
    }

    public Map<String, Integer> e() {
        return this.h;
    }

    public com.alibaba.android.arouter.facade.enums.a h() {
        return this.a;
    }

    public a n(com.alibaba.android.arouter.facade.enums.a type) {
        this.a = type;
        return this;
    }

    public Class<?> b() {
        return this.c;
    }

    public a i(Class<?> destination) {
        this.c = destination;
        return this;
    }

    public String f() {
        return this.d;
    }

    public a l(String path) {
        this.d = path;
        return this;
    }

    public String d() {
        return this.e;
    }

    public a k(String group) {
        this.e = group;
        return this;
    }

    public int g() {
        return this.f;
    }

    public a m(int priority) {
        this.f = priority;
        return this;
    }

    public int c() {
        return this.g;
    }

    public a j(int extra) {
        this.g = extra;
        return this;
    }

    public String toString() {
        return "RouteMeta{type=" + this.a + ", rawType=" + this.b + ", destination=" + this.c + ", path='" + this.d + '\'' + ", group='" + this.e + '\'' + ", priority=" + this.f + ", extra=" + this.g + ", paramsType=" + this.h + ", name='" + this.i + '\'' + '}';
    }
}
