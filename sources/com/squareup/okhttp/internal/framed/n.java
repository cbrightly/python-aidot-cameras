package com.squareup.okhttp.internal.framed;

import java.util.Arrays;

/* compiled from: Settings */
public final class n {
    private int a;
    private int b;
    private int c;
    private final int[] d = new int[10];

    /* access modifiers changed from: package-private */
    public void a() {
        this.c = 0;
        this.b = 0;
        this.a = 0;
        Arrays.fill(this.d, 0);
    }

    /* access modifiers changed from: package-private */
    public n l(int id, int idFlags, int value) {
        int[] iArr = this.d;
        if (id >= iArr.length) {
            return this;
        }
        int bit = 1 << id;
        this.a |= bit;
        if ((idFlags & 1) != 0) {
            this.b |= bit;
        } else {
            this.b &= ~bit;
        }
        if ((idFlags & 2) != 0) {
            this.c |= bit;
        } else {
            this.c &= ~bit;
        }
        iArr[id] = value;
        return this;
    }

    /* access modifiers changed from: package-private */
    public boolean i(int id) {
        if ((this.a & (1 << id)) != 0) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public int c(int id) {
        return this.d[id];
    }

    /* access modifiers changed from: package-private */
    public int b(int id) {
        int result = 0;
        if (h(id)) {
            result = 0 | 2;
        }
        if (k(id)) {
            return result | 1;
        }
        return result;
    }

    /* access modifiers changed from: package-private */
    public int m() {
        return Integer.bitCount(this.a);
    }

    /* access modifiers changed from: package-private */
    public int d() {
        if ((this.a & 2) != 0) {
            return this.d[1];
        }
        return -1;
    }

    /* access modifiers changed from: package-private */
    public int f(int defaultValue) {
        return (this.a & 16) != 0 ? this.d[4] : defaultValue;
    }

    /* access modifiers changed from: package-private */
    public int g(int defaultValue) {
        return (this.a & 32) != 0 ? this.d[5] : defaultValue;
    }

    /* access modifiers changed from: package-private */
    public int e(int defaultValue) {
        return (this.a & 128) != 0 ? this.d[7] : defaultValue;
    }

    /* access modifiers changed from: package-private */
    public boolean k(int id) {
        if ((this.b & (1 << id)) != 0) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public boolean h(int id) {
        if ((this.c & (1 << id)) != 0) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public void j(n other) {
        for (int i = 0; i < 10; i++) {
            if (other.i(i)) {
                l(i, other.b(i), other.c(i));
            }
        }
    }
}
