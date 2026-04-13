package io.ktor.util.pipeline;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Comparator;
import java.util.WeakHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import kotlin.TypeCastException;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.k;
import kotlin.o;
import kotlin.p;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ExceptionConstructor.kt */
public final class a {
    private static final int a = d(Throwable.class, -1);
    private static final ReentrantReadWriteLock b = new ReentrantReadWriteLock();
    private static final WeakHashMap<Class<? extends Throwable>, l<Throwable, Throwable>> c = new WeakHashMap<>();

    /*  JADX ERROR: StackOverflow in pass: MarkFinallyVisitor
        jadx.core.utils.exceptions.JadxOverflowException: 
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:47)
        	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:81)
        */
    @org.jetbrains.annotations.Nullable
    public static final <E extends java.lang.Throwable> E e(@org.jetbrains.annotations.NotNull E r8, @org.jetbrains.annotations.NotNull java.lang.Throwable r9) {
        /*
            java.lang.String r0 = "exception"
            kotlin.jvm.internal.k.f(r8, r0)
            java.lang.String r0 = "cause"
            kotlin.jvm.internal.k.f(r9, r0)
            boolean r0 = r8 instanceof kotlinx.coroutines.g0
            r1 = 0
            if (r0 == 0) goto L_0x0032
            kotlin.o$a r9 = kotlin.o.Companion     // Catch:{ all -> 0x001c }
            kotlinx.coroutines.g0 r8 = (kotlinx.coroutines.g0) r8     // Catch:{ all -> 0x001c }
            java.lang.Throwable r8 = r8.createCopy()     // Catch:{ all -> 0x001c }
            java.lang.Object r8 = kotlin.o.m17constructorimpl(r8)     // Catch:{ all -> 0x001c }
            goto L_0x0027
        L_0x001c:
            r8 = move-exception
            kotlin.o$a r9 = kotlin.o.Companion
            java.lang.Object r8 = kotlin.p.a(r8)
            java.lang.Object r8 = kotlin.o.m17constructorimpl(r8)
        L_0x0027:
            boolean r9 = kotlin.o.m22isFailureimpl(r8)
            if (r9 == 0) goto L_0x002e
            goto L_0x002f
        L_0x002e:
            r1 = r8
        L_0x002f:
            java.lang.Throwable r1 = (java.lang.Throwable) r1
            return r1
        L_0x0032:
            java.util.concurrent.locks.ReentrantReadWriteLock r0 = b
            java.util.concurrent.locks.ReentrantReadWriteLock$ReadLock r2 = r0.readLock()
            r2.lock()
            java.util.WeakHashMap<java.lang.Class<? extends java.lang.Throwable>, kotlin.jvm.functions.l<java.lang.Throwable, java.lang.Throwable>> r3 = c     // Catch:{ all -> 0x0131 }
            java.lang.Class r4 = r8.getClass()     // Catch:{ all -> 0x0131 }
            java.lang.Object r3 = r3.get(r4)     // Catch:{ all -> 0x0131 }
            kotlin.jvm.functions.l r3 = (kotlin.jvm.functions.l) r3     // Catch:{ all -> 0x0131 }
            r2.unlock()
            if (r3 == 0) goto L_0x0053
            java.lang.Object r8 = r3.invoke(r8)
            java.lang.Throwable r8 = (java.lang.Throwable) r8
            return r8
        L_0x0053:
            int r2 = a
            java.lang.Class r3 = r8.getClass()
            r4 = 0
            int r3 = d(r3, r4)
            if (r2 == r3) goto L_0x00a6
            java.util.concurrent.locks.ReentrantReadWriteLock$ReadLock r9 = r0.readLock()
            int r2 = r0.getWriteHoldCount()
            if (r2 != 0) goto L_0x006f
            int r2 = r0.getReadHoldCount()
            goto L_0x0070
        L_0x006f:
            r2 = r4
        L_0x0070:
            r3 = r4
        L_0x0071:
            if (r3 >= r2) goto L_0x0079
            r9.unlock()
            int r3 = r3 + 1
            goto L_0x0071
        L_0x0079:
            java.util.concurrent.locks.ReentrantReadWriteLock$WriteLock r0 = r0.writeLock()
            r0.lock()
            java.util.WeakHashMap<java.lang.Class<? extends java.lang.Throwable>, kotlin.jvm.functions.l<java.lang.Throwable, java.lang.Throwable>> r3 = c     // Catch:{ all -> 0x0099 }
            java.lang.Class r8 = r8.getClass()     // Catch:{ all -> 0x0099 }
            io.ktor.util.pipeline.a$f r5 = io.ktor.util.pipeline.a.f.INSTANCE     // Catch:{ all -> 0x0099 }
            r3.put(r8, r5)     // Catch:{ all -> 0x0099 }
            kotlin.x r8 = kotlin.x.a     // Catch:{ all -> 0x0099 }
        L_0x008d:
            if (r4 >= r2) goto L_0x0095
            r9.lock()
            int r4 = r4 + 1
            goto L_0x008d
        L_0x0095:
            r0.unlock()
            return r1
        L_0x0099:
            r8 = move-exception
        L_0x009a:
            if (r4 >= r2) goto L_0x00a2
            r9.lock()
            int r4 = r4 + 1
            goto L_0x009a
        L_0x00a2:
            r0.unlock()
            throw r8
        L_0x00a6:
            java.lang.Class r0 = r8.getClass()
            java.lang.reflect.Constructor[] r0 = r0.getConstructors()
            java.lang.String r2 = "exception.javaClass.constructors"
            kotlin.jvm.internal.k.b(r0, r2)
            io.ktor.util.pipeline.a$e r2 = new io.ktor.util.pipeline.a$e
            r2.<init>()
            java.util.List r0 = kotlin.collections.l.M(r0, r2)
            java.util.Iterator r0 = r0.iterator()
            r2 = r1
        L_0x00c3:
            boolean r3 = r0.hasNext()
            if (r3 == 0) goto L_0x00dc
            java.lang.Object r2 = r0.next()
            java.lang.reflect.Constructor r2 = (java.lang.reflect.Constructor) r2
            java.lang.String r3 = "constructor"
            kotlin.jvm.internal.k.b(r2, r3)
            kotlin.jvm.functions.l r2 = a(r2)
            if (r2 == 0) goto L_0x00db
            goto L_0x00dc
        L_0x00db:
            goto L_0x00c3
        L_0x00dc:
            java.util.concurrent.locks.ReentrantReadWriteLock r0 = b
            java.util.concurrent.locks.ReentrantReadWriteLock$ReadLock r3 = r0.readLock()
            int r5 = r0.getWriteHoldCount()
            if (r5 != 0) goto L_0x00ed
            int r5 = r0.getReadHoldCount()
            goto L_0x00ee
        L_0x00ed:
            r5 = r4
        L_0x00ee:
            r6 = r4
        L_0x00ef:
            if (r6 >= r5) goto L_0x00f7
            r3.unlock()
            int r6 = r6 + 1
            goto L_0x00ef
        L_0x00f7:
            java.util.concurrent.locks.ReentrantReadWriteLock$WriteLock r0 = r0.writeLock()
            r0.lock()
            java.util.WeakHashMap<java.lang.Class<? extends java.lang.Throwable>, kotlin.jvm.functions.l<java.lang.Throwable, java.lang.Throwable>> r6 = c     // Catch:{ all -> 0x0124 }
            java.lang.Class r8 = r8.getClass()     // Catch:{ all -> 0x0124 }
            if (r2 == 0) goto L_0x0108
            r7 = r2
            goto L_0x010a
        L_0x0108:
            io.ktor.util.pipeline.a$g r7 = io.ktor.util.pipeline.a.g.INSTANCE     // Catch:{ all -> 0x0124 }
        L_0x010a:
            r6.put(r8, r7)     // Catch:{ all -> 0x0124 }
            kotlin.x r8 = kotlin.x.a     // Catch:{ all -> 0x0124 }
        L_0x010f:
            if (r4 >= r5) goto L_0x0117
            r3.lock()
            int r4 = r4 + 1
            goto L_0x010f
        L_0x0117:
            r0.unlock()
            if (r2 == 0) goto L_0x0123
            java.lang.Object r8 = r2.invoke(r9)
            r1 = r8
            java.lang.Throwable r1 = (java.lang.Throwable) r1
        L_0x0123:
            return r1
        L_0x0124:
            r8 = move-exception
        L_0x0125:
            if (r4 >= r5) goto L_0x012d
            r3.lock()
            int r4 = r4 + 1
            goto L_0x0125
        L_0x012d:
            r0.unlock()
            throw r8
        L_0x0131:
            r8 = move-exception
            r2.unlock()
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.util.pipeline.a.e(java.lang.Throwable, java.lang.Throwable):java.lang.Throwable");
    }

    /* compiled from: ExceptionConstructor.kt */
    public static final class f extends kotlin.jvm.internal.l implements l {
        public static final f INSTANCE = new f();

        f() {
            super(1);
        }

        @Nullable
        public final Void invoke(@NotNull Throwable it) {
            k.f(it, "it");
            return null;
        }
    }

    /* compiled from: ExceptionConstructor.kt */
    public static final class g extends kotlin.jvm.internal.l implements l {
        public static final g INSTANCE = new g();

        g() {
            super(1);
        }

        @Nullable
        public final Void invoke(@NotNull Throwable it) {
            k.f(it, "it");
            return null;
        }
    }

    private static final l<Throwable, Throwable> a(Constructor<?> constructor) {
        Class<String> cls = String.class;
        Class[] parameterTypes = constructor.getParameterTypes();
        switch (parameterTypes.length) {
            case 0:
                return new d(constructor);
            case 1:
                Class cls2 = parameterTypes[0];
                if (k.a(cls2, Throwable.class)) {
                    return new b(constructor);
                }
                if (k.a(cls2, cls)) {
                    return new c(constructor);
                }
                break;
            case 2:
                if (k.a(parameterTypes[0], cls) && k.a(parameterTypes[1], Throwable.class)) {
                    return new C0275a(constructor);
                }
        }
        return null;
    }

    /* renamed from: io.ktor.util.pipeline.a$a  reason: collision with other inner class name */
    /* compiled from: ExceptionConstructor.kt */
    public static final class C0275a extends kotlin.jvm.internal.l implements l<Throwable, Throwable> {
        final /* synthetic */ Constructor $constructor$inlined;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public C0275a(Constructor constructor) {
            super(1);
            this.$constructor$inlined = constructor;
        }

        @Nullable
        public final Throwable invoke(@NotNull Throwable e) {
            Object obj;
            k.f(e, "e");
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
                throw new TypeCastException("null cannot be cast to non-null type kotlin.Throwable");
            } catch (Throwable th) {
                o.a aVar2 = o.Companion;
                obj = o.m17constructorimpl(p.a(th));
            }
        }
    }

    /* compiled from: ExceptionConstructor.kt */
    public static final class b extends kotlin.jvm.internal.l implements l<Throwable, Throwable> {
        final /* synthetic */ Constructor $constructor$inlined;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public b(Constructor constructor) {
            super(1);
            this.$constructor$inlined = constructor;
        }

        @Nullable
        public final Throwable invoke(@NotNull Throwable e) {
            Object obj;
            k.f(e, "e");
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
                throw new TypeCastException("null cannot be cast to non-null type kotlin.Throwable");
            } catch (Throwable th) {
                o.a aVar2 = o.Companion;
                obj = o.m17constructorimpl(p.a(th));
            }
        }
    }

    /* compiled from: ExceptionConstructor.kt */
    public static final class c extends kotlin.jvm.internal.l implements l<Throwable, Throwable> {
        final /* synthetic */ Constructor $constructor$inlined;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public c(Constructor constructor) {
            super(1);
            this.$constructor$inlined = constructor;
        }

        @Nullable
        public final Throwable invoke(@NotNull Throwable e) {
            Object obj;
            k.f(e, "e");
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
                throw new TypeCastException("null cannot be cast to non-null type kotlin.Throwable");
            } catch (Throwable th) {
                o.a aVar2 = o.Companion;
                obj = o.m17constructorimpl(p.a(th));
            }
        }
    }

    /* compiled from: ExceptionConstructor.kt */
    public static final class d extends kotlin.jvm.internal.l implements l<Throwable, Throwable> {
        final /* synthetic */ Constructor $constructor$inlined;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public d(Constructor constructor) {
            super(1);
            this.$constructor$inlined = constructor;
        }

        @Nullable
        public final Throwable invoke(@NotNull Throwable e) {
            Object obj;
            k.f(e, "e");
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
                throw new TypeCastException("null cannot be cast to non-null type kotlin.Throwable");
            } catch (Throwable th) {
                o.a aVar2 = o.Companion;
                obj = o.m17constructorimpl(p.a(th));
            }
        }
    }

    private static final int d(@NotNull Class<?> $this$fieldsCountOrDefault, int defaultValue) {
        Integer num;
        kotlin.reflect.c<?> e2 = kotlin.jvm.a.e($this$fieldsCountOrDefault);
        try {
            o.a aVar = o.Companion;
            num = o.m17constructorimpl(Integer.valueOf(c($this$fieldsCountOrDefault, 0, 1, (Object) null)));
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

    static /* synthetic */ int c(Class cls, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = 0;
        }
        return b(cls, i);
    }

    private static final int b(@NotNull Class<?> $this$fieldsCount, int accumulator) {
        while (true) {
            Field[] declaredFields = $this$fieldsCount.getDeclaredFields();
            k.b(declaredFields, "declaredFields");
            int count$iv = 0;
            for (Field it : declaredFields) {
                k.b(it, "it");
                if (!Modifier.isStatic(it.getModifiers())) {
                    count$iv++;
                }
            }
            int totalFields = accumulator + count$iv;
            Class<?> superClass = $this$fieldsCount.getSuperclass();
            if (superClass == null) {
                return totalFields;
            }
            accumulator = totalFields;
            $this$fieldsCount = superClass;
        }
    }

    /* compiled from: Comparisons.kt */
    public static final class e<T> implements Comparator<T> {
        public final int compare(T a, T b) {
            Constructor it = (Constructor) b;
            k.b(it, "it");
            Integer valueOf = Integer.valueOf(it.getParameterTypes().length);
            Constructor it2 = (Constructor) a;
            k.b(it2, "it");
            return kotlin.comparisons.a.c(valueOf, Integer.valueOf(it2.getParameterTypes().length));
        }
    }
}
