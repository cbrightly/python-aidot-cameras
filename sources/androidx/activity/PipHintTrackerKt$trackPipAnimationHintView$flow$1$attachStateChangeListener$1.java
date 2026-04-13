package androidx.activity;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;
import kotlin.jvm.internal.k;
import kotlinx.coroutines.channels.u;
import org.jetbrains.annotations.NotNull;

/* compiled from: PipHintTracker.kt */
public final class PipHintTrackerKt$trackPipAnimationHintView$flow$1$attachStateChangeListener$1 implements View.OnAttachStateChangeListener {
    final /* synthetic */ u<Rect> $$this$callbackFlow;
    final /* synthetic */ View.OnLayoutChangeListener $layoutChangeListener;
    final /* synthetic */ ViewTreeObserver.OnScrollChangedListener $scrollChangeListener;
    final /* synthetic */ View $view;

    PipHintTrackerKt$trackPipAnimationHintView$flow$1$attachStateChangeListener$1(u<? super Rect> $$this$callbackFlow2, View $view2, ViewTreeObserver.OnScrollChangedListener $scrollChangeListener2, View.OnLayoutChangeListener $layoutChangeListener2) {
        this.$$this$callbackFlow = $$this$callbackFlow2;
        this.$view = $view2;
        this.$scrollChangeListener = $scrollChangeListener2;
        this.$layoutChangeListener = $layoutChangeListener2;
    }

    public void onViewAttachedToWindow(@NotNull View v) {
        k.e(v, "v");
        this.$$this$callbackFlow.offer(PipHintTrackerKt.trackPipAnimationHintView$positionInWindow(this.$view));
        this.$view.getViewTreeObserver().addOnScrollChangedListener(this.$scrollChangeListener);
        this.$view.addOnLayoutChangeListener(this.$layoutChangeListener);
    }

    public void onViewDetachedFromWindow(@NotNull View v) {
        k.e(v, "v");
        v.getViewTreeObserver().removeOnScrollChangedListener(this.$scrollChangeListener);
        v.removeOnLayoutChangeListener(this.$layoutChangeListener);
    }
}
