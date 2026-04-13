package org.apache.http.config;

import java.nio.charset.Charset;
import java.nio.charset.CodingErrorAction;
import org.apache.http.b;

/* compiled from: ConnectionConfig */
public class a implements Cloneable {
    public static final a c = new C0144a().a();
    private final int d;
    private final int f;
    private final Charset q;
    private final CodingErrorAction x;
    private final CodingErrorAction y;
    private final c z;

    a(int bufferSize, int fragmentSizeHint, Charset charset, CodingErrorAction malformedInputAction, CodingErrorAction unmappableInputAction, c messageConstraints) {
        this.d = bufferSize;
        this.f = fragmentSizeHint;
        this.q = charset;
        this.x = malformedInputAction;
        this.y = unmappableInputAction;
        this.z = messageConstraints;
    }

    public int b() {
        return this.d;
    }

    public int d() {
        return this.f;
    }

    public Charset c() {
        return this.q;
    }

    public CodingErrorAction e() {
        return this.x;
    }

    public CodingErrorAction h() {
        return this.y;
    }

    public c f() {
        return this.z;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public a clone() {
        return (a) super.clone();
    }

    public String toString() {
        return "[bufferSize=" + this.d + ", fragmentSizeHint=" + this.f + ", charset=" + this.q + ", malformedInputAction=" + this.x + ", unmappableInputAction=" + this.y + ", messageConstraints=" + this.z + "]";
    }

    /* renamed from: org.apache.http.config.a$a  reason: collision with other inner class name */
    /* compiled from: ConnectionConfig */
    public static class C0144a {
        private int a;
        private int b = -1;
        private Charset c;
        private CodingErrorAction d;
        private CodingErrorAction e;
        private c f;

        C0144a() {
        }

        public a a() {
            Charset cs = this.c;
            if (cs == null && !(this.d == null && this.e == null)) {
                cs = b.b;
            }
            int i = this.a;
            if (i <= 0) {
                i = 8192;
            }
            int bufSize = i;
            int i2 = this.b;
            return new a(bufSize, i2 >= 0 ? i2 : bufSize, cs, this.d, this.e, this.f);
        }
    }
}
