package com.didichuxing.doraemonkit.widget.bravh;

import android.view.View;
import com.didichuxing.doraemonkit.widget.bravh.binder.BaseItemBinder;
import com.didichuxing.doraemonkit.widget.bravh.viewholder.BaseViewHolder;
import kotlin.jvm.internal.k;
import kotlin.l;

@l(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\u0010\u0007\u001a\u00020\u00032\u000e\u0010\u0002\u001a\n \u0001*\u0004\u0018\u00010\u00000\u0000H\n¢\u0006\u0004\b\u0004\u0010\u0005¨\u0006\u0006"}, d2 = {"Landroid/view/View;", "kotlin.jvm.PlatformType", "v", "", "onLongClick", "(Landroid/view/View;)Z", "com/didichuxing/doraemonkit/widget/bravh/BaseBinderAdapter$$special$$inlined$let$lambda$2", "<anonymous>"}, k = 3, mv = {1, 4, 0})
/* compiled from: BaseBinderAdapter.kt */
public final class BaseBinderAdapter$bindChildClick$$inlined$forEach$lambda$2 implements View.OnLongClickListener {
    final /* synthetic */ BaseItemBinder $provider$inlined;
    final /* synthetic */ BaseViewHolder $viewHolder$inlined;
    final /* synthetic */ BaseBinderAdapter this$0;

    BaseBinderAdapter$bindChildClick$$inlined$forEach$lambda$2(BaseBinderAdapter baseBinderAdapter, BaseViewHolder baseViewHolder, BaseItemBinder baseItemBinder) {
        this.this$0 = baseBinderAdapter;
        this.$viewHolder$inlined = baseViewHolder;
        this.$provider$inlined = baseItemBinder;
    }

    public final boolean onLongClick(View v) {
        int position = this.$viewHolder$inlined.getAdapterPosition();
        if (position == -1) {
            return false;
        }
        int position2 = position - this.this$0.getHeaderLayoutCount();
        BaseItemBinder baseItemBinder = this.$provider$inlined;
        BaseViewHolder baseViewHolder = this.$viewHolder$inlined;
        k.b(v, "v");
        return baseItemBinder.onChildLongClick(baseViewHolder, v, this.this$0.getData().get(position2), position2);
    }
}
