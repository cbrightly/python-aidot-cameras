package com.squareup.okhttp.internal.framed;

/* compiled from: Header */
public final class f {
    public static final okio.f a = okio.f.encodeUtf8(":status");
    public static final okio.f b = okio.f.encodeUtf8(":method");
    public static final okio.f c = okio.f.encodeUtf8(":path");
    public static final okio.f d = okio.f.encodeUtf8(":scheme");
    public static final okio.f e = okio.f.encodeUtf8(":authority");
    public static final okio.f f = okio.f.encodeUtf8(":host");
    public static final okio.f g = okio.f.encodeUtf8(":version");
    public final okio.f h;
    public final okio.f i;
    final int j;

    public f(String name, String value) {
        this(okio.f.encodeUtf8(name), okio.f.encodeUtf8(value));
    }

    public f(okio.f name, String value) {
        this(name, okio.f.encodeUtf8(value));
    }

    public f(okio.f name, okio.f value) {
        this.h = name;
        this.i = value;
        this.j = name.size() + 32 + value.size();
    }

    public boolean equals(Object other) {
        if (!(other instanceof f)) {
            return false;
        }
        f that = (f) other;
        if (!this.h.equals(that.h) || !this.i.equals(that.i)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return (((17 * 31) + this.h.hashCode()) * 31) + this.i.hashCode();
    }

    public String toString() {
        return String.format("%s: %s", new Object[]{this.h.utf8(), this.i.utf8()});
    }
}
