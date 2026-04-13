package com.squareup.moshi.internal;

import com.squareup.moshi.f;
import com.squareup.moshi.i;
import com.squareup.moshi.o;
import javax.annotation.Nullable;

/* compiled from: NullSafeJsonAdapter */
public final class a<T> extends f<T> {
    private final f<T> a;

    public a(f<T> delegate) {
        this.a = delegate;
    }

    @Nullable
    public T b(i reader) {
        if (reader.x() == i.b.NULL) {
            return reader.t();
        }
        return this.a.b(reader);
    }

    public void i(o writer, @Nullable T value) {
        if (value == null) {
            writer.s();
        } else {
            this.a.i(writer, value);
        }
    }

    public String toString() {
        return this.a + ".nullSafe()";
    }
}
