package com.airbnb.lottie.parser.moshi;

import java.io.Closeable;
import java.io.IOException;
import java.util.Arrays;
import okio.c;
import okio.d;
import okio.e;
import okio.f;
import okio.s;

/* compiled from: JsonReader */
public abstract class a implements Closeable {
    private static final String[] c = new String[128];
    int d;
    int[] f = new int[32];
    String[] q = new String[32];
    int[] x = new int[32];
    boolean y;
    boolean z;

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

    public abstract void c();

    public abstract void g();

    public abstract void i();

    public abstract void j();

    public abstract boolean l();

    public abstract boolean m();

    public abstract double n();

    public abstract int o();

    public abstract String r();

    public abstract String s();

    public abstract b u();

    public abstract int x(C0011a aVar);

    public abstract void z();

    static {
        for (int i = 0; i <= 31; i++) {
            c[i] = String.format("\\u%04x", new Object[]{Integer.valueOf(i)});
        }
        String[] strArr = c;
        strArr[34] = "\\\"";
        strArr[92] = "\\\\";
        strArr[9] = "\\t";
        strArr[8] = "\\b";
        strArr[10] = "\\n";
        strArr[13] = "\\r";
        strArr[12] = "\\f";
    }

    public static a t(e source) {
        return new c(source);
    }

    a() {
    }

    /* access modifiers changed from: package-private */
    public final void v(int newTop) {
        int i = this.d;
        int[] iArr = this.f;
        if (i == iArr.length) {
            if (i != 256) {
                this.f = Arrays.copyOf(iArr, iArr.length * 2);
                String[] strArr = this.q;
                this.q = (String[]) Arrays.copyOf(strArr, strArr.length * 2);
                int[] iArr2 = this.x;
                this.x = Arrays.copyOf(iArr2, iArr2.length * 2);
            } else {
                throw new JsonDataException("Nesting too deep at " + getPath());
            }
        }
        int[] iArr3 = this.f;
        int i2 = this.d;
        this.d = i2 + 1;
        iArr3[i2] = newTop;
    }

    /* access modifiers changed from: package-private */
    public final JsonEncodingException J(String message) {
        throw new JsonEncodingException(message + " at path " + getPath());
    }

    public final String getPath() {
        return b.a(this.d, this.f, this.q, this.x);
    }

    /* renamed from: com.airbnb.lottie.parser.moshi.a$a  reason: collision with other inner class name */
    /* compiled from: JsonReader */
    public static final class C0011a {
        final String[] a;
        final s b;

        private C0011a(String[] strings, s doubleQuoteSuffix) {
            this.a = strings;
            this.b = doubleQuoteSuffix;
        }

        public static C0011a a(String... strings) {
            try {
                f[] result = new f[strings.length];
                c buffer = new c();
                for (int i = 0; i < strings.length; i++) {
                    a.I(buffer, strings[i]);
                    buffer.readByte();
                    result[i] = buffer.D0();
                }
                return new C0011a((String[]) strings.clone(), s.k(result));
            } catch (IOException e) {
                throw new AssertionError(e);
            }
        }
    }

    /* access modifiers changed from: private */
    public static void I(d sink, String value) {
        String replacement;
        String[] replacements = c;
        sink.writeByte(34);
        int last = 0;
        int length = value.length();
        for (int i = 0; i < length; i++) {
            char c2 = value.charAt(i);
            if (c2 < 128) {
                replacement = replacements[c2];
                if (replacement == null) {
                }
            } else if (c2 == 8232) {
                replacement = "\\u2028";
            } else if (c2 == 8233) {
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
}
