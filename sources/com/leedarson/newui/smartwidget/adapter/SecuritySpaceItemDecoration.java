package com.leedarson.newui.smartwidget.adapter;

import android.graphics.Rect;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;

public class SecuritySpaceItemDecoration extends RecyclerView.ItemDecoration {
    public static ChangeQuickRedirect changeQuickRedirect;
    private int a;

    public SecuritySpaceItemDecoration(int space) {
        this.a = space;
    }

    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (!PatchProxy.proxy(new Object[]{outRect, view, parent, state}, this, changeQuickRedirect, false, 4791, new Class[]{Rect.class, View.class, RecyclerView.class, RecyclerView.State.class}, Void.TYPE).isSupported) {
            if (parent.getChildLayoutPosition(view) == 0) {
                outRect.top = this.a;
            }
            outRect.bottom = this.a;
        }
    }
}
