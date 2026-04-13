package androidx.activity;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;
import kotlin.l;
import kotlinx.coroutines.channels.u;

@l(d1 = {"\u0000\b\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0001\u001a\u00020\u0000H\n¢\u0006\u0004\b\u0001\u0010\u0002"}, d2 = {"Lkotlin/x;", "<anonymous>", "()V"}, k = 3, mv = {1, 5, 1})
/* compiled from: PipHintTracker.kt */
public final class PipHintTrackerKt$trackPipAnimationHintView$flow$1$scrollChangeListener$1 implements ViewTreeObserver.OnScrollChangedListener {
    final /* synthetic */ u<Rect> $$this$callbackFlow;
    final /* synthetic */ View $view;

    PipHintTrackerKt$trackPipAnimationHintView$flow$1$scrollChangeListener$1(u<? super Rect> uVar, View view) {
        this.$$this$callbackFlow = uVar;
        this.$view = view;
    }

    public final void onScrollChanged() {
        this.$$this$callbackFlow.offer(PipHintTrackerKt.trackPipAnimationHintView$positionInWindow(this.$view));
    }
}
