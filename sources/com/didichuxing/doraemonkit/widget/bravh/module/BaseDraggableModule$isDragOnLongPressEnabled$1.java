package com.didichuxing.doraemonkit.widget.bravh.module;

import android.view.View;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.didichuxing.doraemonkit.R;
import kotlin.TypeCastException;
import kotlin.l;

@l(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\u0010\u0006\u001a\u00020\u00032\u000e\u0010\u0002\u001a\n \u0001*\u0004\u0018\u00010\u00000\u0000H\n¢\u0006\u0004\b\u0004\u0010\u0005"}, d2 = {"Landroid/view/View;", "kotlin.jvm.PlatformType", "v", "", "onLongClick", "(Landroid/view/View;)Z", "<anonymous>"}, k = 3, mv = {1, 4, 0})
/* compiled from: DraggableModule.kt */
public final class BaseDraggableModule$isDragOnLongPressEnabled$1 implements View.OnLongClickListener {
    final /* synthetic */ BaseDraggableModule this$0;

    BaseDraggableModule$isDragOnLongPressEnabled$1(BaseDraggableModule baseDraggableModule) {
        this.this$0 = baseDraggableModule;
    }

    public final boolean onLongClick(View v) {
        if (!this.this$0.isDragEnabled()) {
            return true;
        }
        ItemTouchHelper itemTouchHelper = this.this$0.getItemTouchHelper();
        Object tag = v.getTag(R.id.dokit_baseQuickAdapter_viewholder_support);
        if (tag != null) {
            itemTouchHelper.startDrag((RecyclerView.ViewHolder) tag);
            return true;
        }
        throw new TypeCastException("null cannot be cast to non-null type androidx.recyclerview.widget.RecyclerView.ViewHolder");
    }
}
