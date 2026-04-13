package com.didichuxing.doraemonkit.kit.toolpanel;

import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Build;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.e0;
import com.blankj.utilcode.util.i0;
import com.didichuxing.doraemonkit.constant.DokitConstant;
import com.didichuxing.doraemonkit.widget.bravh.listener.OnItemDragListener;
import com.didichuxing.doraemonkit.widget.bravh.viewholder.BaseViewHolder;
import java.util.List;
import kotlin.TypeCastException;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: DokitManagerFragment.kt */
public final class DokitManagerFragment$initView$1 implements OnItemDragListener {
    final /* synthetic */ DokitManagerFragment this$0;

    DokitManagerFragment$initView$1(DokitManagerFragment $outer) {
        this.this$0 = $outer;
    }

    public void onItemDragStart(@Nullable RecyclerView.ViewHolder viewHolder, int pos) {
        if (viewHolder != null) {
            BaseViewHolder holder = (BaseViewHolder) viewHolder;
            int endColor = Color.rgb(245, 245, 245);
            if (Build.VERSION.SDK_INT >= 21) {
                ValueAnimator v = ValueAnimator.ofArgb(new int[]{-1, endColor});
                v.addUpdateListener(new DokitManagerFragment$initView$1$onItemDragStart$1(holder));
                k.b(v, "v");
                v.setDuration(300);
                v.start();
            }
            i0.b(50);
            this.this$0.mDragStartPos = pos;
            this.this$0.mBakKits.clear();
            this.this$0.mBakKits.addAll(this.this$0.mKits);
            return;
        }
        throw new TypeCastException("null cannot be cast to non-null type com.didichuxing.doraemonkit.widget.bravh.viewholder.BaseViewHolder");
    }

    public boolean canDropOver(@NotNull RecyclerView recyclerView, @NotNull RecyclerView.ViewHolder current, @NotNull RecyclerView.ViewHolder target) {
        k.f(recyclerView, "recyclerView");
        k.f(current, "current");
        k.f(target, TypedValues.AttributesType.S_TARGET);
        List list = DokitConstant.GLOBAL_KITS.get(((KitWrapItem) this.this$0.mKits.get(current.getAdapterPosition())).getGroupName());
        if (list == null || list.size() != 1) {
            return true;
        }
        e0.o("分组中必须存在一个元素", new Object[0]);
        return false;
    }

    public void onItemDragMoving(@Nullable RecyclerView.ViewHolder source, int from, @Nullable RecyclerView.ViewHolder target, int to) {
    }

    public void onItemDragEnd(@Nullable RecyclerView.ViewHolder viewHolder, int pos) {
        if (viewHolder != null) {
            BaseViewHolder holder = (BaseViewHolder) viewHolder;
            int startColor = Color.rgb(245, 245, 245);
            if (Build.VERSION.SDK_INT >= 21) {
                ValueAnimator v = ValueAnimator.ofArgb(new int[]{startColor, -1});
                v.addUpdateListener(new DokitManagerFragment$initView$1$onItemDragEnd$1(holder));
                k.b(v, "v");
                v.setDuration(300);
                v.start();
            }
            if (this.this$0.mDragStartPos != pos) {
                KitWrapItem originItem = (KitWrapItem) this.this$0.mBakKits.get(pos);
                KitWrapItem currentItem = (KitWrapItem) this.this$0.mKits.get(pos);
                if (originItem.getItemType() == currentItem.getItemType()) {
                    currentItem.setGroupName(originItem.getGroupName());
                } else {
                    currentItem.setGroupName(((KitWrapItem) this.this$0.mBakKits.get(pos - 1)).getGroupName());
                }
                this.this$0.reGroupForKit();
                return;
            }
            return;
        }
        throw new TypeCastException("null cannot be cast to non-null type com.didichuxing.doraemonkit.widget.bravh.viewholder.BaseViewHolder");
    }
}
