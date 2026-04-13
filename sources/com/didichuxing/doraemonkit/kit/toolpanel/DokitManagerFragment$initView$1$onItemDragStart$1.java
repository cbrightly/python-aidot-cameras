package com.didichuxing.doraemonkit.kit.toolpanel;

import android.animation.ValueAnimator;
import android.view.View;
import com.didichuxing.doraemonkit.widget.bravh.viewholder.BaseViewHolder;
import kotlin.TypeCastException;
import kotlin.l;

@l(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\u0006\u001a\u00020\u00032\u000e\u0010\u0002\u001a\n \u0001*\u0004\u0018\u00010\u00000\u0000H\n¢\u0006\u0004\b\u0004\u0010\u0005"}, d2 = {"Landroid/animation/ValueAnimator;", "kotlin.jvm.PlatformType", "it", "Lkotlin/x;", "onAnimationUpdate", "(Landroid/animation/ValueAnimator;)V", "<anonymous>"}, k = 3, mv = {1, 4, 0})
/* compiled from: DokitManagerFragment.kt */
public final class DokitManagerFragment$initView$1$onItemDragStart$1 implements ValueAnimator.AnimatorUpdateListener {
    final /* synthetic */ BaseViewHolder $holder;

    DokitManagerFragment$initView$1$onItemDragStart$1(BaseViewHolder baseViewHolder) {
        this.$holder = baseViewHolder;
    }

    public final void onAnimationUpdate(ValueAnimator it) {
        View view = this.$holder.itemView;
        Object animatedValue = it.getAnimatedValue();
        if (animatedValue != null) {
            view.setBackgroundColor(((Integer) animatedValue).intValue());
            return;
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlin.Int");
    }
}
