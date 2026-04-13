package androidx.work;

import com.google.common.util.concurrent.ListenableFuture;
import java.util.concurrent.CancellationException;
import kotlin.l;
import kotlin.o;
import kotlin.p;
import kotlinx.coroutines.n;

@l(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0002\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0000H\n¢\u0006\u0004\b\u0002\u0010\u0003"}, d2 = {"R", "Lkotlin/x;", "<anonymous>", "()V"}, k = 3, mv = {1, 5, 1})
/* compiled from: ListenableFuture.kt */
public final class ListenableFutureKt$await$2$1 implements Runnable {
    final /* synthetic */ n<R> $cancellableContinuation;
    final /* synthetic */ ListenableFuture<R> $this_await;

    public ListenableFutureKt$await$2$1(n<? super R> nVar, ListenableFuture<R> listenableFuture) {
        this.$cancellableContinuation = nVar;
        this.$this_await = listenableFuture;
    }

    public final void run() {
        try {
            n<R> nVar = this.$cancellableContinuation;
            R r = this.$this_await.get();
            o.a aVar = o.Companion;
            nVar.resumeWith(o.m17constructorimpl(r));
        } catch (Throwable throwable) {
            Throwable cause = throwable.getCause();
            if (cause == null) {
                cause = throwable;
            }
            if (throwable instanceof CancellationException) {
                this.$cancellableContinuation.b(cause);
                return;
            }
            n<R> nVar2 = this.$cancellableContinuation;
            o.a aVar2 = o.Companion;
            nVar2.resumeWith(o.m17constructorimpl(p.a(cause)));
        }
    }
}
