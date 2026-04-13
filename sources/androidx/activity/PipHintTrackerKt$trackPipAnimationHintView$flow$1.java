package androidx.activity;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;
import kotlin.coroutines.d;
import kotlin.coroutines.intrinsics.c;
import kotlin.coroutines.jvm.internal.f;
import kotlin.jvm.functions.a;
import kotlin.jvm.functions.p;
import kotlin.l;
import kotlin.x;
import kotlinx.coroutines.channels.s;
import kotlinx.coroutines.channels.u;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0003\u001a\u00020\u0002*\b\u0012\u0004\u0012\u00020\u00010\u0000H@¢\u0006\u0004\b\u0003\u0010\u0004"}, d2 = {"Lkotlinx/coroutines/channels/u;", "Landroid/graphics/Rect;", "Lkotlin/x;", "<anonymous>", "(Lkotlinx/coroutines/channels/u;)V"}, k = 3, mv = {1, 5, 1})
@f(c = "androidx.activity.PipHintTrackerKt$trackPipAnimationHintView$flow$1", f = "PipHintTracker.kt", l = {88}, m = "invokeSuspend")
/* compiled from: PipHintTracker.kt */
public final class PipHintTrackerKt$trackPipAnimationHintView$flow$1 extends kotlin.coroutines.jvm.internal.l implements p<u<? super Rect>, d<? super x>, Object> {
    final /* synthetic */ View $view;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    PipHintTrackerKt$trackPipAnimationHintView$flow$1(View view, d<? super PipHintTrackerKt$trackPipAnimationHintView$flow$1> dVar) {
        super(2, dVar);
        this.$view = view;
    }

    @NotNull
    public final d<x> create(@Nullable Object obj, @NotNull d<?> dVar) {
        PipHintTrackerKt$trackPipAnimationHintView$flow$1 pipHintTrackerKt$trackPipAnimationHintView$flow$1 = new PipHintTrackerKt$trackPipAnimationHintView$flow$1(this.$view, dVar);
        pipHintTrackerKt$trackPipAnimationHintView$flow$1.L$0 = obj;
        return pipHintTrackerKt$trackPipAnimationHintView$flow$1;
    }

    @Nullable
    public final Object invoke(@NotNull u<? super Rect> uVar, @Nullable d<? super x> dVar) {
        return ((PipHintTrackerKt$trackPipAnimationHintView$flow$1) create(uVar, dVar)).invokeSuspend(x.a);
    }

    @Nullable
    public final Object invokeSuspend(@NotNull Object $result) {
        Object d = c.d();
        switch (this.label) {
            case 0:
                kotlin.p.b($result);
                u $this$callbackFlow = (u) this.L$0;
                final View.OnLayoutChangeListener layoutChangeListener = new PipHintTrackerKt$trackPipAnimationHintView$flow$1$layoutChangeListener$1($this$callbackFlow);
                final ViewTreeObserver.OnScrollChangedListener scrollChangeListener = new PipHintTrackerKt$trackPipAnimationHintView$flow$1$scrollChangeListener$1($this$callbackFlow, this.$view);
                final PipHintTrackerKt$trackPipAnimationHintView$flow$1$attachStateChangeListener$1 attachStateChangeListener = new PipHintTrackerKt$trackPipAnimationHintView$flow$1$attachStateChangeListener$1($this$callbackFlow, this.$view, scrollChangeListener, layoutChangeListener);
                if (Api19Impl.INSTANCE.isAttachedToWindow(this.$view)) {
                    $this$callbackFlow.offer(PipHintTrackerKt.trackPipAnimationHintView$positionInWindow(this.$view));
                    this.$view.getViewTreeObserver().addOnScrollChangedListener(scrollChangeListener);
                    this.$view.addOnLayoutChangeListener(layoutChangeListener);
                }
                this.$view.addOnAttachStateChangeListener(attachStateChangeListener);
                final View view = this.$view;
                AnonymousClass1 r6 = new a<x>() {
                    public final void invoke() {
                        view.getViewTreeObserver().removeOnScrollChangedListener(scrollChangeListener);
                        view.removeOnLayoutChangeListener(layoutChangeListener);
                        view.removeOnAttachStateChangeListener(attachStateChangeListener);
                    }
                };
                this.label = 1;
                if (s.a($this$callbackFlow, r6, this) != d) {
                    break;
                } else {
                    return d;
                }
            case 1:
                kotlin.p.b($result);
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        return x.a;
    }
}
