package kotlinx.coroutines.internal;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Comparator;
import kotlin.l;
import kotlin.o;
import kotlin.p;
import kotlinx.coroutines.g0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"\u0000.\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\u001a2\u0010\u0004\u001a\u0014\u0012\u0004\u0012\u00020\u0006\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u0005j\u0002`\u0007\"\b\b\u0000\u0010\b*\u00020\u00062\f\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\b0\nH\u0002\u001a*\u0010\u000b\u001a\u0018\u0012\u0004\u0012\u00020\u0006\u0012\u0006\u0012\u0004\u0018\u00010\u0006\u0018\u00010\u0005j\u0004\u0018\u0001`\u00072\n\u0010\f\u001a\u0006\u0012\u0002\b\u00030\rH\u0002\u001a1\u0010\u000e\u001a\u0014\u0012\u0004\u0012\u00020\u0006\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u0005j\u0002`\u00072\u0014\b\u0004\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u0005H\b\u001a!\u0010\u0010\u001a\u0004\u0018\u0001H\b\"\b\b\u0000\u0010\b*\u00020\u00062\u0006\u0010\u0011\u001a\u0002H\bH\u0000¢\u0006\u0002\u0010\u0012\u001a\u001b\u0010\u0013\u001a\u00020\u0003*\u0006\u0012\u0002\b\u00030\n2\b\b\u0002\u0010\u0014\u001a\u00020\u0003H\u0010\u001a\u0018\u0010\u0015\u001a\u00020\u0003*\u0006\u0012\u0002\b\u00030\n2\u0006\u0010\u0016\u001a\u00020\u0003H\u0002\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0003X\u0004¢\u0006\u0002\n\u0000*(\b\u0002\u0010\u0017\"\u0010\u0012\u0004\u0012\u00020\u0006\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u00052\u0010\u0012\u0004\u0012\u00020\u0006\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u0005¨\u0006\u0018"}, d2 = {"ctorCache", "Lkotlinx/coroutines/internal/CtorCache;", "throwableFields", "", "createConstructor", "Lkotlin/Function1;", "", "Lkotlinx/coroutines/internal/Ctor;", "E", "clz", "Ljava/lang/Class;", "createSafeConstructor", "constructor", "Ljava/lang/reflect/Constructor;", "safeCtor", "block", "tryCopyException", "exception", "(Ljava/lang/Throwable;)Ljava/lang/Throwable;", "fieldsCount", "accumulator", "fieldsCountOrDefault", "defaultValue", "Ctor", "kotlinx-coroutines-core"}, k = 2, mv = {1, 6, 0}, xi = 48)
/* compiled from: ExceptionsConstructor.kt */
public final class k {
    private static final int a = f(Throwable.class, -1);
    @NotNull
    private static final h b;

    static {
        h hVar;
        try {
            if (m.a()) {
                hVar = n0.a;
            } else {
                hVar = e.a;
            }
        } catch (Throwable th) {
            hVar = n0.a;
        }
        b = hVar;
    }

    @Nullable
    public static final <E extends Throwable> E g(@NotNull E exception) {
        E e2;
        if (!(exception instanceof g0)) {
            return (Throwable) b.a(exception.getClass()).invoke(exception);
        }
        try {
            o.a aVar = o.Companion;
            e2 = o.m17constructorimpl(((g0) exception).createCopy());
        } catch (Throwable th) {
            o.a aVar2 = o.Companion;
            e2 = o.m17constructorimpl(p.a(th));
        }
        if (o.m22isFailureimpl(e2)) {
            e2 = null;
        }
        return (Throwable) e2;
    }

    @l(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0001\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0002\u0010\u0000\u001a\u0004\u0018\u00010\u0001\"\b\b\u0000\u0010\u0002*\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0003H\n¢\u0006\u0002\b\u0005"}, d2 = {"<anonymous>", "", "E", "", "it", "invoke"}, k = 3, mv = {1, 6, 0}, xi = 48)
    /* compiled from: ExceptionsConstructor.kt */
    public static final class b extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l {
        public static final b INSTANCE = new b();

        b() {
            super(1);
        }

        @Nullable
        public final Void invoke(@NotNull Throwable it) {
            return null;
        }
    }

    /* access modifiers changed from: private */
    public static final <E extends Throwable> kotlin.jvm.functions.l<Throwable, Throwable> b(Class<E> clz) {
        kotlin.jvm.functions.l nullResult = b.INSTANCE;
        if (a != f(clz, 0)) {
            return nullResult;
        }
        for (Constructor constructor : kotlin.collections.l.M(clz.getConstructors(), new a())) {
            kotlin.jvm.functions.l result = c(constructor);
            if (result != null) {
                return result;
            }
        }
        return nullResult;
    }

    private static final kotlin.jvm.functions.l<Throwable, Throwable> c(Constructor<?> constructor) {
        Class<String> cls = String.class;
        Class[] p = constructor.getParameterTypes();
        switch (p.length) {
            case 0:
                return new f(constructor);
            case 1:
                Class cls2 = p[0];
                if (kotlin.jvm.internal.k.a(cls2, Throwable.class)) {
                    return new d(constructor);
                }
                if (kotlin.jvm.internal.k.a(cls2, cls)) {
                    return new e(constructor);
                }
                return null;
            case 2:
                if (!kotlin.jvm.internal.k.a(p[0], cls) || !kotlin.jvm.internal.k.a(p[1], Throwable.class)) {
                    return null;
                }
                return new c(constructor);
            default:
                return null;
        }
    }

    @l(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0003\u0010\u0000\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0003¨\u0006\u0004"}, d2 = {"<anonymous>", "", "e", "invoke", "kotlinx/coroutines/internal/ExceptionsConstructorKt$safeCtor$1"}, k = 3, mv = {1, 6, 0}, xi = 48)
    /* compiled from: ExceptionsConstructor.kt */
    public static final class c extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<Throwable, Throwable> {
        final /* synthetic */ Constructor $constructor$inlined;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public c(Constructor constructor) {
            super(1);
            this.$constructor$inlined = constructor;
        }

        @Nullable
        public final Throwable invoke(@NotNull Throwable e) {
            Object obj;
            try {
                o.a aVar = o.Companion;
                Throwable e2 = e;
                Object newInstance = this.$constructor$inlined.newInstance(new Object[]{e2.getMessage(), e2});
                if (newInstance != null) {
                    obj = o.m17constructorimpl((Throwable) newInstance);
                    if (o.m22isFailureimpl(obj)) {
                        obj = null;
                    }
                    return (Throwable) obj;
                }
                throw new NullPointerException("null cannot be cast to non-null type kotlin.Throwable");
            } catch (Throwable th) {
                o.a aVar2 = o.Companion;
                obj = o.m17constructorimpl(p.a(th));
            }
        }
    }

    @l(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0003\u0010\u0000\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0003¨\u0006\u0004"}, d2 = {"<anonymous>", "", "e", "invoke", "kotlinx/coroutines/internal/ExceptionsConstructorKt$safeCtor$1"}, k = 3, mv = {1, 6, 0}, xi = 48)
    /* compiled from: ExceptionsConstructor.kt */
    public static final class d extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<Throwable, Throwable> {
        final /* synthetic */ Constructor $constructor$inlined;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public d(Constructor constructor) {
            super(1);
            this.$constructor$inlined = constructor;
        }

        @Nullable
        public final Throwable invoke(@NotNull Throwable e) {
            Object obj;
            try {
                o.a aVar = o.Companion;
                Object newInstance = this.$constructor$inlined.newInstance(new Object[]{e});
                if (newInstance != null) {
                    obj = o.m17constructorimpl((Throwable) newInstance);
                    if (o.m22isFailureimpl(obj)) {
                        obj = null;
                    }
                    return (Throwable) obj;
                }
                throw new NullPointerException("null cannot be cast to non-null type kotlin.Throwable");
            } catch (Throwable th) {
                o.a aVar2 = o.Companion;
                obj = o.m17constructorimpl(p.a(th));
            }
        }
    }

    @l(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0003\u0010\u0000\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0003¨\u0006\u0004"}, d2 = {"<anonymous>", "", "e", "invoke", "kotlinx/coroutines/internal/ExceptionsConstructorKt$safeCtor$1"}, k = 3, mv = {1, 6, 0}, xi = 48)
    /* compiled from: ExceptionsConstructor.kt */
    public static final class e extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<Throwable, Throwable> {
        final /* synthetic */ Constructor $constructor$inlined;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public e(Constructor constructor) {
            super(1);
            this.$constructor$inlined = constructor;
        }

        @Nullable
        public final Throwable invoke(@NotNull Throwable e) {
            Object obj;
            try {
                o.a aVar = o.Companion;
                Throwable e2 = e;
                Object newInstance = this.$constructor$inlined.newInstance(new Object[]{e2.getMessage()});
                if (newInstance != null) {
                    Throwable it = (Throwable) newInstance;
                    it.initCause(e2);
                    obj = o.m17constructorimpl(it);
                    if (o.m22isFailureimpl(obj)) {
                        obj = null;
                    }
                    return (Throwable) obj;
                }
                throw new NullPointerException("null cannot be cast to non-null type kotlin.Throwable");
            } catch (Throwable th) {
                o.a aVar2 = o.Companion;
                obj = o.m17constructorimpl(p.a(th));
            }
        }
    }

    @l(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0003\u0010\u0000\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0003¨\u0006\u0004"}, d2 = {"<anonymous>", "", "e", "invoke", "kotlinx/coroutines/internal/ExceptionsConstructorKt$safeCtor$1"}, k = 3, mv = {1, 6, 0}, xi = 48)
    /* compiled from: ExceptionsConstructor.kt */
    public static final class f extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<Throwable, Throwable> {
        final /* synthetic */ Constructor $constructor$inlined;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public f(Constructor constructor) {
            super(1);
            this.$constructor$inlined = constructor;
        }

        @Nullable
        public final Throwable invoke(@NotNull Throwable e) {
            Object obj;
            try {
                o.a aVar = o.Companion;
                Throwable e2 = e;
                Object newInstance = this.$constructor$inlined.newInstance(new Object[0]);
                if (newInstance != null) {
                    Throwable it = (Throwable) newInstance;
                    it.initCause(e2);
                    obj = o.m17constructorimpl(it);
                    if (o.m22isFailureimpl(obj)) {
                        obj = null;
                    }
                    return (Throwable) obj;
                }
                throw new NullPointerException("null cannot be cast to non-null type kotlin.Throwable");
            } catch (Throwable th) {
                o.a aVar2 = o.Companion;
                obj = o.m17constructorimpl(p.a(th));
            }
        }
    }

    private static final int f(Class<?> $this$fieldsCountOrDefault, int defaultValue) {
        Integer num;
        kotlin.reflect.c<?> e2 = kotlin.jvm.a.e($this$fieldsCountOrDefault);
        try {
            o.a aVar = o.Companion;
            num = o.m17constructorimpl(Integer.valueOf(e($this$fieldsCountOrDefault, 0, 1, (Object) null)));
        } catch (Throwable th) {
            o.a aVar2 = o.Companion;
            num = o.m17constructorimpl(p.a(th));
        }
        Integer valueOf = Integer.valueOf(defaultValue);
        if (o.m22isFailureimpl(num)) {
            num = valueOf;
        }
        return ((Number) num).intValue();
    }

    private static final int d(Class<?> $this$fieldsCount, int accumulator) {
        Class<?> cls = $this$fieldsCount;
        int totalFields = accumulator;
        do {
            Field[] declaredFields = cls.getDeclaredFields();
            int count$iv = 0;
            int i = 0;
            int length = declaredFields.length;
            while (i < length) {
                Field it = declaredFields[i];
                i++;
                if (!Modifier.isStatic(it.getModifiers())) {
                    count$iv++;
                }
            }
            totalFields += count$iv;
            cls = cls.getSuperclass();
        } while (cls != null);
        return totalFields;
    }

    static /* synthetic */ int e(Class cls, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = 0;
        }
        return d(cls, i);
    }

    @l(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u000e\u0010\u0003\u001a\n \u0004*\u0004\u0018\u0001H\u0002H\u00022\u000e\u0010\u0005\u001a\n \u0004*\u0004\u0018\u0001H\u0002H\u0002H\n¢\u0006\u0004\b\u0006\u0010\u0007¨\u0006\b"}, d2 = {"<anonymous>", "", "T", "a", "kotlin.jvm.PlatformType", "b", "compare", "(Ljava/lang/Object;Ljava/lang/Object;)I", "kotlin/comparisons/ComparisonsKt__ComparisonsKt$compareByDescending$1"}, k = 3, mv = {1, 6, 0}, xi = 48)
    /* compiled from: Comparisons.kt */
    public static final class a<T> implements Comparator {
        public final int compare(T a, T b) {
            return kotlin.comparisons.a.c(Integer.valueOf(((Constructor) b).getParameterTypes().length), Integer.valueOf(((Constructor) a).getParameterTypes().length));
        }
    }
}
