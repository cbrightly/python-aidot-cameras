package androidx.work;

import androidx.annotation.RestrictTo;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.concurrent.ExecutionException;
import kotlin.coroutines.d;
import kotlin.coroutines.intrinsics.b;
import kotlin.coroutines.intrinsics.c;
import kotlin.coroutines.jvm.internal.h;
import kotlin.jvm.internal.j;
import kotlinx.coroutines.n;
import kotlinx.coroutines.o;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ListenableFuture.kt */
public final class ListenableFutureKt {
    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static final <R> Object await(@NotNull ListenableFuture<R> $this$await, @NotNull d<? super R> $completion) {
        if ($this$await.isDone()) {
            try {
                return $this$await.get();
            } catch (ExecutionException e) {
                Throwable cause = e.getCause();
                if (cause == null) {
                    cause = e;
                }
                throw cause;
            }
        } else {
            o cancellable$iv = new o(b.c($completion), 1);
            cancellable$iv.w();
            n cancellableContinuation = cancellable$iv;
            $this$await.addListener(new ListenableFutureKt$await$2$1(cancellableContinuation, $this$await), DirectExecutor.INSTANCE);
            cancellableContinuation.f(new ListenableFutureKt$await$2$2($this$await));
            Object t = cancellable$iv.t();
            if (t == c.d()) {
                h.c($completion);
            }
            return t;
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    private static final <R> Object await$$forInline(ListenableFuture<R> $this$await, d<? super R> $completion) {
        if ($this$await.isDone()) {
            try {
                return $this$await.get();
            } catch (ExecutionException e) {
                Throwable cause = e.getCause();
                if (cause == null) {
                    cause = e;
                }
                throw cause;
            }
        } else {
            j.c(0);
            o cancellable$iv = new o(b.c($completion), 1);
            cancellable$iv.w();
            n cancellableContinuation = cancellable$iv;
            $this$await.addListener(new ListenableFutureKt$await$2$1(cancellableContinuation, $this$await), DirectExecutor.INSTANCE);
            cancellableContinuation.f(new ListenableFutureKt$await$2$2($this$await));
            Object t = cancellable$iv.t();
            if (t == c.d()) {
                h.c($completion);
            }
            j.c(1);
            return t;
        }
    }
}
