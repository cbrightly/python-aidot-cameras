package com.squareup.moshi;

import com.squareup.moshi.i;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Set;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;

/* compiled from: JsonAdapter */
public abstract class f<T> {

    /* compiled from: JsonAdapter */
    public interface d {
        @CheckReturnValue
        @Nullable
        f<?> a(Type type, Set<? extends Annotation> set, r rVar);
    }

    @CheckReturnValue
    @Nullable
    public abstract T b(i iVar);

    public abstract void i(o oVar, @Nullable T t);

    @CheckReturnValue
    @Nullable
    public final T c(String string) {
        i reader = i.v(new okio.c().writeUtf8(string));
        T result = b(reader);
        if (d() || reader.x() == i.b.END_DOCUMENT) {
            return result;
        }
        throw new JsonDataException("JSON document was not fully consumed.");
    }

    public final void j(okio.d sink, @Nullable T value) {
        i(o.t(sink), value);
    }

    @CheckReturnValue
    public final String h(@Nullable T value) {
        okio.c buffer = new okio.c();
        try {
            j(buffer, value);
            return buffer.a1();
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

    /* compiled from: JsonAdapter */
    public class a extends f<T> {
        final /* synthetic */ f a;

        a(f fVar) {
            this.a = fVar;
        }

        @Nullable
        public T b(i reader) {
            return this.a.b(reader);
        }

        public void i(o writer, @Nullable T value) {
            boolean serializeNulls = writer.n();
            writer.I(true);
            try {
                this.a.i(writer, value);
            } finally {
                writer.I(serializeNulls);
            }
        }

        /* access modifiers changed from: package-private */
        public boolean d() {
            return this.a.d();
        }

        public String toString() {
            return this.a + ".serializeNulls()";
        }
    }

    @CheckReturnValue
    public final f<T> g() {
        return new a(this);
    }

    @CheckReturnValue
    public final f<T> f() {
        if (this instanceof com.squareup.moshi.internal.a) {
            return this;
        }
        return new com.squareup.moshi.internal.a(this);
    }

    /* compiled from: JsonAdapter */
    public class b extends f<T> {
        final /* synthetic */ f a;

        b(f fVar) {
            this.a = fVar;
        }

        @Nullable
        public T b(i reader) {
            boolean lenient = reader.m();
            reader.W(true);
            try {
                return this.a.b(reader);
            } finally {
                reader.W(lenient);
            }
        }

        public void i(o writer, @Nullable T value) {
            boolean lenient = writer.o();
            writer.E(true);
            try {
                this.a.i(writer, value);
            } finally {
                writer.E(lenient);
            }
        }

        /* access modifiers changed from: package-private */
        public boolean d() {
            return true;
        }

        public String toString() {
            return this.a + ".lenient()";
        }
    }

    @CheckReturnValue
    public final f<T> e() {
        return new b(this);
    }

    /* compiled from: JsonAdapter */
    public class c extends f<T> {
        final /* synthetic */ f a;

        c(f fVar) {
            this.a = fVar;
        }

        @Nullable
        public T b(i reader) {
            boolean skipForbidden = reader.j();
            reader.T(true);
            try {
                return this.a.b(reader);
            } finally {
                reader.T(skipForbidden);
            }
        }

        public void i(o writer, @Nullable T value) {
            this.a.i(writer, value);
        }

        /* access modifiers changed from: package-private */
        public boolean d() {
            return this.a.d();
        }

        public String toString() {
            return this.a + ".failOnUnknown()";
        }
    }

    @CheckReturnValue
    public final f<T> a() {
        return new c(this);
    }

    /* access modifiers changed from: package-private */
    public boolean d() {
        return false;
    }
}
