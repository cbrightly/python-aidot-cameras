package com.leedarson.ui.decorations;

import android.graphics.Rect;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.utils.n;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;

public class SpaceItemAlbumDecoration extends RecyclerView.ItemDecoration {
    public static ChangeQuickRedirect changeQuickRedirect;
    private int a;
    private int b;

    public SpaceItemAlbumDecoration(int left) {
        this.a = left;
        this.b = left;
    }

    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        Class[] clsArr = {Rect.class, View.class, RecyclerView.class, RecyclerView.State.class};
        if (!PatchProxy.proxy(new Object[]{outRect, view, parent, state}, this, changeQuickRedirect, false, 11449, clsArr, Void.TYPE).isSupported) {
            outRect.bottom = -5;
            outRect.left = 0;
            outRect.right = 0;
            int childLayoutPosition = parent.getChildLayoutPosition(view) % 3;
            int itemPosition = parent.getChildAdapterPosition(view);
            int itemCount = state.getItemCount();
            if (itemCount > 0 && itemPosition == itemCount - 1) {
                outRect.bottom = n.a(BaseApplication.b(), 18.0f);
            }
        }
    }
}
