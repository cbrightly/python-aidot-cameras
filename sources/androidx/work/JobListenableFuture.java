package androidx.work;

import androidx.work.impl.utils.futures.SettableFuture;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.concurrent.CancellationException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.k;
import kotlin.x;
import kotlinx.coroutines.z1;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ListenableFuture.kt */
public final class JobListenableFuture<R> implements ListenableFuture<R> {
    @NotNull
    private final z1 job;
    /* access modifiers changed from: private */
    @NotNull
    public final SettableFuture<R> underlying;

    public void addListener(Runnable runnable, Executor executor) {
        this.underlying.addListener(runnable, executor);
    }

    public boolean cancel(boolean z) {
        return this.underlying.cancel(z);
    }

    public R get() {
        return this.underlying.get();
    }

    public R get(long j, TimeUnit timeUnit) {
        return this.underlying.get(j, timeUnit);
    }

    public boolean isCancelled() {
        return this.underlying.isCancelled();
    }

    public boolean isDone() {
        return this.underlying.isDone();
    }

    public JobListenableFuture(@NotNull z1 job2, @NotNull SettableFuture<R> underlying2) {
        k.e(job2, "job");
        k.e(underlying2, "underlying");
        this.job = job2;
        this.underlying = underlying2;
        job2.t(new l<Throwable, x>(this) {
            final /* synthetic */ JobListenableFuture<R> this$0;

            {
                this.this$0 = r2;
            }

            public /* bridge */ /* synthetic */ Object invoke(Object p1) {
                invoke((Throwable) p1);
                return x.a;
            }

            public final void invoke(@Nullable Throwable throwable) {
                if (throwable == null) {
                    if (!this.this$0.underlying.isDone()) {
                        throw new IllegalArgumentException("Failed requirement.".toString());
                    }
                } else if (throwable instanceof CancellationException) {
                    this.this$0.underlying.cancel(true);
                } else {
                    SettableFuture access$getUnderlying$p = this.this$0.underlying;
                    Throwable cause = throwable.getCause();
                    if (cause == null) {
                        cause = throwable;
                    }
                    access$getUnderlying$p.setException(cause);
                }
            }
        });
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public /* synthetic */ JobListenableFuture(kotlinx.coroutines.z1 r1, androidx.work.impl.utils.futures.SettableFuture r2, int r3, kotlin.jvm.internal.DefaultConstructorMarker r4) {
        /*
            r0 = this;
            r3 = r3 & 2
            if (r3 == 0) goto L_0x000d
            androidx.work.impl.utils.futures.SettableFuture r2 = androidx.work.impl.utils.futures.SettableFuture.create()
            java.lang.String r3 = "create()"
            kotlin.jvm.internal.k.d(r2, r3)
        L_0x000d:
            r0.<init>(r1, r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.work.JobListenableFuture.<init>(kotlinx.coroutines.z1, androidx.work.impl.utils.futures.SettableFuture, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    public final void complete(R result) {
        this.underlying.set(result);
    }
}
