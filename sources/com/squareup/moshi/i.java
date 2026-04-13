package com.squareup.moshi;

import java.io.Closeable;
import java.io.IOException;
import java.util.Arrays;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;
import okio.c;
import okio.e;
import okio.f;
import okio.s;

/* compiled from: JsonReader */
public abstract class i implements Closeable {
    int c;
    int[] d;
    String[] f;
    int[] q;
    boolean x;
    boolean y;

    /* compiled from: JsonReader */
    public enum b {
        BEGIN_ARRAY,
        END_ARRAY,
        BEGIN_OBJECT,
        END_OBJECT,
        NAME,
        STRING,
        NUMBER,
        BOOLEAN,
        NULL,
        END_DOCUMENT
    }

    public abstract void E();

    @CheckReturnValue
    public abstract int J(a aVar);

    @CheckReturnValue
    public abstract int P(a aVar);

    public abstract void a();

    public abstract void c();

    public abstract void g();

    public abstract void i();

    @CheckReturnValue
    public abstract boolean l();

    public abstract boolean n();

    public abstract double o();

    public abstract void o0();

    public abstract int r();

    public abstract long s();

    @Nullable
    public abstract <T> T t();

    public abstract String u();

    public abstract void u0();

    @CheckReturnValue
    public abstract b x();

    @CheckReturnValue
    public abstract i z();

    @CheckReturnValue
    public static i v(e source) {
        return new k(source);
    }

    i() {
        this.d = new int[32];
        this.f = new String[32];
        this.q = new int[32];
    }

    i(i copyFrom) {
        this.c = copyFrom.c;
        this.d = (int[]) copyFrom.d.clone();
        this.f = (String[]) copyFrom.f.clone();
        this.q = (int[]) copyFrom.q.clone();
        this.x = copyFrom.x;
        this.y = copyFrom.y;
    }

    /* access modifiers changed from: package-private */
    public final void I(int newTop) {
        int i = this.c;
        int[] iArr = this.d;
        if (i == iArr.length) {
            if (i != 256) {
                this.d = Arrays.copyOf(iArr, iArr.length * 2);
                String[] strArr = this.f;
                this.f = (String[]) Arrays.copyOf(strArr, strArr.length * 2);
                int[] iArr2 = this.q;
                this.q = Arrays.copyOf(iArr2, iArr2.length * 2);
            } else {
                throw new JsonDataException("Nesting too deep at " + getPath());
            }
        }
        int[] iArr3 = this.d;
        int i2 = this.c;
        this.c = i2 + 1;
        iArr3[i2] = newTop;
    }

    /* access modifiers changed from: package-private */
    public final JsonEncodingException F0(String message) {
        throw new JsonEncodingException(message + " at path " + getPath());
    }

    public final void W(boolean lenient) {
        this.x = lenient;
    }

    @CheckReturnValue
    public final boolean m() {
        return this.x;
    }

    public final void T(boolean failOnUnknown) {
        this.y = failOnUnknown;
    }

    @CheckReturnValue
    public final boolean j() {
        return this.y;
    }

    @CheckReturnValue
    public final String getPath() {
        return j.a(this.c, this.d, this.f, this.q);
    }

    /* compiled from: JsonReader */
    public static final class a {
        final String[] a;
        final s b;

        private a(String[] strings, s doubleQuoteSuffix) {
            this.a = strings;
            this.b = doubleQuoteSuffix;
        }

        @CheckReturnValue
        public static a a(String... strings) {
            try {
                f[] result = new f[strings.length];
                c buffer = new c();
                for (int i = 0; i < strings.length; i++) {
                    l.c1(buffer, strings[i]);
                    buffer.readByte();
                    result[i] = buffer.D0();
                }
                return new a((String[]) strings.clone(), s.k(result));
            } catch (IOException e) {
                throw new AssertionError(e);
            }
        }
    }
}
