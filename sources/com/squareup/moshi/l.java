package com.squareup.moshi;

import com.google.maps.android.BuildConfig;
import com.tencent.bugly.Bugly;
import java.io.IOException;
import javax.annotation.Nullable;
import okio.d;

/* compiled from: JsonUtf8Writer */
public final class l extends o {
    private static final String[] p1 = new String[128];
    private final d a2;
    private String p2 = ":";
    private String p3;

    static {
        for (int i = 0; i <= 31; i++) {
            p1[i] = String.format("\\u%04x", new Object[]{Integer.valueOf(i)});
        }
        String[] strArr = p1;
        strArr[34] = "\\\"";
        strArr[92] = "\\\\";
        strArr[9] = "\\t";
        strArr[8] = "\\b";
        strArr[10] = "\\n";
        strArr[13] = "\\r";
        strArr[12] = "\\f";
    }

    l(d sink) {
        if (sink != null) {
            this.a2 = sink;
            x(6);
            return;
        }
        throw new NullPointerException("sink == null");
    }

    public o a() {
        if (!this.p0) {
            d1();
            return b1(1, 2, '[');
        }
        throw new IllegalStateException("Array cannot be used as a map key in JSON at path " + getPath());
    }

    public o j() {
        return P0(1, 2, ']');
    }

    public o g() {
        if (!this.p0) {
            d1();
            return b1(3, 5, '{');
        }
        throw new IllegalStateException("Object cannot be used as a map key in JSON at path " + getPath());
    }

    public o m() {
        this.p0 = false;
        return P0(3, 5, '}');
    }

    private o b1(int empty, int nonempty, char openBracket) {
        int i = this.c;
        int i2 = this.a1;
        if (i == i2) {
            int[] iArr = this.d;
            if (iArr[i - 1] == empty || iArr[i - 1] == nonempty) {
                this.a1 = ~i2;
                return this;
            }
        }
        F0();
        i();
        x(empty);
        this.q[this.c - 1] = 0;
        this.a2.writeByte(openBracket);
        return this;
    }

    private o P0(int empty, int nonempty, char closeBracket) {
        int context = u();
        if (context != nonempty && context != empty) {
            throw new IllegalStateException("Nesting problem.");
        } else if (this.p3 == null) {
            int i = this.c;
            int i2 = this.a1;
            if (i == (~i2)) {
                this.a1 = ~i2;
                return this;
            }
            int i3 = i - 1;
            this.c = i3;
            this.f[i3] = null;
            int[] iArr = this.q;
            int i4 = i3 - 1;
            iArr[i4] = iArr[i4] + 1;
            if (context == nonempty) {
                a1();
            }
            this.a2.writeByte(closeBracket);
            return this;
        } else {
            throw new IllegalStateException("Dangling name: " + this.p3);
        }
    }

    public o r(String name) {
        if (name == null) {
            throw new NullPointerException("name == null");
        } else if (this.c != 0) {
            int context = u();
            if ((context == 3 || context == 5) && this.p3 == null && !this.p0) {
                this.p3 = name;
                this.f[this.c - 1] = name;
                return this;
            }
            throw new IllegalStateException("Nesting problem.");
        } else {
            throw new IllegalStateException("JsonWriter is closed.");
        }
    }

    private void d1() {
        if (this.p3 != null) {
            u0();
            c1(this.a2, this.p3);
            this.p3 = null;
        }
    }

    public o W(String value) {
        if (value == null) {
            return s();
        }
        if (this.p0) {
            this.p0 = false;
            return r(value);
        }
        d1();
        F0();
        c1(this.a2, value);
        int[] iArr = this.q;
        int i = this.c - 1;
        iArr[i] = iArr[i] + 1;
        return this;
    }

    public o s() {
        if (!this.p0) {
            if (this.p3 != null) {
                if (this.z) {
                    d1();
                } else {
                    this.p3 = null;
                    return this;
                }
            }
            F0();
            this.a2.writeUtf8(BuildConfig.TRAVIS);
            int[] iArr = this.q;
            int i = this.c - 1;
            iArr[i] = iArr[i] + 1;
            return this;
        }
        throw new IllegalStateException("null cannot be used as a map key in JSON at path " + getPath());
    }

    public o o0(boolean value) {
        if (!this.p0) {
            d1();
            F0();
            this.a2.writeUtf8(value ? "true" : Bugly.SDK_IS_DEV);
            int[] iArr = this.q;
            int i = this.c - 1;
            iArr[i] = iArr[i] + 1;
            return this;
        }
        throw new IllegalStateException("Boolean cannot be used as a map key in JSON at path " + getPath());
    }

    public o J(double value) {
        if (!this.y && (Double.isNaN(value) || Double.isInfinite(value))) {
            throw new IllegalArgumentException("Numeric values must be finite, but was " + value);
        } else if (this.p0) {
            this.p0 = false;
            return r(Double.toString(value));
        } else {
            d1();
            F0();
            this.a2.writeUtf8(Double.toString(value));
            int[] iArr = this.q;
            int i = this.c - 1;
            iArr[i] = iArr[i] + 1;
            return this;
        }
    }

    public o P(long value) {
        if (this.p0) {
            this.p0 = false;
            return r(Long.toString(value));
        }
        d1();
        F0();
        this.a2.writeUtf8(Long.toString(value));
        int[] iArr = this.q;
        int i = this.c - 1;
        iArr[i] = iArr[i] + 1;
        return this;
    }

    public o T(@Nullable Number value) {
        if (value == null) {
            return s();
        }
        String string = value.toString();
        if (!this.y && (string.equals("-Infinity") || string.equals("Infinity") || string.equals("NaN"))) {
            throw new IllegalArgumentException("Numeric values must be finite, but was " + value);
        } else if (this.p0) {
            this.p0 = false;
            return r(string);
        } else {
            d1();
            F0();
            this.a2.writeUtf8(string);
            int[] iArr = this.q;
            int i = this.c - 1;
            iArr[i] = iArr[i] + 1;
            return this;
        }
    }

    public void flush() {
        if (this.c != 0) {
            this.a2.flush();
            return;
        }
        throw new IllegalStateException("JsonWriter is closed.");
    }

    public void close() {
        this.a2.close();
        int size = this.c;
        if (size > 1 || (size == 1 && this.d[size - 1] != 7)) {
            throw new IOException("Incomplete document");
        }
        this.c = 0;
    }

    static void c1(d sink, String value) {
        String replacement;
        String[] replacements = p1;
        sink.writeByte(34);
        int last = 0;
        int length = value.length();
        for (int i = 0; i < length; i++) {
            char c = value.charAt(i);
            if (c < 128) {
                replacement = replacements[c];
                if (replacement == null) {
                }
            } else if (c == 8232) {
                replacement = "\\u2028";
            } else if (c == 8233) {
                replacement = "\\u2029";
            }
            if (last < i) {
                sink.writeUtf8(value, last, i);
            }
            sink.writeUtf8(replacement);
            last = i + 1;
        }
        if (last < length) {
            sink.writeUtf8(value, last, length);
        }
        sink.writeByte(34);
    }

    private void a1() {
        if (this.x != null) {
            this.a2.writeByte(10);
            int size = this.c;
            for (int i = 1; i < size; i++) {
                this.a2.writeUtf8(this.x);
            }
        }
    }

    private void u0() {
        int context = u();
        if (context == 5) {
            this.a2.writeByte(44);
        } else if (context != 3) {
            throw new IllegalStateException("Nesting problem.");
        }
        a1();
        z(4);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0024, code lost:
        r0 = 7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0036, code lost:
        a1();
        r0 = 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x003b, code lost:
        z(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x003e, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void F0() {
        /*
            r3 = this;
            int r0 = r3.u()
            switch(r0) {
                case 1: goto L_0x0036;
                case 2: goto L_0x002f;
                case 3: goto L_0x0007;
                case 4: goto L_0x0026;
                case 5: goto L_0x0007;
                case 6: goto L_0x0024;
                case 7: goto L_0x0017;
                case 8: goto L_0x0007;
                case 9: goto L_0x000f;
                default: goto L_0x0007;
            }
        L_0x0007:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "Nesting problem."
            r0.<init>(r1)
            throw r0
        L_0x000f:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "Sink from valueSink() was not closed"
            r0.<init>(r1)
            throw r0
        L_0x0017:
            boolean r0 = r3.y
            if (r0 == 0) goto L_0x001c
            goto L_0x0024
        L_0x001c:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "JSON must have only one top-level value."
            r0.<init>(r1)
            throw r0
        L_0x0024:
            r0 = 7
            goto L_0x003b
        L_0x0026:
            r0 = 5
            okio.d r1 = r3.a2
            java.lang.String r2 = r3.p2
            r1.writeUtf8(r2)
            goto L_0x003b
        L_0x002f:
            okio.d r0 = r3.a2
            r1 = 44
            r0.writeByte(r1)
        L_0x0036:
            r3.a1()
            r0 = 2
        L_0x003b:
            r3.z(r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.squareup.moshi.l.F0():void");
    }
}
