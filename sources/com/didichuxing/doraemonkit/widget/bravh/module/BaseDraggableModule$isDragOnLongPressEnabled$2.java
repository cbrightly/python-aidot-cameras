package com.didichuxing.doraemonkit.widget.bravh.module;

import android.view.MotionEvent;
import android.view.View;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.didichuxing.doraemonkit.R;
import kotlin.TypeCastException;
import kotlin.jvm.internal.k;
import kotlin.l;

@l(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\u0010\b\u001a\u00020\u00052\u000e\u0010\u0002\u001a\n \u0001*\u0004\u0018\u00010\u00000\u00002\u000e\u0010\u0004\u001a\n \u0001*\u0004\u0018\u00010\u00030\u0003H\n¢\u0006\u0004\b\u0006\u0010\u0007"}, d2 = {"Landroid/view/View;", "kotlin.jvm.PlatformType", "v", "Landroid/view/MotionEvent;", "event", "", "onTouch", "(Landroid/view/View;Landroid/view/MotionEvent;)Z", "<anonymous>"}, k = 3, mv = {1, 4, 0})
/* compiled from: DraggableModule.kt */
public final class BaseDraggableModule$isDragOnLongPressEnabled$2 implements View.OnTouchListener {
    final /* synthetic */ BaseDraggableModule this$0;

    BaseDraggableModule$isDragOnLongPressEnabled$2(BaseDraggableModule baseDraggableModule) {
        this.this$0 = baseDraggableModule;
    }

    public final boolean onTouch(View v, MotionEvent event) {
        k.b(event, NotificationCompat.CATEGORY_EVENT);
        if (event.getAction() != 0 || this.this$0.isDragOnLongPressEnabled()) {
            return false;
        }
        if (this.this$0.isDragEnabled()) {
            ItemTouchHelper itemTouchHelper = this.this$0.getItemTouchHelper();
            Object tag = v.getTag(R.id.dokit_baseQuickAdapter_viewholder_support);
            if (tag != null) {
                itemTouchHelper.startDrag((RecyclerView.ViewHolder) tag);
            } else {
                throw new TypeCastException("null cannot be cast to non-null type androidx.recyclerview.widget.RecyclerView.ViewHolder");
            }
        }
        return true;
    }
}
