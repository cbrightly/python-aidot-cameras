package androidx.work;

import kotlin.coroutines.d;
import kotlin.coroutines.intrinsics.c;
import kotlin.coroutines.jvm.internal.f;
import kotlin.jvm.functions.p;
import kotlin.l;
import kotlin.x;
import kotlinx.coroutines.o0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H@¢\u0006\u0004\b\u0002\u0010\u0003"}, d2 = {"Lkotlinx/coroutines/o0;", "Lkotlin/x;", "<anonymous>", "(Lkotlinx/coroutines/o0;)V"}, k = 3, mv = {1, 5, 1})
@f(c = "androidx.work.CoroutineWorker$getForegroundInfoAsync$1", f = "CoroutineWorker.kt", l = {134}, m = "invokeSuspend")
/* compiled from: CoroutineWorker.kt */
public final class CoroutineWorker$getForegroundInfoAsync$1 extends kotlin.coroutines.jvm.internal.l implements p<o0, d<? super x>, Object> {
    final /* synthetic */ JobListenableFuture<ForegroundInfo> $jobFuture;
    Object L$0;
    int label;
    final /* synthetic */ CoroutineWorker this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    CoroutineWorker$getForegroundInfoAsync$1(JobListenableFuture<ForegroundInfo> jobListenableFuture, CoroutineWorker coroutineWorker, d<? super CoroutineWorker$getForegroundInfoAsync$1> dVar) {
        super(2, dVar);
        this.$jobFuture = jobListenableFuture;
        this.this$0 = coroutineWorker;
    }

    @NotNull
    public final d<x> create(@Nullable Object obj, @NotNull d<?> dVar) {
        return new CoroutineWorker$getForegroundInfoAsync$1(this.$jobFuture, this.this$0, dVar);
    }

    @Nullable
    public final Object invoke(@NotNull o0 o0Var, @Nullable d<? super x> dVar) {
        return ((CoroutineWorker$getForegroundInfoAsync$1) create(o0Var, dVar)).invokeSuspend(x.a);
    }

    @Nullable
    public final Object invokeSuspend(@NotNull Object $result) {
        JobListenableFuture<ForegroundInfo> jobListenableFuture;
        Object d = c.d();
        switch (this.label) {
            case 0:
                kotlin.p.b($result);
                jobListenableFuture = this.$jobFuture;
                CoroutineWorker coroutineWorker = this.this$0;
                this.L$0 = jobListenableFuture;
                this.label = 1;
                Object foregroundInfo = coroutineWorker.getForegroundInfo(this);
                if (foregroundInfo != d) {
                    Object obj = $result;
                    $result = foregroundInfo;
                    break;
                } else {
                    return d;
                }
            case 1:
                kotlin.p.b($result);
                jobListenableFuture = (JobListenableFuture) this.L$0;
                Object obj2 = $result;
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        jobListenableFuture.complete($result);
        return x.a;
    }
}
