package com.squareup.okhttp;

import com.squareup.okhttp.internal.j;

/* compiled from: Challenge */
public final class g {
    private final String a;
    private final String b;

    public g(String scheme, String realm) {
        this.a = scheme;
        this.b = realm;
    }

    public String b() {
        return this.a;
    }

    public String a() {
        return this.b;
    }

    public boolean equals(Object o) {
        return (o instanceof g) && j.h(this.a, ((g) o).a) && j.h(this.b, ((g) o).b);
    }

    public int hashCode() {
        int i = 29 * 31;
        String str = this.b;
        int i2 = 0;
        int result = (i + (str != null ? str.hashCode() : 0)) * 31;
        String str2 = this.a;
        if (str2 != null) {
            i2 = str2.hashCode();
        }
        return result + i2;
    }

    public String toString() {
        return this.a + " realm=\"" + this.b + "\"";
    }
}
