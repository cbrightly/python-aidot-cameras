package com.didichuxing.doraemonkit.widget.bravh;

import android.view.View;
import com.didichuxing.doraemonkit.widget.bravh.viewholder.BaseViewHolder;
import kotlin.jvm.internal.k;
import kotlin.l;

@l(d1 = {"\u0000\u0018\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\u0010\n\u001a\u00020\u0006\"\u0004\b\u0000\u0010\u0000\"\b\b\u0001\u0010\u0002*\u00020\u00012\u000e\u0010\u0005\u001a\n \u0004*\u0004\u0018\u00010\u00030\u0003H\n¢\u0006\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"T", "LLcom/didichuxing/doraemonkit/widget/bravh/viewholder/BaseViewHolder;;", "VH", "Landroid/view/View;", "kotlin.jvm.PlatformType", "v", "", "nLongClic", "(Landroid/view/View;)Z", "com/didichuxing/doraemonkit/widget/bravh/BaseQuickAdapter$bindViewClickListener$2$1", "<anonymous>"}, k = 3, mv = {1, 4, 0})
/* compiled from: BaseQuickAdapter.kt */
public final class BaseQuickAdapter$bindViewClickListener$$inlined$let$lambda$2 implements View.OnLongClickListener {
    final /* synthetic */ BaseViewHolder $viewHolder$inlined;
    final /* synthetic */ BaseQuickAdapter this$0;

    BaseQuickAdapter$bindViewClickListener$$inlined$let$lambda$2(BaseQuickAdapter baseQuickAdapter, BaseViewHolder baseViewHolder) {
        this.this$0 = baseQuickAdapter;
        this.$viewHolder$inlined = baseViewHolder;
    }

    public final boolean onLongClick(View v) {
        int position = this.$viewHolder$inlined.getAdapterPosition();
        if (position == -1) {
            return false;
        }
        int position2 = position - this.this$0.getHeaderLayoutCount();
        BaseQuickAdapter baseQuickAdapter = this.this$0;
        k.b(v, "v");
        return baseQuickAdapter.setOnItemLongClick(v, position2);
    }
}
