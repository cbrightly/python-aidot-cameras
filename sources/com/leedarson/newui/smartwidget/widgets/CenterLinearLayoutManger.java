package com.leedarson.newui.smartwidget.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;

public class CenterLinearLayoutManger extends LinearLayoutManager {
    public static ChangeQuickRedirect changeQuickRedirect;

    public CenterLinearLayoutManger(Context context) {
        super(context);
    }

    public CenterLinearLayoutManger(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
        Object[] objArr = {recyclerView, state, new Integer(position)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 4792, new Class[]{RecyclerView.class, RecyclerView.State.class, Integer.TYPE}, Void.TYPE).isSupported) {
            RecyclerView.SmoothScroller smoothScroller = new CenterSmoothScroller(recyclerView.getContext());
            smoothScroller.setTargetPosition(position);
            startSmoothScroll(smoothScroller);
        }
    }

    public static class CenterSmoothScroller extends LinearSmoothScroller {
        public static ChangeQuickRedirect changeQuickRedirect;

        public CenterSmoothScroller(Context context) {
            super(context);
        }

        public int calculateDtToFit(int viewStart, int viewEnd, int boxStart, int boxEnd, int snapPreference) {
            return (((boxEnd - boxStart) / 2) + boxStart) - (((viewEnd - viewStart) / 2) + viewStart);
        }

        public float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
            return 100.0f / ((float) displayMetrics.densityDpi);
        }
    }
}
