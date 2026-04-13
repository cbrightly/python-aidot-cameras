package kotlin.reflect.jvm.internal.impl.storage;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import kotlin.reflect.jvm.internal.impl.storage.LockBasedStorageManager;
import kotlin.reflect.jvm.internal.impl.utils.WrappedValues;
import kotlin.text.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: LockBasedStorageManager */
public class b implements j {
    private static final String a = x.b1(b.class.getCanonicalName(), ".", "");
    public static final j b = new a("NO_LOCKS", f.a, e.c);
    protected final Lock c;
    /* access modifiers changed from: private */
    public final f d;
    private final String e;

    /* compiled from: LockBasedStorageManager */
    public enum n {
        NOT_COMPUTED,
        COMPUTING,
        RECURSION_WAS_DETECTED
    }

    private static /* synthetic */ void i(int i2) {
        String str;
        int i3;
        Throwable th;
        switch (i2) {
            case 8:
            case 12:
            case 28:
                str = "@NotNull method %s.%s must not return null";
                break;
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i2) {
            case 8:
            case 12:
            case 28:
                i3 = 2;
                break;
            default:
                i3 = 3;
                break;
        }
        Object[] objArr = new Object[i3];
        switch (i2) {
            case 1:
            case 3:
            case 6:
                objArr[0] = "exceptionHandlingStrategy";
                break;
            case 4:
                objArr[0] = "lock";
                break;
            case 7:
            case 9:
            case 11:
            case 13:
                objArr[0] = "compute";
                break;
            case 8:
            case 12:
            case 28:
                objArr[0] = "kotlin/reflect/jvm/internal/impl/storage/LockBasedStorageManager";
                break;
            case 10:
            case 14:
                objArr[0] = "map";
                break;
            case 15:
            case 16:
            case 18:
            case 20:
            case 22:
            case 23:
            case 24:
            case 26:
                objArr[0] = "computable";
                break;
            case 17:
            case 19:
                objArr[0] = "onRecursiveCall";
                break;
            case 21:
            case 25:
                objArr[0] = "postCompute";
                break;
            case 27:
                objArr[0] = "throwable";
                break;
            default:
                objArr[0] = "debugText";
                break;
        }
        switch (i2) {
            case 8:
                objArr[1] = "createMemoizedFunction";
                break;
            case 12:
                objArr[1] = "createMemoizedFunctionWithNullableValues";
                break;
            case 28:
                objArr[1] = "sanitizeStackTrace";
                break;
            default:
                objArr[1] = "kotlin/reflect/jvm/internal/impl/storage/LockBasedStorageManager";
                break;
        }
        switch (i2) {
            case 2:
            case 3:
            case 4:
                objArr[2] = "<init>";
                break;
            case 5:
            case 6:
                objArr[2] = "replaceExceptionHandling";
                break;
            case 7:
            case 9:
            case 10:
                objArr[2] = "createMemoizedFunction";
                break;
            case 8:
            case 12:
            case 28:
                break;
            case 11:
            case 13:
            case 14:
                objArr[2] = "createMemoizedFunctionWithNullableValues";
                break;
            case 15:
            case 16:
            case 17:
                objArr[2] = "createLazyValue";
                break;
            case 18:
            case 19:
                objArr[2] = "createRecursionTolerantLazyValue";
                break;
            case 20:
            case 21:
                objArr[2] = "createLazyValueWithPostCompute";
                break;
            case 22:
                objArr[2] = "createNullableLazyValue";
                break;
            case 23:
                objArr[2] = "createRecursionTolerantNullableLazyValue";
                break;
            case 24:
            case 25:
                objArr[2] = "createNullableLazyValueWithPostCompute";
                break;
            case 26:
                objArr[2] = "compute";
                break;
            case 27:
                objArr[2] = "sanitizeStackTrace";
                break;
            default:
                objArr[2] = "createWithExceptionHandling";
                break;
        }
        String format = String.format(str, objArr);
        switch (i2) {
            case 8:
            case 12:
            case 28:
                th = new IllegalStateException(format);
                break;
            default:
                th = new IllegalArgumentException(format);
                break;
        }
        throw th;
    }

    /* synthetic */ b(String x0, f x1, Lock x2, a x3) {
        this(x0, x1, x2);
    }

    /* compiled from: LockBasedStorageManager */
    public interface f {
        public static final f a = new a();

        @NotNull
        RuntimeException handleException(@NotNull Throwable th);

        /* compiled from: LockBasedStorageManager */
        public static final class a implements f {
            private static /* synthetic */ void a(int i) {
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", new Object[]{"throwable", "kotlin/reflect/jvm/internal/impl/storage/LockBasedStorageManager$ExceptionHandlingStrategy$1", "handleException"}));
            }

            a() {
            }

            @NotNull
            public RuntimeException handleException(@NotNull Throwable throwable) {
                if (throwable == null) {
                    a(0);
                }
                throw kotlin.reflect.jvm.internal.impl.utils.c.b(throwable);
            }
        }
    }

    /* compiled from: LockBasedStorageManager */
    public static final class a extends b {
        private static /* synthetic */ void i(int i) {
            throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", new Object[]{"kotlin/reflect/jvm/internal/impl/storage/LockBasedStorageManager$1", "recursionDetectedDefault"}));
        }

        a(String x0, f x1, Lock x2) {
            super(x0, x1, x2, (a) null);
        }

        /* access modifiers changed from: protected */
        @NotNull
        public <T> o<T> o() {
            o<T> a = o.a();
            if (a == null) {
                i(0);
            }
            return a;
        }
    }

    private b(@NotNull String debugText, @NotNull f exceptionHandlingStrategy, @NotNull Lock lock) {
        if (debugText == null) {
            i(2);
        }
        if (exceptionHandlingStrategy == null) {
            i(3);
        }
        if (lock == null) {
            i(4);
        }
        this.c = lock;
        this.d = exceptionHandlingStrategy;
        this.e = debugText;
    }

    public b(String debugText) {
        this(debugText, f.a, new ReentrantLock());
    }

    public String toString() {
        return getClass().getSimpleName() + "@" + Integer.toHexString(hashCode()) + " (" + this.e + ")";
    }

    @NotNull
    public <K, V> c<K, V> h(@NotNull kotlin.jvm.functions.l<? super K, ? extends V> compute) {
        if (compute == null) {
            i(7);
        }
        c<K, V> m2 = m(compute, l());
        if (m2 == null) {
            i(8);
        }
        return m2;
    }

    @NotNull
    public <K, V> c<K, V> m(@NotNull kotlin.jvm.functions.l<? super K, ? extends V> compute, @NotNull ConcurrentMap<K, Object> map) {
        if (compute == null) {
            i(9);
        }
        if (map == null) {
            i(10);
        }
        return new m(this, map, compute);
    }

    @NotNull
    public <K, V> d<K, V> g(@NotNull kotlin.jvm.functions.l<? super K, ? extends V> compute) {
        if (compute == null) {
            i(11);
        }
        d<K, V> n2 = n(compute, l());
        if (n2 == null) {
            i(12);
        }
        return n2;
    }

    @NotNull
    public <K, V> d<K, V> n(@NotNull kotlin.jvm.functions.l<? super K, ? extends V> compute, @NotNull ConcurrentMap<K, Object> map) {
        if (compute == null) {
            i(13);
        }
        if (map == null) {
            i(14);
        }
        return new l(this, map, compute);
    }

    @NotNull
    public <T> f<T> c(@NotNull kotlin.jvm.functions.a<? extends T> computable) {
        if (computable == null) {
            i(15);
        }
        return new j(this, computable);
    }

    /* renamed from: kotlin.reflect.jvm.internal.impl.storage.b$b  reason: collision with other inner class name */
    /* compiled from: LockBasedStorageManager */
    public class C0421b extends j<T> {
        final /* synthetic */ Object q;

        private static /* synthetic */ void a(int i) {
            throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", new Object[]{"kotlin/reflect/jvm/internal/impl/storage/LockBasedStorageManager$3", "recursionDetected"}));
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        C0421b(b x0, kotlin.jvm.functions.a x1, Object obj) {
            super(x0, x1);
            this.q = obj;
        }

        /* access modifiers changed from: protected */
        @NotNull
        public o<T> c(boolean firstTime) {
            o<T> d = o.d(this.q);
            if (d == null) {
                a(0);
            }
            return d;
        }
    }

    @NotNull
    public <T> f<T> b(@NotNull kotlin.jvm.functions.a<? extends T> computable, @NotNull T onRecursiveCall) {
        if (computable == null) {
            i(18);
        }
        if (onRecursiveCall == null) {
            i(19);
        }
        return new C0421b(this, computable, onRecursiveCall);
    }

    /* compiled from: LockBasedStorageManager */
    public class c extends k<T> {
        final /* synthetic */ kotlin.jvm.functions.l x;
        final /* synthetic */ kotlin.jvm.functions.l y;

        private static /* synthetic */ void a(int i) {
            String str;
            int i2;
            Throwable th;
            switch (i) {
                case 2:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
                default:
                    str = "@NotNull method %s.%s must not return null";
                    break;
            }
            switch (i) {
                case 2:
                    i2 = 3;
                    break;
                default:
                    i2 = 2;
                    break;
            }
            Object[] objArr = new Object[i2];
            switch (i) {
                case 2:
                    objArr[0] = "value";
                    break;
                default:
                    objArr[0] = "kotlin/reflect/jvm/internal/impl/storage/LockBasedStorageManager$4";
                    break;
            }
            switch (i) {
                case 2:
                    objArr[1] = "kotlin/reflect/jvm/internal/impl/storage/LockBasedStorageManager$4";
                    break;
                default:
                    objArr[1] = "recursionDetected";
                    break;
            }
            switch (i) {
                case 2:
                    objArr[2] = "doPostCompute";
                    break;
            }
            String format = String.format(str, objArr);
            switch (i) {
                case 2:
                    th = new IllegalArgumentException(format);
                    break;
                default:
                    th = new IllegalStateException(format);
                    break;
            }
            throw th;
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        c(b x0, kotlin.jvm.functions.a x1, kotlin.jvm.functions.l lVar, kotlin.jvm.functions.l lVar2) {
            super(x0, x1);
            this.x = lVar;
            this.y = lVar2;
        }

        /* access modifiers changed from: protected */
        @NotNull
        public o<T> c(boolean firstTime) {
            kotlin.jvm.functions.l lVar = this.x;
            if (lVar == null) {
                o<T> c = super.c(firstTime);
                if (c == null) {
                    a(0);
                }
                return c;
            }
            o<T> d = o.d(lVar.invoke(Boolean.valueOf(firstTime)));
            if (d == null) {
                a(1);
            }
            return d;
        }

        /* access modifiers changed from: protected */
        public void d(@NotNull T value) {
            if (value == null) {
                a(2);
            }
            this.y.invoke(value);
        }
    }

    @NotNull
    public <T> f<T> f(@NotNull kotlin.jvm.functions.a<? extends T> computable, kotlin.jvm.functions.l<? super Boolean, ? extends T> onRecursiveCall, @NotNull kotlin.jvm.functions.l<? super T, kotlin.x> postCompute) {
        if (computable == null) {
            i(20);
        }
        if (postCompute == null) {
            i(21);
        }
        return new c(this, computable, onRecursiveCall, postCompute);
    }

    @NotNull
    public <T> g<T> e(@NotNull kotlin.jvm.functions.a<? extends T> computable) {
        if (computable == null) {
            i(22);
        }
        return new h(this, computable);
    }

    public <T> T d(@NotNull kotlin.jvm.functions.a<? extends T> computable) {
        if (computable == null) {
            i(26);
        }
        this.c.lock();
        try {
            T invoke = computable.invoke();
            this.c.unlock();
            return invoke;
        } catch (Throwable throwable) {
            this.c.unlock();
            throw throwable;
        }
    }

    @NotNull
    private static <K> ConcurrentMap<K, Object> l() {
        return new ConcurrentHashMap(3, 1.0f, 2);
    }

    /* access modifiers changed from: protected */
    @NotNull
    public <T> o<T> o() {
        throw ((IllegalStateException) p(new IllegalStateException("Recursive call in a lazy value under " + this)));
    }

    /* compiled from: LockBasedStorageManager */
    public static class o<T> {
        private final T a;
        private final boolean b;

        @NotNull
        public static <T> o<T> d(T value) {
            return new o<>(value, false);
        }

        @NotNull
        public static <T> o<T> a() {
            return new o<>((Object) null, true);
        }

        private o(T value, boolean fallThrough) {
            this.a = value;
            this.b = fallThrough;
        }

        public T b() {
            if (!this.b) {
                return this.a;
            }
            throw new AssertionError("A value requested from FALL_THROUGH in " + this);
        }

        public boolean c() {
            return this.b;
        }

        public String toString() {
            return c() ? "FALL_THROUGH" : String.valueOf(this.a);
        }
    }

    /* compiled from: LockBasedStorageManager */
    public static class h<T> implements g<T> {
        private final b c;
        private final kotlin.jvm.functions.a<? extends T> d;
        @Nullable
        private volatile Object f;

        private static /* synthetic */ void a(int i) {
            String str;
            int i2;
            Throwable th;
            switch (i) {
                case 2:
                case 3:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
            switch (i) {
                case 2:
                case 3:
                    i2 = 2;
                    break;
                default:
                    i2 = 3;
                    break;
            }
            Object[] objArr = new Object[i2];
            switch (i) {
                case 1:
                    objArr[0] = "computable";
                    break;
                case 2:
                case 3:
                    objArr[0] = "kotlin/reflect/jvm/internal/impl/storage/LockBasedStorageManager$LockBasedLazyValue";
                    break;
                default:
                    objArr[0] = "storageManager";
                    break;
            }
            switch (i) {
                case 2:
                    objArr[1] = "recursionDetected";
                    break;
                case 3:
                    objArr[1] = "renderDebugInformation";
                    break;
                default:
                    objArr[1] = "kotlin/reflect/jvm/internal/impl/storage/LockBasedStorageManager$LockBasedLazyValue";
                    break;
            }
            switch (i) {
                case 2:
                case 3:
                    break;
                default:
                    objArr[2] = "<init>";
                    break;
            }
            String format = String.format(str, objArr);
            switch (i) {
                case 2:
                case 3:
                    th = new IllegalStateException(format);
                    break;
                default:
                    th = new IllegalArgumentException(format);
                    break;
            }
            throw th;
        }

        public h(@NotNull b storageManager, @NotNull kotlin.jvm.functions.a<? extends T> computable) {
            if (storageManager == null) {
                a(0);
            }
            if (computable == null) {
                a(1);
            }
            this.f = n.NOT_COMPUTED;
            this.c = storageManager;
            this.d = computable;
        }

        public boolean o() {
            return (this.f == n.NOT_COMPUTED || this.f == n.COMPUTING) ? false : true;
        }

        public T invoke() {
            Object _value = this.f;
            if (!(_value instanceof n)) {
                return WrappedValues.f(_value);
            }
            this.c.c.lock();
            Object _value2 = this.f;
            if (!(_value2 instanceof n)) {
                T f2 = WrappedValues.f(_value2);
                this.c.c.unlock();
                return f2;
            }
            n nVar = n.COMPUTING;
            if (_value2 == nVar) {
                this.f = n.RECURSION_WAS_DETECTED;
                LockBasedStorageManager.RecursionDetectedResult<T> result = c(true);
                if (!result.c()) {
                    T b = result.b();
                    this.c.c.unlock();
                    return b;
                }
            }
            try {
                if (_value2 == n.RECURSION_WAS_DETECTED) {
                    LockBasedStorageManager.RecursionDetectedResult<T> result2 = c(false);
                    if (!result2.c()) {
                        T b2 = result2.b();
                        this.c.c.unlock();
                        return b2;
                    }
                }
                this.f = nVar;
                T typedValue = this.d.invoke();
                b(typedValue);
                this.f = typedValue;
                this.c.c.unlock();
                return typedValue;
            } catch (Throwable throwable) {
                this.c.c.unlock();
                throw throwable;
            }
        }

        /* access modifiers changed from: protected */
        @NotNull
        public o<T> c(boolean firstTime) {
            o<T> o = this.c.o();
            if (o == null) {
                a(2);
            }
            return o;
        }

        /* access modifiers changed from: protected */
        public void b(T t) {
        }
    }

    /* compiled from: LockBasedStorageManager */
    public static abstract class i<T> extends h<T> {
        @Nullable
        private volatile h<T> q;

        private static /* synthetic */ void a(int i) {
            Object[] objArr = new Object[3];
            switch (i) {
                case 1:
                    objArr[0] = "computable";
                    break;
                default:
                    objArr[0] = "storageManager";
                    break;
            }
            objArr[1] = "kotlin/reflect/jvm/internal/impl/storage/LockBasedStorageManager$LockBasedLazyValueWithPostCompute";
            objArr[2] = "<init>";
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        /* access modifiers changed from: protected */
        public abstract void d(T t);

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public i(@NotNull b storageManager, @NotNull kotlin.jvm.functions.a<? extends T> computable) {
            super(storageManager, computable);
            if (storageManager == null) {
                a(0);
            }
            if (computable == null) {
                a(1);
            }
            this.q = null;
        }

        public T invoke() {
            SingleThreadValue<T> postComputeCache = this.q;
            if (postComputeCache == null || !postComputeCache.b()) {
                return super.invoke();
            }
            return postComputeCache.a();
        }

        /* access modifiers changed from: protected */
        public final void b(T value) {
            this.q = new h<>(value);
            try {
                d(value);
            } finally {
                this.q = null;
            }
        }
    }

    /* compiled from: LockBasedStorageManager */
    public static abstract class k<T> extends i<T> implements f<T> {
        private static /* synthetic */ void a(int i) {
            String str;
            int i2;
            Throwable th;
            switch (i) {
                case 2:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
            switch (i) {
                case 2:
                    i2 = 2;
                    break;
                default:
                    i2 = 3;
                    break;
            }
            Object[] objArr = new Object[i2];
            switch (i) {
                case 1:
                    objArr[0] = "computable";
                    break;
                case 2:
                    objArr[0] = "kotlin/reflect/jvm/internal/impl/storage/LockBasedStorageManager$LockBasedNotNullLazyValueWithPostCompute";
                    break;
                default:
                    objArr[0] = "storageManager";
                    break;
            }
            switch (i) {
                case 2:
                    objArr[1] = "invoke";
                    break;
                default:
                    objArr[1] = "kotlin/reflect/jvm/internal/impl/storage/LockBasedStorageManager$LockBasedNotNullLazyValueWithPostCompute";
                    break;
            }
            switch (i) {
                case 2:
                    break;
                default:
                    objArr[2] = "<init>";
                    break;
            }
            String format = String.format(str, objArr);
            switch (i) {
                case 2:
                    th = new IllegalStateException(format);
                    break;
                default:
                    th = new IllegalArgumentException(format);
                    break;
            }
            throw th;
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public k(@NotNull b storageManager, @NotNull kotlin.jvm.functions.a<? extends T> computable) {
            super(storageManager, computable);
            if (storageManager == null) {
                a(0);
            }
            if (computable == null) {
                a(1);
            }
        }

        @NotNull
        public T invoke() {
            T result = super.invoke();
            if (result != null) {
                if (result == null) {
                    a(2);
                }
                return result;
            }
            throw new AssertionError("compute() returned null");
        }
    }

    /* compiled from: LockBasedStorageManager */
    public static class j<T> extends h<T> implements f<T> {
        private static /* synthetic */ void a(int i) {
            String str;
            int i2;
            Throwable th;
            switch (i) {
                case 2:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
            switch (i) {
                case 2:
                    i2 = 2;
                    break;
                default:
                    i2 = 3;
                    break;
            }
            Object[] objArr = new Object[i2];
            switch (i) {
                case 1:
                    objArr[0] = "computable";
                    break;
                case 2:
                    objArr[0] = "kotlin/reflect/jvm/internal/impl/storage/LockBasedStorageManager$LockBasedNotNullLazyValue";
                    break;
                default:
                    objArr[0] = "storageManager";
                    break;
            }
            switch (i) {
                case 2:
                    objArr[1] = "invoke";
                    break;
                default:
                    objArr[1] = "kotlin/reflect/jvm/internal/impl/storage/LockBasedStorageManager$LockBasedNotNullLazyValue";
                    break;
            }
            switch (i) {
                case 2:
                    break;
                default:
                    objArr[2] = "<init>";
                    break;
            }
            String format = String.format(str, objArr);
            switch (i) {
                case 2:
                    th = new IllegalStateException(format);
                    break;
                default:
                    th = new IllegalArgumentException(format);
                    break;
            }
            throw th;
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public j(@NotNull b storageManager, @NotNull kotlin.jvm.functions.a<? extends T> computable) {
            super(storageManager, computable);
            if (storageManager == null) {
                a(0);
            }
            if (computable == null) {
                a(1);
            }
        }

        @NotNull
        public T invoke() {
            T result = super.invoke();
            if (result != null) {
                if (result == null) {
                    a(2);
                }
                return result;
            }
            throw new AssertionError("compute() returned null");
        }
    }

    /* compiled from: LockBasedStorageManager */
    public static class l<K, V> implements d<K, V> {
        private final b c;
        private final ConcurrentMap<K, Object> d;
        private final kotlin.jvm.functions.l<? super K, ? extends V> f;

        private static /* synthetic */ void b(int i) {
            String str;
            int i2;
            Throwable th;
            switch (i) {
                case 3:
                case 4:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
            switch (i) {
                case 3:
                case 4:
                    i2 = 2;
                    break;
                default:
                    i2 = 3;
                    break;
            }
            Object[] objArr = new Object[i2];
            switch (i) {
                case 1:
                    objArr[0] = "map";
                    break;
                case 2:
                    objArr[0] = "compute";
                    break;
                case 3:
                case 4:
                    objArr[0] = "kotlin/reflect/jvm/internal/impl/storage/LockBasedStorageManager$MapBasedMemoizedFunction";
                    break;
                default:
                    objArr[0] = "storageManager";
                    break;
            }
            switch (i) {
                case 3:
                    objArr[1] = "recursionDetected";
                    break;
                case 4:
                    objArr[1] = "raceCondition";
                    break;
                default:
                    objArr[1] = "kotlin/reflect/jvm/internal/impl/storage/LockBasedStorageManager$MapBasedMemoizedFunction";
                    break;
            }
            switch (i) {
                case 3:
                case 4:
                    break;
                default:
                    objArr[2] = "<init>";
                    break;
            }
            String format = String.format(str, objArr);
            switch (i) {
                case 3:
                case 4:
                    th = new IllegalStateException(format);
                    break;
                default:
                    th = new IllegalArgumentException(format);
                    break;
            }
            throw th;
        }

        public l(@NotNull b storageManager, @NotNull ConcurrentMap<K, Object> map, @NotNull kotlin.jvm.functions.l<? super K, ? extends V> compute) {
            if (storageManager == null) {
                b(0);
            }
            if (map == null) {
                b(1);
            }
            if (compute == null) {
                b(2);
            }
            this.c = storageManager;
            this.d = map;
            this.f = compute;
        }

        @Nullable
        public V invoke(K input) {
            Object value = this.d.get(input);
            if (value != null && value != n.COMPUTING) {
                return WrappedValues.d(value);
            }
            this.c.c.lock();
            try {
                Object value2 = this.d.get(input);
                n nVar = n.COMPUTING;
                if (value2 == nVar) {
                    throw e(input);
                } else if (value2 != null) {
                    V d2 = WrappedValues.d(value2);
                    this.c.c.unlock();
                    return d2;
                } else {
                    this.d.put(input, nVar);
                    V typedValue = this.f.invoke(input);
                    Object oldValue = this.d.put(input, WrappedValues.b(typedValue));
                    if (oldValue == nVar) {
                        this.c.c.unlock();
                        return typedValue;
                    }
                    throw d(input, oldValue);
                }
            } catch (Throwable th) {
                this.c.c.unlock();
                throw th;
            }
        }

        @NotNull
        private AssertionError e(K input) {
            AssertionError assertionError = (AssertionError) b.p(new AssertionError("Recursion detected on input: " + input + " under " + this.c));
            if (assertionError == null) {
                b(3);
            }
            return assertionError;
        }

        @NotNull
        private AssertionError d(K input, Object oldValue) {
            AssertionError assertionError = (AssertionError) b.p(new AssertionError("Race condition detected on input " + input + ". Old value is " + oldValue + " under " + this.c));
            if (assertionError == null) {
                b(4);
            }
            return assertionError;
        }

        /* access modifiers changed from: protected */
        public b c() {
            return this.c;
        }
    }

    /* compiled from: LockBasedStorageManager */
    public static class m<K, V> extends l<K, V> implements c<K, V> {
        private static /* synthetic */ void b(int i) {
            String str;
            int i2;
            Throwable th;
            switch (i) {
                case 3:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
            switch (i) {
                case 3:
                    i2 = 2;
                    break;
                default:
                    i2 = 3;
                    break;
            }
            Object[] objArr = new Object[i2];
            switch (i) {
                case 1:
                    objArr[0] = "map";
                    break;
                case 2:
                    objArr[0] = "compute";
                    break;
                case 3:
                    objArr[0] = "kotlin/reflect/jvm/internal/impl/storage/LockBasedStorageManager$MapBasedMemoizedFunctionToNotNull";
                    break;
                default:
                    objArr[0] = "storageManager";
                    break;
            }
            switch (i) {
                case 3:
                    objArr[1] = "invoke";
                    break;
                default:
                    objArr[1] = "kotlin/reflect/jvm/internal/impl/storage/LockBasedStorageManager$MapBasedMemoizedFunctionToNotNull";
                    break;
            }
            switch (i) {
                case 3:
                    break;
                default:
                    objArr[2] = "<init>";
                    break;
            }
            String format = String.format(str, objArr);
            switch (i) {
                case 3:
                    th = new IllegalStateException(format);
                    break;
                default:
                    th = new IllegalArgumentException(format);
                    break;
            }
            throw th;
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public m(@NotNull b storageManager, @NotNull ConcurrentMap<K, Object> map, @NotNull kotlin.jvm.functions.l<? super K, ? extends V> compute) {
            super(storageManager, map, compute);
            if (storageManager == null) {
                b(0);
            }
            if (map == null) {
                b(1);
            }
            if (compute == null) {
                b(2);
            }
        }

        @NotNull
        public V invoke(K input) {
            V result = super.invoke(input);
            if (result != null) {
                if (result == null) {
                    b(3);
                }
                return result;
            }
            throw new AssertionError("compute() returned null under " + c());
        }
    }

    /* access modifiers changed from: private */
    @NotNull
    public static <T extends Throwable> T p(@NotNull T throwable) {
        if (throwable == null) {
            i(27);
        }
        StackTraceElement[] stackTrace = throwable.getStackTrace();
        int size = stackTrace.length;
        int firstNonStorage = -1;
        int i2 = 0;
        while (true) {
            if (i2 >= size) {
                break;
            } else if (!stackTrace[i2].getClassName().startsWith(a)) {
                firstNonStorage = i2;
                break;
            } else {
                i2++;
            }
        }
        if (firstNonStorage >= 0) {
            List<StackTraceElement> list = Arrays.asList(stackTrace).subList(firstNonStorage, size);
            throwable.setStackTrace((StackTraceElement[]) list.toArray(new StackTraceElement[list.size()]));
            return throwable;
        }
        throw new AssertionError("This method should only be called on exceptions created in LockBasedStorageManager");
    }

    /* compiled from: LockBasedStorageManager */
    public static class e<K, V> extends l<g<K, V>, V> {
        private static /* synthetic */ void b(int i) {
            Object[] objArr = new Object[3];
            switch (i) {
                case 1:
                    objArr[0] = "map";
                    break;
                case 2:
                    objArr[0] = "computation";
                    break;
                default:
                    objArr[0] = "storageManager";
                    break;
            }
            objArr[1] = "kotlin/reflect/jvm/internal/impl/storage/LockBasedStorageManager$CacheWithNullableValuesBasedOnMemoizedFunction";
            switch (i) {
                case 2:
                    objArr[2] = "computeIfAbsent";
                    break;
                default:
                    objArr[2] = "<init>";
                    break;
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        /* synthetic */ e(b x0, ConcurrentMap x1, a x2) {
            this(x0, x1);
        }

        /* compiled from: LockBasedStorageManager */
        public class a implements kotlin.jvm.functions.l<g<K, V>, V> {
            a() {
            }

            /* renamed from: a */
            public V invoke(g<K, V> computation) {
                return computation.b.invoke();
            }
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        private e(@NotNull b storageManager, @NotNull ConcurrentMap<g<K, V>, Object> map) {
            super(storageManager, map, new a());
            if (storageManager == null) {
                b(0);
            }
            if (map == null) {
                b(1);
            }
        }

        @Nullable
        public V a(K key, @NotNull kotlin.jvm.functions.a<? extends V> computation) {
            if (computation == null) {
                b(2);
            }
            return invoke(new g(key, computation));
        }
    }

    @NotNull
    public <K, V> a<K, V> a() {
        return new d(this, l(), (a) null);
    }

    /* compiled from: LockBasedStorageManager */
    public static class d<K, V> extends e<K, V> implements a<K, V> {
        private static /* synthetic */ void b(int i) {
            String str;
            int i2;
            Throwable th;
            switch (i) {
                case 3:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
            switch (i) {
                case 3:
                    i2 = 2;
                    break;
                default:
                    i2 = 3;
                    break;
            }
            Object[] objArr = new Object[i2];
            switch (i) {
                case 1:
                    objArr[0] = "map";
                    break;
                case 2:
                    objArr[0] = "computation";
                    break;
                case 3:
                    objArr[0] = "kotlin/reflect/jvm/internal/impl/storage/LockBasedStorageManager$CacheWithNotNullValuesBasedOnMemoizedFunction";
                    break;
                default:
                    objArr[0] = "storageManager";
                    break;
            }
            switch (i) {
                case 3:
                    objArr[1] = "computeIfAbsent";
                    break;
                default:
                    objArr[1] = "kotlin/reflect/jvm/internal/impl/storage/LockBasedStorageManager$CacheWithNotNullValuesBasedOnMemoizedFunction";
                    break;
            }
            switch (i) {
                case 2:
                    objArr[2] = "computeIfAbsent";
                    break;
                case 3:
                    break;
                default:
                    objArr[2] = "<init>";
                    break;
            }
            String format = String.format(str, objArr);
            switch (i) {
                case 3:
                    th = new IllegalStateException(format);
                    break;
                default:
                    th = new IllegalArgumentException(format);
                    break;
            }
            throw th;
        }

        /* synthetic */ d(b x0, ConcurrentMap x1, a x2) {
            this(x0, x1);
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        private d(@NotNull b storageManager, @NotNull ConcurrentMap<g<K, V>, Object> map) {
            super(storageManager, map, (a) null);
            if (storageManager == null) {
                b(0);
            }
            if (map == null) {
                b(1);
            }
        }

        @NotNull
        public V a(K key, @NotNull kotlin.jvm.functions.a<? extends V> computation) {
            if (computation == null) {
                b(2);
            }
            V result = super.a(key, computation);
            if (result != null) {
                if (result == null) {
                    b(3);
                }
                return result;
            }
            throw new AssertionError("computeIfAbsent() returned null under " + c());
        }
    }

    /* compiled from: LockBasedStorageManager */
    public static class g<K, V> {
        private final K a;
        /* access modifiers changed from: private */
        public final kotlin.jvm.functions.a<? extends V> b;

        public g(K key, kotlin.jvm.functions.a<? extends V> computation) {
            this.a = key;
            this.b = computation;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass() || !this.a.equals(((g) o).a)) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            return this.a.hashCode();
        }
    }
}
