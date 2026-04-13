package retrofit2;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import javax.annotation.Nullable;
import kotlin.x;
import okhttp3.c0;
import okhttp3.e0;
import retrofit2.h;
import retrofit2.http.w;

/* compiled from: BuiltInConverters */
public final class c extends h.a {
    private boolean a = true;

    c() {
    }

    @Nullable
    public h<e0, ?> responseBodyConverter(Type type, Annotation[] annotations, t retrofit) {
        if (type == e0.class) {
            if (x.l(annotations, w.class)) {
                return C0489c.a;
            }
            return a.a;
        } else if (type == Void.class) {
            return f.a;
        } else {
            if (!this.a || type != x.class) {
                return null;
            }
            try {
                return e.a;
            } catch (NoClassDefFoundError e2) {
                this.a = false;
                return null;
            }
        }
    }

    @Nullable
    public h<?, c0> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, t retrofit) {
        if (c0.class.isAssignableFrom(x.h(type))) {
            return b.a;
        }
        return null;
    }

    /* compiled from: BuiltInConverters */
    public static final class f implements h<e0, Void> {
        static final f a = new f();

        f() {
        }

        /* renamed from: a */
        public Void convert(e0 value) {
            value.close();
            return null;
        }
    }

    /* compiled from: BuiltInConverters */
    public static final class e implements h<e0, x> {
        static final e a = new e();

        e() {
        }

        /* renamed from: a */
        public x convert(e0 value) {
            value.close();
            return x.a;
        }
    }

    /* compiled from: BuiltInConverters */
    public static final class b implements h<c0, c0> {
        static final b a = new b();

        b() {
        }

        /* renamed from: a */
        public c0 convert(c0 value) {
            return value;
        }
    }

    /* renamed from: retrofit2.c$c  reason: collision with other inner class name */
    /* compiled from: BuiltInConverters */
    public static final class C0489c implements h<e0, e0> {
        static final C0489c a = new C0489c();

        C0489c() {
        }

        /* renamed from: a */
        public e0 convert(e0 value) {
            return value;
        }
    }

    /* compiled from: BuiltInConverters */
    public static final class a implements h<e0, e0> {
        static final a a = new a();

        a() {
        }

        /* renamed from: a */
        public e0 convert(e0 value) {
            try {
                return x.a(value);
            } finally {
                value.close();
            }
        }
    }

    /* compiled from: BuiltInConverters */
    public static final class d implements h<Object, String> {
        static final d a = new d();

        d() {
        }

        /* renamed from: a */
        public String convert(Object value) {
            return value.toString();
        }
    }
}
