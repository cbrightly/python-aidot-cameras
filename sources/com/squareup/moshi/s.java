package com.squareup.moshi;

import com.squareup.moshi.f;
import com.squareup.moshi.i;
import io.netty.util.internal.StringUtil;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* compiled from: StandardJsonAdapters */
public final class s {
    public static final f.d a = new c();
    static final f<Boolean> b = new d();
    static final f<Byte> c = new e();
    static final f<Character> d = new f();
    static final f<Double> e = new g();
    static final f<Float> f = new h();
    static final f<Integer> g = new i();
    static final f<Long> h = new j();
    static final f<Short> i = new k();
    static final f<String> j = new a();

    /* compiled from: StandardJsonAdapters */
    public class c implements f.d {
        c() {
        }

        public f<?> a(Type type, Set<? extends Annotation> annotations, r moshi) {
            if (!annotations.isEmpty()) {
                return null;
            }
            if (type == Boolean.TYPE) {
                return s.b;
            }
            if (type == Byte.TYPE) {
                return s.c;
            }
            if (type == Character.TYPE) {
                return s.d;
            }
            if (type == Double.TYPE) {
                return s.e;
            }
            if (type == Float.TYPE) {
                return s.f;
            }
            if (type == Integer.TYPE) {
                return s.g;
            }
            if (type == Long.TYPE) {
                return s.h;
            }
            if (type == Short.TYPE) {
                return s.i;
            }
            if (type == Boolean.class) {
                return s.b.f();
            }
            if (type == Byte.class) {
                return s.c.f();
            }
            if (type == Character.class) {
                return s.d.f();
            }
            if (type == Double.class) {
                return s.e.f();
            }
            if (type == Float.class) {
                return s.f.f();
            }
            if (type == Integer.class) {
                return s.g.f();
            }
            if (type == Long.class) {
                return s.h.f();
            }
            if (type == Short.class) {
                return s.i.f();
            }
            if (type == String.class) {
                return s.j.f();
            }
            if (type == Object.class) {
                return new m(moshi).f();
            }
            Class<?> rawType = t.g(type);
            JsonAdapter<?> generatedAdapter = com.squareup.moshi.internal.b.d(moshi, type, rawType);
            if (generatedAdapter != null) {
                return generatedAdapter;
            }
            if (rawType.isEnum()) {
                return new l(rawType).f();
            }
            return null;
        }
    }

    static int a(i reader, String typeMessage, int min, int max) {
        int value = reader.r();
        if (value >= min && value <= max) {
            return value;
        }
        throw new JsonDataException(String.format("Expected %s but was %s at path %s", new Object[]{typeMessage, Integer.valueOf(value), reader.getPath()}));
    }

    /* compiled from: StandardJsonAdapters */
    public class d extends f<Boolean> {
        d() {
        }

        /* renamed from: k */
        public Boolean b(i reader) {
            return Boolean.valueOf(reader.n());
        }

        /* renamed from: l */
        public void i(o writer, Boolean value) {
            writer.o0(value.booleanValue());
        }

        public String toString() {
            return "JsonAdapter(Boolean)";
        }
    }

    /* compiled from: StandardJsonAdapters */
    public class e extends f<Byte> {
        e() {
        }

        /* renamed from: k */
        public Byte b(i reader) {
            return Byte.valueOf((byte) s.a(reader, "a byte", -128, 255));
        }

        /* renamed from: l */
        public void i(o writer, Byte value) {
            writer.P((long) (value.intValue() & 255));
        }

        public String toString() {
            return "JsonAdapter(Byte)";
        }
    }

    /* compiled from: StandardJsonAdapters */
    public class f extends f<Character> {
        f() {
        }

        /* renamed from: k */
        public Character b(i reader) {
            String value = reader.u();
            if (value.length() <= 1) {
                return Character.valueOf(value.charAt(0));
            }
            throw new JsonDataException(String.format("Expected %s but was %s at path %s", new Object[]{"a char", StringUtil.DOUBLE_QUOTE + value + StringUtil.DOUBLE_QUOTE, reader.getPath()}));
        }

        /* renamed from: l */
        public void i(o writer, Character value) {
            writer.W(value.toString());
        }

        public String toString() {
            return "JsonAdapter(Character)";
        }
    }

    /* compiled from: StandardJsonAdapters */
    public class g extends f<Double> {
        g() {
        }

        /* renamed from: k */
        public Double b(i reader) {
            return Double.valueOf(reader.o());
        }

        /* renamed from: l */
        public void i(o writer, Double value) {
            writer.J(value.doubleValue());
        }

        public String toString() {
            return "JsonAdapter(Double)";
        }
    }

    /* compiled from: StandardJsonAdapters */
    public class h extends f<Float> {
        h() {
        }

        /* renamed from: k */
        public Float b(i reader) {
            float value = (float) reader.o();
            if (reader.m() || !Float.isInfinite(value)) {
                return Float.valueOf(value);
            }
            throw new JsonDataException("JSON forbids NaN and infinities: " + value + " at path " + reader.getPath());
        }

        /* renamed from: l */
        public void i(o writer, Float value) {
            if (value != null) {
                writer.T(value);
                return;
            }
            throw new NullPointerException();
        }

        public String toString() {
            return "JsonAdapter(Float)";
        }
    }

    /* compiled from: StandardJsonAdapters */
    public class i extends f<Integer> {
        i() {
        }

        /* renamed from: k */
        public Integer b(i reader) {
            return Integer.valueOf(reader.r());
        }

        /* renamed from: l */
        public void i(o writer, Integer value) {
            writer.P((long) value.intValue());
        }

        public String toString() {
            return "JsonAdapter(Integer)";
        }
    }

    /* compiled from: StandardJsonAdapters */
    public class j extends f<Long> {
        j() {
        }

        /* renamed from: k */
        public Long b(i reader) {
            return Long.valueOf(reader.s());
        }

        /* renamed from: l */
        public void i(o writer, Long value) {
            writer.P(value.longValue());
        }

        public String toString() {
            return "JsonAdapter(Long)";
        }
    }

    /* compiled from: StandardJsonAdapters */
    public class k extends f<Short> {
        k() {
        }

        /* renamed from: k */
        public Short b(i reader) {
            return Short.valueOf((short) s.a(reader, "a short", -32768, 32767));
        }

        /* renamed from: l */
        public void i(o writer, Short value) {
            writer.P((long) value.intValue());
        }

        public String toString() {
            return "JsonAdapter(Short)";
        }
    }

    /* compiled from: StandardJsonAdapters */
    public class a extends f<String> {
        a() {
        }

        /* renamed from: k */
        public String b(i reader) {
            return reader.u();
        }

        /* renamed from: l */
        public void i(o writer, String value) {
            writer.W(value);
        }

        public String toString() {
            return "JsonAdapter(String)";
        }
    }

    /* compiled from: StandardJsonAdapters */
    public static final class l<T extends Enum<T>> extends f<T> {
        private final Class<T> a;
        private final String[] b;
        private final T[] c;
        private final i.a d;

        l(Class<T> enumType) {
            this.a = enumType;
            try {
                T[] tArr = (Enum[]) enumType.getEnumConstants();
                this.c = tArr;
                this.b = new String[tArr.length];
                int i = 0;
                while (true) {
                    T[] tArr2 = this.c;
                    if (i < tArr2.length) {
                        T constant = tArr2[i];
                        e annotation = (e) enumType.getField(constant.name()).getAnnotation(e.class);
                        this.b[i] = annotation != null ? annotation.name() : constant.name();
                        i++;
                    } else {
                        this.d = i.a.a(this.b);
                        return;
                    }
                }
            } catch (NoSuchFieldException e) {
                throw new AssertionError("Missing field in " + enumType.getName(), e);
            }
        }

        /* renamed from: k */
        public T b(i reader) {
            int index = reader.P(this.d);
            if (index != -1) {
                return this.c[index];
            }
            String path = reader.getPath();
            String name = reader.u();
            throw new JsonDataException("Expected one of " + Arrays.asList(this.b) + " but was " + name + " at path " + path);
        }

        /* renamed from: l */
        public void i(o writer, T value) {
            writer.W(this.b[value.ordinal()]);
        }

        public String toString() {
            return "JsonAdapter(" + this.a.getName() + ")";
        }
    }

    /* compiled from: StandardJsonAdapters */
    public static final class m extends f<Object> {
        private final r a;
        private final f<List> b;
        private final f<Map> c;
        private final f<String> d;
        private final f<Double> e;
        private final f<Boolean> f;

        m(r moshi) {
            this.a = moshi;
            this.b = moshi.c(List.class);
            this.c = moshi.c(Map.class);
            this.d = moshi.c(String.class);
            this.e = moshi.c(Double.class);
            this.f = moshi.c(Boolean.class);
        }

        public Object b(i reader) {
            switch (b.a[reader.x().ordinal()]) {
                case 1:
                    return this.b.b(reader);
                case 2:
                    return this.c.b(reader);
                case 3:
                    return this.d.b(reader);
                case 4:
                    return this.e.b(reader);
                case 5:
                    return this.f.b(reader);
                case 6:
                    return reader.t();
                default:
                    throw new IllegalStateException("Expected a value but was " + reader.x() + " at path " + reader.getPath());
            }
        }

        public void i(o writer, Object value) {
            Class<?> valueClass = value.getClass();
            if (valueClass == Object.class) {
                writer.g();
                writer.m();
                return;
            }
            this.a.e(k(valueClass), com.squareup.moshi.internal.b.a).i(writer, value);
        }

        private Class<?> k(Class<?> valueClass) {
            if (Map.class.isAssignableFrom(valueClass)) {
                return Map.class;
            }
            if (Collection.class.isAssignableFrom(valueClass)) {
                return Collection.class;
            }
            return valueClass;
        }

        public String toString() {
            return "JsonAdapter(Object)";
        }
    }

    /* compiled from: StandardJsonAdapters */
    public static /* synthetic */ class b {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[i.b.values().length];
            a = iArr;
            try {
                iArr[i.b.BEGIN_ARRAY.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                a[i.b.BEGIN_OBJECT.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                a[i.b.STRING.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                a[i.b.NUMBER.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                a[i.b.BOOLEAN.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                a[i.b.NULL.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
        }
    }
}
