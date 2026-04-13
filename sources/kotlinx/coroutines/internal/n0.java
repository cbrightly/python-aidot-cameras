package kotlinx.coroutines.internal;

import java.util.WeakHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import kotlin.l;
import org.jetbrains.annotations.NotNull;

@l(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÂ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J*\u0010\u000b\u001a\u0014\u0012\u0004\u0012\u00020\b\u0012\u0006\u0012\u0004\u0018\u00010\b0\tj\u0002`\n2\u000e\u0010\f\u001a\n\u0012\u0006\b\u0001\u0012\u00020\b0\u0007H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0004¢\u0006\u0002\n\u0000R4\u0010\u0005\u001a(\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\b0\u0007\u0012\u0016\u0012\u0014\u0012\u0004\u0012\u00020\b\u0012\u0006\u0012\u0004\u0018\u00010\b0\tj\u0002`\n0\u0006X\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lkotlinx/coroutines/internal/WeakMapCtorCache;", "Lkotlinx/coroutines/internal/CtorCache;", "()V", "cacheLock", "Ljava/util/concurrent/locks/ReentrantReadWriteLock;", "exceptionCtors", "Ljava/util/WeakHashMap;", "Ljava/lang/Class;", "", "Lkotlin/Function1;", "Lkotlinx/coroutines/internal/Ctor;", "get", "key", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* compiled from: ExceptionsConstructor.kt */
public final class n0 extends h {
    @NotNull
    public static final n0 a = new n0();
    @NotNull
    private static final ReentrantReadWriteLock b = new ReentrantReadWriteLock();
    @NotNull
    private static final WeakHashMap<Class<? extends Throwable>, kotlin.jvm.functions.l<Throwable, Throwable>> c = new WeakHashMap<>();

    private n0() {
    }

    /* JADX INFO: finally extract failed */
    /*  JADX ERROR: StackOverflow in pass: MarkFinallyVisitor
        jadx.core.utils.exceptions.JadxOverflowException: 
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:47)
        	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:81)
        */
    @org.jetbrains.annotations.NotNull
    public kotlin.jvm.functions.l<java.lang.Throwable, java.lang.Throwable> a(@org.jetbrains.annotations.NotNull java.lang.Class<? extends java.lang.Throwable> r10) {
        /*
            r9 = this;
            java.util.concurrent.locks.ReentrantReadWriteLock r0 = b
            java.util.concurrent.locks.ReentrantReadWriteLock$ReadLock r1 = r0.readLock()
            r1.lock()
            r2 = 0
            java.util.WeakHashMap<java.lang.Class<? extends java.lang.Throwable>, kotlin.jvm.functions.l<java.lang.Throwable, java.lang.Throwable>> r3 = c     // Catch:{ all -> 0x007b }
            java.lang.Object r3 = r3.get(r10)     // Catch:{ all -> 0x007b }
            kotlin.jvm.functions.l r3 = (kotlin.jvm.functions.l) r3     // Catch:{ all -> 0x007b }
            if (r3 != 0) goto L_0x0074
            r1.unlock()
            java.util.concurrent.locks.ReentrantReadWriteLock$ReadLock r1 = r0.readLock()
            int r2 = r0.getWriteHoldCount()
            r3 = 0
            if (r2 != 0) goto L_0x0027
            int r2 = r0.getReadHoldCount()
            goto L_0x0028
        L_0x0027:
            r2 = r3
        L_0x0028:
            r4 = r3
        L_0x0029:
            if (r4 >= r2) goto L_0x0031
            int r4 = r4 + 1
            r1.unlock()
            goto L_0x0029
        L_0x0031:
            java.util.concurrent.locks.ReentrantReadWriteLock$WriteLock r0 = r0.writeLock()
            r0.lock()
            r4 = 0
            java.util.WeakHashMap<java.lang.Class<? extends java.lang.Throwable>, kotlin.jvm.functions.l<java.lang.Throwable, java.lang.Throwable>> r5 = c     // Catch:{ all -> 0x0067 }
            java.lang.Object r6 = r5.get(r10)     // Catch:{ all -> 0x0067 }
            kotlin.jvm.functions.l r6 = (kotlin.jvm.functions.l) r6     // Catch:{ all -> 0x0067 }
            if (r6 != 0) goto L_0x0058
            kotlin.jvm.functions.l r6 = kotlinx.coroutines.internal.k.b(r10)     // Catch:{ all -> 0x0067 }
            r7 = r6
            r8 = 0
            r5.put(r10, r7)     // Catch:{ all -> 0x0067 }
        L_0x004c:
            if (r3 >= r2) goto L_0x0054
            int r3 = r3 + 1
            r1.lock()
            goto L_0x004c
        L_0x0054:
            r0.unlock()
            return r6
        L_0x0058:
            r5 = r6
            r6 = 0
        L_0x005b:
            if (r3 >= r2) goto L_0x0063
            int r3 = r3 + 1
            r1.lock()
            goto L_0x005b
        L_0x0063:
            r0.unlock()
            return r5
        L_0x0067:
            r4 = move-exception
        L_0x0068:
            if (r3 >= r2) goto L_0x0070
            int r3 = r3 + 1
            r1.lock()
            goto L_0x0068
        L_0x0070:
            r0.unlock()
            throw r4
        L_0x0074:
            r0 = r3
            r3 = 0
            r1.unlock()
            return r0
        L_0x007b:
            r0 = move-exception
            r1.unlock()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.internal.n0.a(java.lang.Class):kotlin.jvm.functions.l");
    }
}
