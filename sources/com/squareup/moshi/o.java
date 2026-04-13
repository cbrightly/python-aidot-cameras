package com.squareup.moshi;

import java.io.Closeable;
import java.io.Flushable;
import java.util.Arrays;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;
import okio.d;

/* compiled from: JsonWriter */
public abstract class o implements Closeable, Flushable {
    int a1 = -1;
    int c = 0;
    int[] d = new int[32];
    String[] f = new String[32];
    boolean p0;
    int[] q = new int[32];
    String x;
    boolean y;
    boolean z;

    public abstract o J(double d2);

    public abstract o P(long j);

    public abstract o T(@Nullable Number number);

    public abstract o W(@Nullable String str);

    public abstract o a();

    public abstract o g();

    public abstract o j();

    public abstract o m();

    public abstract o o0(boolean z2);

    public abstract o r(String str);

    public abstract o s();

    @CheckReturnValue
    public static o t(d sink) {
        return new l(sink);
    }

    o() {
    }

    /* access modifiers changed from: package-private */
    public final int u() {
        int i = this.c;
        if (i != 0) {
            return this.d[i - 1];
        }
        throw new IllegalStateException("JsonWriter is closed.");
    }

    /* access modifiers changed from: package-private */
    public final boolean i() {
        int i = this.c;
        int[] iArr = this.d;
        if (i != iArr.length) {
            return false;
        }
        if (i != 256) {
            this.d = Arrays.copyOf(iArr, iArr.length * 2);
            String[] strArr = this.f;
            this.f = (String[]) Arrays.copyOf(strArr, strArr.length * 2);
            int[] iArr2 = this.q;
            this.q = Arrays.copyOf(iArr2, iArr2.length * 2);
            if (!(this instanceof n)) {
                return true;
            }
            ((n) this).p1 = Arrays.copyOf(((n) this).p1, ((n) this).p1.length * 2);
            return true;
        }
        throw new JsonDataException("Nesting too deep at " + getPath() + ": circular reference?");
    }

    /* access modifiers changed from: package-private */
    public final void x(int newTop) {
        int[] iArr = this.d;
        int i = this.c;
        this.c = i + 1;
        iArr[i] = newTop;
    }

    /* access modifiers changed from: package-private */
    public final void z(int topOfStack) {
        this.d[this.c - 1] = topOfStack;
    }

    public final void E(boolean lenient) {
        this.y = lenient;
    }

    @CheckReturnValue
    public final boolean o() {
        return this.y;
    }

    public final void I(boolean serializeNulls) {
        this.z = serializeNulls;
    }

    @CheckReturnValue
    public final boolean n() {
        return this.z;
    }

    public final void v() {
        int context = u();
        if (context == 5 || context == 3) {
            this.p0 = true;
            return;
        }
        throw new IllegalStateException("Nesting problem.");
    }

    @CheckReturnValue
    public final int c() {
        int context = u();
        if (context == 5 || context == 3 || context == 2 || context == 1) {
            int token = this.a1;
            this.a1 = this.c;
            return token;
        }
        throw new IllegalStateException("Nesting problem.");
    }

    public final void l(int token) {
        this.a1 = token;
    }

    @CheckReturnValue
    public final String getPath() {
        return j.a(this.c, this.d, this.f, this.q);
    }
}
