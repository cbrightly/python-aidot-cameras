package com.android.volley;

import android.text.TextUtils;

/* compiled from: Header */
public final class e {
    private final String a;
    private final String b;

    public e(String name, String value) {
        this.a = name;
        this.b = value;
    }

    public final String a() {
        return this.a;
    }

    public final String b() {
        return this.b;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        e header = (e) o;
        if (!TextUtils.equals(this.a, header.a) || !TextUtils.equals(this.b, header.b)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return (this.a.hashCode() * 31) + this.b.hashCode();
    }

    public String toString() {
        return "Header[name=" + this.a + ",value=" + this.b + "]";
    }
}
