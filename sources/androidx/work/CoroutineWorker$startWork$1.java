package androidx.work;

import kotlin.coroutines.d;
import kotlin.coroutines.jvm.internal.f;
import kotlin.jvm.functions.p;
import kotlin.l;
import kotlin.x;
import kotlinx.coroutines.o0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H@¢\u0006\u0004\b\u0002\u0010\u0003"}, d2 = {"Lkotlinx/coroutines/o0;", "Lkotlin/x;", "<anonymous>", "(Lkotlinx/coroutines/o0;)V"}, k = 3, mv = {1, 5, 1})
@f(c = "androidx.work.CoroutineWorker$startWork$1", f = "CoroutineWorker.kt", l = {68}, m = "invokeSuspend")
/* compiled from: CoroutineWorker.kt */
public final class CoroutineWorker$startWork$1 extends kotlin.coroutines.jvm.internal.l implements p<o0, d<? super x>, Object> {
    int label;
    final /* synthetic */ CoroutineWorker this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    CoroutineWorker$startWork$1(CoroutineWorker coroutineWorker, d<? super CoroutineWorker$startWork$1> dVar) {
        super(2, dVar);
        this.this$0 = coroutineWorker;
    }

    @NotNull
    public final d<x> create(@Nullable Object obj, @NotNull d<?> dVar) {
        return new CoroutineWorker$startWork$1(this.this$0, dVar);
    }

    @Nullable
    public final Object invoke(@NotNull o0 o0Var, @Nullable d<? super x> dVar) {
        return ((CoroutineWorker$startWork$1) create(o0Var, dVar)).invokeSuspend(x.a);
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:16:?, code lost:
        r1.this$0.getFuture$work_runtime_ktx_release().set((androidx.work.ListenableWorker.Result) r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0039, code lost:
        r6 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x003a, code lost:
        r4 = r1;
        r1 = r6;
        r6 = r0;
        r0 = r4;
     */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r6) {
        /*
            r5 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.c.d()
            int r1 = r5.label
            switch(r1) {
                case 0: goto L_0x001a;
                case 1: goto L_0x0011;
                default: goto L_0x0009;
            }
        L_0x0009:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r0)
            throw r6
        L_0x0011:
            r0 = r5
            kotlin.p.b(r6)     // Catch:{ all -> 0x0018 }
            r1 = r0
            r0 = r6
            goto L_0x002d
        L_0x0018:
            r1 = move-exception
            goto L_0x0043
        L_0x001a:
            kotlin.p.b(r6)
            r1 = r5
            androidx.work.CoroutineWorker r2 = r1.this$0     // Catch:{ all -> 0x003f }
            r3 = 1
            r1.label = r3     // Catch:{ all -> 0x003f }
            java.lang.Object r2 = r2.doWork(r1)     // Catch:{ all -> 0x003f }
            if (r2 != r0) goto L_0x002b
            return r0
        L_0x002b:
            r0 = r6
            r6 = r2
        L_0x002d:
            androidx.work.ListenableWorker$Result r6 = (androidx.work.ListenableWorker.Result) r6     // Catch:{ all -> 0x0039 }
            androidx.work.CoroutineWorker r2 = r1.this$0     // Catch:{ all -> 0x0039 }
            androidx.work.impl.utils.futures.SettableFuture r2 = r2.getFuture$work_runtime_ktx_release()     // Catch:{ all -> 0x0039 }
            r2.set(r6)     // Catch:{ all -> 0x0039 }
            goto L_0x004e
        L_0x0039:
            r6 = move-exception
            r4 = r1
            r1 = r6
            r6 = r0
            r0 = r4
            goto L_0x0043
        L_0x003f:
            r0 = move-exception
            r4 = r1
            r1 = r0
            r0 = r4
        L_0x0043:
            androidx.work.CoroutineWorker r2 = r0.this$0
            androidx.work.impl.utils.futures.SettableFuture r2 = r2.getFuture$work_runtime_ktx_release()
            r2.setException(r1)
            r1 = r0
            r0 = r6
        L_0x004e:
            kotlin.x r6 = kotlin.x.a
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.work.CoroutineWorker$startWork$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
