package com.didichuxing.doraemonkit.widget.bravh;

import android.view.View;
import com.didichuxing.doraemonkit.widget.bravh.binder.BaseItemBinder;
import com.didichuxing.doraemonkit.widget.bravh.viewholder.BaseViewHolder;
import kotlin.jvm.internal.k;
import kotlin.l;

@l(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\u0010\u0006\u001a\u00020\u00032\u000e\u0010\u0002\u001a\n \u0001*\u0004\u0018\u00010\u00000\u0000H\n¢\u0006\u0004\b\u0004\u0010\u0005"}, d2 = {"Landroid/view/View;", "kotlin.jvm.PlatformType", "it", "", "onLongClick", "(Landroid/view/View;)Z", "<anonymous>"}, k = 3, mv = {1, 4, 0})
/* compiled from: BaseBinderAdapter.kt */
public final class BaseBinderAdapter$bindClick$2 implements View.OnLongClickListener {
    final /* synthetic */ BaseViewHolder $viewHolder;
    final /* synthetic */ BaseBinderAdapter this$0;

    BaseBinderAdapter$bindClick$2(BaseBinderAdapter baseBinderAdapter, BaseViewHolder baseViewHolder) {
        this.this$0 = baseBinderAdapter;
        this.$viewHolder = baseViewHolder;
    }

    public final boolean onLongClick(View it) {
        int position = this.$viewHolder.getAdapterPosition();
        if (position == -1) {
            return false;
        }
        int position2 = position - this.this$0.getHeaderLayoutCount();
        BaseItemBinder binder = this.this$0.getItemBinder(this.$viewHolder.getItemViewType());
        BaseViewHolder baseViewHolder = this.$viewHolder;
        k.b(it, "it");
        return binder.onLongClick(baseViewHolder, it, this.this$0.getData().get(position2), position2);
    }
}
