package com.leedarson.ui;

import android.graphics.Rect;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import com.meituan.robust.ChangeQuickRedirect;

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
    public static ChangeQuickRedirect changeQuickRedirect;
    private int a;
    private int b;

    public SpaceItemDecoration(int left) {
        this.a = left;
        this.b = left;
    }

    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = this.a;
        outRect.bottom = this.b;
    }
}
