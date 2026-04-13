package com.didichuxing.doraemonkit.widget.bravh.diff;

import androidx.recyclerview.widget.DiffUtil;
import java.util.List;
import kotlin.jvm.internal.k;
import kotlin.l;

@l(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\u0004\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0000H\n¢\u0006\u0004\b\u0002\u0010\u0003"}, d2 = {"T", "Lkotlin/x;", "run", "()V", "<anonymous>"}, k = 3, mv = {1, 4, 0})
/* compiled from: BrvahAsyncDiffer.kt */
public final class BrvahAsyncDiffer$submitList$1 implements Runnable {
    final /* synthetic */ Runnable $commitCallback;
    final /* synthetic */ List $newList;
    final /* synthetic */ List $oldList;
    final /* synthetic */ int $runGeneration;
    final /* synthetic */ BrvahAsyncDiffer this$0;

    BrvahAsyncDiffer$submitList$1(BrvahAsyncDiffer brvahAsyncDiffer, List list, List list2, int i, Runnable runnable) {
        this.this$0 = brvahAsyncDiffer;
        this.$oldList = list;
        this.$newList = list2;
        this.$runGeneration = i;
        this.$commitCallback = runnable;
    }

    public final void run() {
        final DiffUtil.DiffResult result = DiffUtil.calculateDiff(new BrvahAsyncDiffer$submitList$1$result$1(this));
        k.b(result, "DiffUtil.calculateDiff(o…         }\n            })");
        this.this$0.mMainThreadExecutor.execute(new Runnable(this) {
            final /* synthetic */ BrvahAsyncDiffer$submitList$1 this$0;

            {
                this.this$0 = r1;
            }

            public final void run() {
                int access$getMMaxScheduledGeneration$p = this.this$0.this$0.mMaxScheduledGeneration;
                BrvahAsyncDiffer$submitList$1 brvahAsyncDiffer$submitList$1 = this.this$0;
                if (access$getMMaxScheduledGeneration$p == brvahAsyncDiffer$submitList$1.$runGeneration) {
                    brvahAsyncDiffer$submitList$1.this$0.latchList(brvahAsyncDiffer$submitList$1.$newList, result, brvahAsyncDiffer$submitList$1.$commitCallback);
                }
            }
        });
    }
}
