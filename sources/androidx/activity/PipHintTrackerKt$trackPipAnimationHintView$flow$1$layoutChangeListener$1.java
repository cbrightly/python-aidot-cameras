package androidx.activity;

import android.graphics.Rect;
import android.view.View;
import kotlin.jvm.internal.k;
import kotlin.l;
import kotlinx.coroutines.channels.u;

@l(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\r\u001a\u00020\f2\u000e\u0010\u0002\u001a\n \u0001*\u0004\u0018\u00010\u00000\u00002\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00020\u0003H\n¢\u0006\u0004\b\r\u0010\u000e"}, d2 = {"Landroid/view/View;", "kotlin.jvm.PlatformType", "v", "", "l", "t", "r", "b", "oldLeft", "oldTop", "oldRight", "oldBottom", "Lkotlin/x;", "<anonymous>", "(Landroid/view/View;IIIIIIII)V"}, k = 3, mv = {1, 5, 1})
/* compiled from: PipHintTracker.kt */
public final class PipHintTrackerKt$trackPipAnimationHintView$flow$1$layoutChangeListener$1 implements View.OnLayoutChangeListener {
    final /* synthetic */ u<Rect> $$this$callbackFlow;

    PipHintTrackerKt$trackPipAnimationHintView$flow$1$layoutChangeListener$1(u<? super Rect> uVar) {
        this.$$this$callbackFlow = uVar;
    }

    public final void onLayoutChange(View v, int l, int t, int r, int b, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        if (l != oldLeft || r != oldRight || t != oldTop || b != oldBottom) {
            u<Rect> uVar = this.$$this$callbackFlow;
            k.d(v, "v");
            uVar.offer(PipHintTrackerKt.trackPipAnimationHintView$positionInWindow(v));
        }
    }
}
