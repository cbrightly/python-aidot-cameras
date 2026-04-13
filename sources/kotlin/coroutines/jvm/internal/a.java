package kotlin.coroutines.jvm.internal;

import java.io.Serializable;
import kotlin.coroutines.d;
import kotlin.coroutines.g;
import kotlin.jvm.internal.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ContinuationImpl.kt */
public abstract class a implements d<Object>, e, Serializable {
    @Nullable
    private final d<Object> completion;

    @NotNull
    public abstract /* synthetic */ g getContext();

    /* access modifiers changed from: protected */
    @Nullable
    public abstract Object invokeSuspend(@NotNull Object obj);

    public a(@Nullable d<Object> completion2) {
        this.completion = completion2;
    }

    @Nullable
    public final d<Object> getCompletion() {
        return this.completion;
    }

    /* JADX WARNING: type inference failed for: r4v0, types: [kotlin.coroutines.d<java.lang.Object>, java.lang.Object, kotlin.coroutines.d] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void resumeWith(@org.jetbrains.annotations.NotNull java.lang.Object r8) {
        /*
            r7 = this;
            r0 = r7
            r1 = r8
        L_0x0002:
            kotlin.coroutines.jvm.internal.h.b(r0)
            r2 = r0
            r3 = 0
            kotlin.coroutines.d<java.lang.Object> r4 = r2.completion
            kotlin.jvm.internal.k.c(r4)
            java.lang.Object r5 = r2.invokeSuspend(r1)     // Catch:{ all -> 0x0021 }
            java.lang.Object r6 = kotlin.coroutines.intrinsics.c.d()     // Catch:{ all -> 0x0021 }
            if (r5 != r6) goto L_0x001a
            return
        L_0x001a:
            kotlin.o$a r6 = kotlin.o.Companion     // Catch:{ all -> 0x0021 }
            java.lang.Object r6 = kotlin.o.m17constructorimpl(r5)     // Catch:{ all -> 0x0021 }
            goto L_0x002c
        L_0x0021:
            r5 = move-exception
            kotlin.o$a r6 = kotlin.o.Companion
            java.lang.Object r6 = kotlin.p.a(r5)
            java.lang.Object r6 = kotlin.o.m17constructorimpl(r6)
        L_0x002c:
            r5 = r6
            r2.releaseIntercepted()
            boolean r6 = r4 instanceof kotlin.coroutines.jvm.internal.a
            if (r6 == 0) goto L_0x003c
            r0 = r4
            kotlin.coroutines.jvm.internal.a r0 = (kotlin.coroutines.jvm.internal.a) r0
            r1 = r5
            goto L_0x0002
        L_0x003c:
            r4.resumeWith(r5)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.coroutines.jvm.internal.a.resumeWith(java.lang.Object):void");
    }

    /* access modifiers changed from: protected */
    public void releaseIntercepted() {
    }

    @NotNull
    public d<x> create(@NotNull d<?> completion2) {
        k.e(completion2, "completion");
        throw new UnsupportedOperationException("create(Continuation) has not been overridden");
    }

    @NotNull
    public d<x> create(@Nullable Object value, @NotNull d<?> completion2) {
        k.e(completion2, "completion");
        throw new UnsupportedOperationException("create(Any?;Continuation) has not been overridden");
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Continuation at ");
        Object stackTraceElement = getStackTraceElement();
        if (stackTraceElement == null) {
            stackTraceElement = getClass().getName();
        }
        sb.append(stackTraceElement);
        return sb.toString();
    }

    @Nullable
    public e getCallerFrame() {
        d<Object> dVar = this.completion;
        if (!(dVar instanceof e)) {
            dVar = null;
        }
        return (e) dVar;
    }

    @Nullable
    public StackTraceElement getStackTraceElement() {
        return g.d(this);
    }
}
