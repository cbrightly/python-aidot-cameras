package retrofit2;

import com.didichuxing.doraemonkit.okgo.model.HttpHeaders;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Nullable;
import okhttp3.c0;
import okhttp3.u;
import okhttp3.y;

/* compiled from: ParameterHandler */
public abstract class o<T> {
    /* access modifiers changed from: package-private */
    public abstract void a(q qVar, @Nullable T t);

    o() {
    }

    /* compiled from: ParameterHandler */
    public class a extends o<Iterable<T>> {
        a() {
        }

        /* access modifiers changed from: package-private */
        /* renamed from: d */
        public void a(q builder, @Nullable Iterable<T> values) {
            if (values != null) {
                for (T value : values) {
                    o.this.a(builder, value);
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public final o<Iterable<T>> c() {
        return new a();
    }

    /* compiled from: ParameterHandler */
    public class b extends o<Object> {
        b() {
        }

        /* access modifiers changed from: package-private */
        public void a(q builder, @Nullable Object values) {
            if (values != null) {
                int size = Array.getLength(values);
                for (int i = 0; i < size; i++) {
                    o.this.a(builder, Array.get(values, i));
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public final o<Object> b() {
        return new b();
    }

    /* compiled from: ParameterHandler */
    public static final class p extends o<Object> {
        private final Method a;
        private final int b;

        p(Method method, int p) {
            this.a = method;
            this.b = p;
        }

        /* access modifiers changed from: package-private */
        public void a(q builder, @Nullable Object value) {
            if (value != null) {
                builder.m(value);
                return;
            }
            throw x.o(this.a, this.b, "@Url parameter is null.", new Object[0]);
        }
    }

    /* compiled from: ParameterHandler */
    public static final class f<T> extends o<T> {
        private final String a;
        private final h<T, String> b;

        f(String name, h<T, String> valueConverter) {
            Objects.requireNonNull(name, "name == null");
            this.a = name;
            this.b = valueConverter;
        }

        /* access modifiers changed from: package-private */
        public void a(q builder, @Nullable T value) {
            String headerValue;
            if (value != null && (headerValue = this.b.convert(value)) != null) {
                builder.b(this.a, headerValue);
            }
        }
    }

    /* compiled from: ParameterHandler */
    public static final class k<T> extends o<T> {
        private final Method a;
        private final int b;
        private final String c;
        private final h<T, String> d;
        private final boolean e;

        k(Method method, int p, String name, h<T, String> valueConverter, boolean encoded) {
            this.a = method;
            this.b = p;
            Objects.requireNonNull(name, "name == null");
            this.c = name;
            this.d = valueConverter;
            this.e = encoded;
        }

        /* access modifiers changed from: package-private */
        public void a(q builder, @Nullable T value) {
            if (value != null) {
                builder.f(this.c, this.d.convert(value), this.e);
                return;
            }
            Method method = this.a;
            int i = this.b;
            throw x.o(method, i, "Path parameter \"" + this.c + "\" value must not be null.", new Object[0]);
        }
    }

    /* compiled from: ParameterHandler */
    public static final class l<T> extends o<T> {
        private final String a;
        private final h<T, String> b;
        private final boolean c;

        l(String name, h<T, String> valueConverter, boolean encoded) {
            Objects.requireNonNull(name, "name == null");
            this.a = name;
            this.b = valueConverter;
            this.c = encoded;
        }

        /* access modifiers changed from: package-private */
        public void a(q builder, @Nullable T value) {
            String queryValue;
            if (value != null && (queryValue = this.b.convert(value)) != null) {
                builder.g(this.a, queryValue, this.c);
            }
        }
    }

    /* compiled from: ParameterHandler */
    public static final class n<T> extends o<T> {
        private final h<T, String> a;
        private final boolean b;

        n(h<T, String> nameConverter, boolean encoded) {
            this.a = nameConverter;
            this.b = encoded;
        }

        /* access modifiers changed from: package-private */
        public void a(q builder, @Nullable T value) {
            if (value != null) {
                builder.g(this.a.convert(value), (String) null, this.b);
            }
        }
    }

    /* compiled from: ParameterHandler */
    public static final class m<T> extends o<Map<String, T>> {
        private final Method a;
        private final int b;
        private final h<T, String> c;
        private final boolean d;

        m(Method method, int p, h<T, String> valueConverter, boolean encoded) {
            this.a = method;
            this.b = p;
            this.c = valueConverter;
            this.d = encoded;
        }

        /* access modifiers changed from: package-private */
        /* renamed from: d */
        public void a(q builder, @Nullable Map<String, T> value) {
            if (value != null) {
                for (Map.Entry<String, T> entry : value.entrySet()) {
                    String entryKey = entry.getKey();
                    if (entryKey != null) {
                        T entryValue = entry.getValue();
                        if (entryValue != null) {
                            String convertedEntryValue = this.c.convert(entryValue);
                            if (convertedEntryValue != null) {
                                builder.g(entryKey, convertedEntryValue, this.d);
                            } else {
                                Method method = this.a;
                                int i = this.b;
                                throw x.o(method, i, "Query map value '" + entryValue + "' converted to null by " + this.c.getClass().getName() + " for key '" + entryKey + "'.", new Object[0]);
                            }
                        } else {
                            Method method2 = this.a;
                            int i2 = this.b;
                            throw x.o(method2, i2, "Query map contained null value for key '" + entryKey + "'.", new Object[0]);
                        }
                    } else {
                        throw x.o(this.a, this.b, "Query map contained null key.", new Object[0]);
                    }
                }
                return;
            }
            throw x.o(this.a, this.b, "Query map was null", new Object[0]);
        }
    }

    /* compiled from: ParameterHandler */
    public static final class g<T> extends o<Map<String, T>> {
        private final Method a;
        private final int b;
        private final h<T, String> c;

        g(Method method, int p, h<T, String> valueConverter) {
            this.a = method;
            this.b = p;
            this.c = valueConverter;
        }

        /* access modifiers changed from: package-private */
        /* renamed from: d */
        public void a(q builder, @Nullable Map<String, T> value) {
            if (value != null) {
                for (Map.Entry<String, T> entry : value.entrySet()) {
                    String headerName = entry.getKey();
                    if (headerName != null) {
                        T headerValue = entry.getValue();
                        if (headerValue != null) {
                            builder.b(headerName, this.c.convert(headerValue));
                        } else {
                            Method method = this.a;
                            int i = this.b;
                            throw x.o(method, i, "Header map contained null value for key '" + headerName + "'.", new Object[0]);
                        }
                    } else {
                        throw x.o(this.a, this.b, "Header map contained null key.", new Object[0]);
                    }
                }
                return;
            }
            throw x.o(this.a, this.b, "Header map was null.", new Object[0]);
        }
    }

    /* compiled from: ParameterHandler */
    public static final class h extends o<u> {
        private final Method a;
        private final int b;

        h(Method method, int p) {
            this.a = method;
            this.b = p;
        }

        /* access modifiers changed from: package-private */
        /* renamed from: d */
        public void a(q builder, @Nullable u headers) {
            if (headers != null) {
                builder.c(headers);
                return;
            }
            throw x.o(this.a, this.b, "Headers parameter must not be null.", new Object[0]);
        }
    }

    /* compiled from: ParameterHandler */
    public static final class d<T> extends o<T> {
        private final String a;
        private final h<T, String> b;
        private final boolean c;

        d(String name, h<T, String> valueConverter, boolean encoded) {
            Objects.requireNonNull(name, "name == null");
            this.a = name;
            this.b = valueConverter;
            this.c = encoded;
        }

        /* access modifiers changed from: package-private */
        public void a(q builder, @Nullable T value) {
            String fieldValue;
            if (value != null && (fieldValue = this.b.convert(value)) != null) {
                builder.a(this.a, fieldValue, this.c);
            }
        }
    }

    /* compiled from: ParameterHandler */
    public static final class e<T> extends o<Map<String, T>> {
        private final Method a;
        private final int b;
        private final h<T, String> c;
        private final boolean d;

        e(Method method, int p, h<T, String> valueConverter, boolean encoded) {
            this.a = method;
            this.b = p;
            this.c = valueConverter;
            this.d = encoded;
        }

        /* access modifiers changed from: package-private */
        /* renamed from: d */
        public void a(q builder, @Nullable Map<String, T> value) {
            if (value != null) {
                for (Map.Entry<String, T> entry : value.entrySet()) {
                    String entryKey = entry.getKey();
                    if (entryKey != null) {
                        T entryValue = entry.getValue();
                        if (entryValue != null) {
                            String fieldEntry = this.c.convert(entryValue);
                            if (fieldEntry != null) {
                                builder.a(entryKey, fieldEntry, this.d);
                            } else {
                                Method method = this.a;
                                int i = this.b;
                                throw x.o(method, i, "Field map value '" + entryValue + "' converted to null by " + this.c.getClass().getName() + " for key '" + entryKey + "'.", new Object[0]);
                            }
                        } else {
                            Method method2 = this.a;
                            int i2 = this.b;
                            throw x.o(method2, i2, "Field map contained null value for key '" + entryKey + "'.", new Object[0]);
                        }
                    } else {
                        throw x.o(this.a, this.b, "Field map contained null key.", new Object[0]);
                    }
                }
                return;
            }
            throw x.o(this.a, this.b, "Field map was null.", new Object[0]);
        }
    }

    /* compiled from: ParameterHandler */
    public static final class i<T> extends o<T> {
        private final Method a;
        private final int b;
        private final u c;
        private final h<T, c0> d;

        i(Method method, int p, u headers, h<T, c0> converter) {
            this.a = method;
            this.b = p;
            this.c = headers;
            this.d = converter;
        }

        /* access modifiers changed from: package-private */
        public void a(q builder, @Nullable T value) {
            if (value != null) {
                try {
                    builder.d(this.c, this.d.convert(value));
                } catch (IOException e) {
                    Method method = this.a;
                    int i = this.b;
                    throw x.o(method, i, "Unable to convert " + value + " to RequestBody", e);
                }
            }
        }
    }

    /* renamed from: retrofit2.o$o  reason: collision with other inner class name */
    /* compiled from: ParameterHandler */
    public static final class C0491o extends o<y.c> {
        static final C0491o a = new C0491o();

        private C0491o() {
        }

        /* access modifiers changed from: package-private */
        /* renamed from: d */
        public void a(q builder, @Nullable y.c value) {
            if (value != null) {
                builder.e(value);
            }
        }
    }

    /* compiled from: ParameterHandler */
    public static final class j<T> extends o<Map<String, T>> {
        private final Method a;
        private final int b;
        private final h<T, c0> c;
        private final String d;

        j(Method method, int p, h<T, c0> valueConverter, String transferEncoding) {
            this.a = method;
            this.b = p;
            this.c = valueConverter;
            this.d = transferEncoding;
        }

        /* access modifiers changed from: package-private */
        /* renamed from: d */
        public void a(q builder, @Nullable Map<String, T> value) {
            if (value != null) {
                for (Map.Entry<String, T> entry : value.entrySet()) {
                    String entryKey = entry.getKey();
                    if (entryKey != null) {
                        T entryValue = entry.getValue();
                        if (entryValue != null) {
                            builder.d(u.g(HttpHeaders.HEAD_KEY_CONTENT_DISPOSITION, "form-data; name=\"" + entryKey + "\"", "Content-Transfer-Encoding", this.d), this.c.convert(entryValue));
                        } else {
                            Method method = this.a;
                            int i = this.b;
                            throw x.o(method, i, "Part map contained null value for key '" + entryKey + "'.", new Object[0]);
                        }
                    } else {
                        throw x.o(this.a, this.b, "Part map contained null key.", new Object[0]);
                    }
                }
                return;
            }
            throw x.o(this.a, this.b, "Part map was null.", new Object[0]);
        }
    }

    /* compiled from: ParameterHandler */
    public static final class c<T> extends o<T> {
        private final Method a;
        private final int b;
        private final h<T, c0> c;

        c(Method method, int p, h<T, c0> converter) {
            this.a = method;
            this.b = p;
            this.c = converter;
        }

        /* access modifiers changed from: package-private */
        public void a(q builder, @Nullable T value) {
            if (value != null) {
                try {
                    builder.l(this.c.convert(value));
                } catch (IOException e) {
                    Method method = this.a;
                    int i = this.b;
                    throw x.p(method, e, i, "Unable to convert " + value + " to RequestBody", new Object[0]);
                }
            } else {
                throw x.o(this.a, this.b, "Body parameter value must not be null.", new Object[0]);
            }
        }
    }

    /* compiled from: ParameterHandler */
    public static final class q<T> extends o<T> {
        final Class<T> a;

        q(Class<T> cls) {
            this.a = cls;
        }

        /* access modifiers changed from: package-private */
        public void a(q builder, @Nullable T value) {
            builder.h(this.a, value);
        }
    }
}
