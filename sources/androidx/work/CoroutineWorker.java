package androidx.work;

import android.content.Context;
import androidx.work.ListenableWorker;
import androidx.work.impl.utils.futures.SettableFuture;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import kotlin.coroutines.d;
import kotlin.coroutines.g;
import kotlin.coroutines.intrinsics.b;
import kotlin.coroutines.intrinsics.c;
import kotlin.coroutines.jvm.internal.h;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.x;
import kotlinx.coroutines.d1;
import kotlinx.coroutines.e2;
import kotlinx.coroutines.i0;
import kotlinx.coroutines.j;
import kotlinx.coroutines.n;
import kotlinx.coroutines.o;
import kotlinx.coroutines.o0;
import kotlinx.coroutines.p0;
import kotlinx.coroutines.q0;
import kotlinx.coroutines.z;
import kotlinx.coroutines.z1;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: CoroutineWorker.kt */
public abstract class CoroutineWorker extends ListenableWorker {
    @NotNull
    private final i0 coroutineContext;
    @NotNull
    private final SettableFuture<ListenableWorker.Result> future;
    @NotNull
    private final z job = e2.b((z1) null, 1, (Object) null);

    public static /* synthetic */ void getCoroutineContext$annotations() {
    }

    @Nullable
    public abstract Object doWork(@NotNull d<? super ListenableWorker.Result> dVar);

    @Nullable
    public Object getForegroundInfo(@NotNull d<? super ForegroundInfo> dVar) {
        return getForegroundInfo$suspendImpl(this, dVar);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public CoroutineWorker(@NotNull Context appContext, @NotNull WorkerParameters params) {
        super(appContext, params);
        k.e(appContext, "appContext");
        k.e(params, "params");
        SettableFuture<ListenableWorker.Result> create = SettableFuture.create();
        k.d(create, "create()");
        this.future = create;
        create.addListener(new Runnable(this) {
            final /* synthetic */ CoroutineWorker this$0;

            {
                this.this$0 = r1;
            }

            public final void run() {
                if (this.this$0.getFuture$work_runtime_ktx_release().isCancelled()) {
                    z1.a.a(this.this$0.getJob$work_runtime_ktx_release(), (CancellationException) null, 1, (Object) null);
                }
            }
        }, getTaskExecutor().getBackgroundExecutor());
        this.coroutineContext = d1.a();
    }

    @NotNull
    public final z getJob$work_runtime_ktx_release() {
        return this.job;
    }

    @NotNull
    public final SettableFuture<ListenableWorker.Result> getFuture$work_runtime_ktx_release() {
        return this.future;
    }

    @NotNull
    public i0 getCoroutineContext() {
        return this.coroutineContext;
    }

    @NotNull
    public final ListenableFuture<ListenableWorker.Result> startWork() {
        z1 unused = j.d(p0.a(getCoroutineContext().plus(this.job)), (g) null, (q0) null, new CoroutineWorker$startWork$1(this, (d<? super CoroutineWorker$startWork$1>) null), 3, (Object) null);
        return this.future;
    }

    static /* synthetic */ Object getForegroundInfo$suspendImpl(CoroutineWorker coroutineWorker, d $completion) {
        throw new IllegalStateException("Not implemented");
    }

    @Nullable
    public final Object setProgress(@NotNull Data data, @NotNull d<? super x> $completion) {
        Void voidR;
        ListenableFuture $this$await$iv = setProgressAsync(data);
        k.d($this$await$iv, "setProgressAsync(data)");
        if ($this$await$iv.isDone()) {
            try {
                voidR = $this$await$iv.get();
            } catch (ExecutionException e$iv) {
                Throwable cause = e$iv.getCause();
                if (cause == null) {
                    cause = e$iv;
                }
                throw cause;
            }
        } else {
            o cancellable$iv$iv = new o(b.c($completion), 1);
            cancellable$iv$iv.w();
            n cancellableContinuation$iv = cancellable$iv$iv;
            $this$await$iv.addListener(new ListenableFutureKt$await$2$1(cancellableContinuation$iv, $this$await$iv), DirectExecutor.INSTANCE);
            cancellableContinuation$iv.f(new ListenableFutureKt$await$2$2($this$await$iv));
            Object t = cancellable$iv$iv.t();
            if (t == c.d()) {
                h.c($completion);
            }
            voidR = t;
        }
        return voidR == c.d() ? voidR : x.a;
    }

    @Nullable
    public final Object setForeground(@NotNull ForegroundInfo foregroundInfo, @NotNull d<? super x> $completion) {
        Void voidR;
        ListenableFuture $this$await$iv = setForegroundAsync(foregroundInfo);
        k.d($this$await$iv, "setForegroundAsync(foregroundInfo)");
        if ($this$await$iv.isDone()) {
            try {
                voidR = $this$await$iv.get();
            } catch (ExecutionException e$iv) {
                Throwable cause = e$iv.getCause();
                if (cause == null) {
                    cause = e$iv;
                }
                throw cause;
            }
        } else {
            o cancellable$iv$iv = new o(b.c($completion), 1);
            cancellable$iv$iv.w();
            n cancellableContinuation$iv = cancellable$iv$iv;
            $this$await$iv.addListener(new ListenableFutureKt$await$2$1(cancellableContinuation$iv, $this$await$iv), DirectExecutor.INSTANCE);
            cancellableContinuation$iv.f(new ListenableFutureKt$await$2$2($this$await$iv));
            Object t = cancellable$iv$iv.t();
            if (t == c.d()) {
                h.c($completion);
            }
            voidR = t;
        }
        return voidR == c.d() ? voidR : x.a;
    }

    @NotNull
    public final ListenableFuture<ForegroundInfo> getForegroundInfoAsync() {
        z job2 = e2.b((z1) null, 1, (Object) null);
        o0 scope = p0.a(getCoroutineContext().plus(job2));
        JobListenableFuture jobFuture = new JobListenableFuture(job2, (SettableFuture) null, 2, (DefaultConstructorMarker) null);
        z1 unused = j.d(scope, (g) null, (q0) null, new CoroutineWorker$getForegroundInfoAsync$1(jobFuture, this, (d<? super CoroutineWorker$getForegroundInfoAsync$1>) null), 3, (Object) null);
        return jobFuture;
    }

    public final void onStopped() {
        super.onStopped();
        this.future.cancel(false);
    }
}
