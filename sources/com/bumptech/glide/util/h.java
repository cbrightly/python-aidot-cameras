package com.bumptech.glide.util;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/* compiled from: MultiClassKey */
public class h {
    private Class<?> a;
    private Class<?> b;
    private Class<?> c;

    public h() {
    }

    public h(@NonNull Class<?> first, @NonNull Class<?> second, @Nullable Class<?> third) {
        a(first, second, third);
    }

    public void a(@NonNull Class<?> first, @NonNull Class<?> second, @Nullable Class<?> third) {
        this.a = first;
        this.b = second;
        this.c = third;
    }

    public String toString() {
        return "MultiClassKey{first=" + this.a + ", second=" + this.b + '}';
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        h that = (h) o;
        if (this.a.equals(that.a) && this.b.equals(that.b) && j.c(this.c, that.c)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int result = ((this.a.hashCode() * 31) + this.b.hashCode()) * 31;
        Class<?> cls = this.c;
        return result + (cls != null ? cls.hashCode() : 0);
    }
}
