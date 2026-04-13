package androidx.work;

import androidx.work.Operation;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.concurrent.ExecutionException;
import kotlin.coroutines.d;
import kotlin.coroutines.intrinsics.b;
import kotlin.coroutines.intrinsics.c;
import kotlin.coroutines.jvm.internal.h;
import kotlin.jvm.internal.j;
import kotlin.jvm.internal.k;
import kotlinx.coroutines.n;
import kotlinx.coroutines.o;

/* compiled from: Operation.kt */
public final class OperationKt {
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v4, resolved type: com.google.common.util.concurrent.ListenableFuture<androidx.work.Operation$State$SUCCESS>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v7, resolved type: androidx.work.Operation$State$SUCCESS} */
    /* JADX WARNING: type inference failed for: r4v2, types: [java.lang.Throwable] */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x003a  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    /* JADX WARNING: Unknown variable types count: 1 */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final java.lang.Object await(@org.jetbrains.annotations.NotNull androidx.work.Operation r12, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super androidx.work.Operation.State.SUCCESS> r13) {
        /*
            boolean r0 = r13 instanceof androidx.work.OperationKt$await$1
            if (r0 == 0) goto L_0x0013
            r0 = r13
            androidx.work.OperationKt$await$1 r0 = (androidx.work.OperationKt$await$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            androidx.work.OperationKt$await$1 r0 = new androidx.work.OperationKt$await$1
            r0.<init>(r13)
        L_0x0018:
            r13 = r0
            java.lang.Object r0 = r13.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.c.d()
            int r2 = r13.label
            switch(r2) {
                case 0: goto L_0x003a;
                case 1: goto L_0x002c;
                default: goto L_0x0024;
            }
        L_0x0024:
            java.lang.IllegalStateException r12 = new java.lang.IllegalStateException
            java.lang.String r13 = "call to 'resume' before 'invoke' with coroutine"
            r12.<init>(r13)
            throw r12
        L_0x002c:
            r12 = 0
            r1 = 0
            r2 = 0
            java.lang.Object r3 = r13.L$0
            com.google.common.util.concurrent.ListenableFuture r3 = (com.google.common.util.concurrent.ListenableFuture) r3
            kotlin.p.b(r0)
            r4 = r2
            r2 = r0
            goto L_0x009e
        L_0x003a:
            kotlin.p.b(r0)
            r2 = 0
            com.google.common.util.concurrent.ListenableFuture r3 = r12.getResult()
            java.lang.String r12 = "result"
            kotlin.jvm.internal.k.d(r3, r12)
            r12 = r3
            r3 = 0
            boolean r4 = r12.isDone()
            if (r4 == 0) goto L_0x0060
            java.lang.Object r1 = r12.get()     // Catch:{ ExecutionException -> 0x0056 }
            goto L_0x00a1
        L_0x0056:
            r1 = move-exception
            java.lang.Throwable r4 = r1.getCause()
            if (r4 != 0) goto L_0x005e
            goto L_0x005f
        L_0x005e:
            r1 = r4
        L_0x005f:
            throw r1
        L_0x0060:
            r4 = 0
            r13.L$0 = r12
            r5 = 1
            r13.label = r5
            r6 = r13
            r7 = 0
            kotlinx.coroutines.o r8 = new kotlinx.coroutines.o
            kotlin.coroutines.d r9 = kotlin.coroutines.intrinsics.b.c(r6)
            r8.<init>(r9, r5)
            r5 = r8
            r5.w()
            r6 = r5
            r8 = 0
            androidx.work.ListenableFutureKt$await$2$1 r9 = new androidx.work.ListenableFutureKt$await$2$1
            r9.<init>(r6, r12)
            androidx.work.DirectExecutor r10 = androidx.work.DirectExecutor.INSTANCE
            r12.addListener(r9, r10)
            androidx.work.ListenableFutureKt$await$2$2 r9 = new androidx.work.ListenableFutureKt$await$2$2
            r9.<init>(r12)
            r6.f(r9)
            java.lang.Object r12 = r5.t()
            java.lang.Object r5 = kotlin.coroutines.intrinsics.c.d()
            if (r12 != r5) goto L_0x0097
            kotlin.coroutines.jvm.internal.h.c(r13)
        L_0x0097:
            if (r12 != r1) goto L_0x009a
            return r1
        L_0x009a:
            r1 = r3
            r11 = r2
            r2 = r12
            r12 = r11
        L_0x009e:
            r1 = r2
            r2 = r12
        L_0x00a1:
            java.lang.String r12 = "result.await()"
            kotlin.jvm.internal.k.d(r1, r12)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.work.OperationKt.await(androidx.work.Operation, kotlin.coroutines.d):java.lang.Object");
    }

    private static final Object await$$forInline(Operation $this$await, d<? super Operation.State.SUCCESS> $completion) {
        Operation.State.SUCCESS success;
        ListenableFuture $this$await$iv = $this$await.getResult();
        k.d($this$await$iv, "result");
        if ($this$await$iv.isDone()) {
            try {
                success = $this$await$iv.get();
            } catch (ExecutionException e$iv) {
                Throwable cause = e$iv.getCause();
                if (cause == null) {
                    cause = e$iv;
                }
                throw cause;
            }
        } else {
            j.c(0);
            o cancellable$iv$iv = new o(b.c($completion), 1);
            cancellable$iv$iv.w();
            n cancellableContinuation$iv = cancellable$iv$iv;
            $this$await$iv.addListener(new ListenableFutureKt$await$2$1(cancellableContinuation$iv, $this$await$iv), DirectExecutor.INSTANCE);
            cancellableContinuation$iv.f(new ListenableFutureKt$await$2$2($this$await$iv));
            Object t = cancellable$iv$iv.t();
            if (t == c.d()) {
                h.c($completion);
            }
            j.c(1);
            success = t;
        }
        k.d(success, "result.await()");
        return success;
    }
}
