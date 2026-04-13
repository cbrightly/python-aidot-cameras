package com.leedarson.newui.pages.layoutmanager;

import android.content.Context;
import android.util.AttributeSet;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;

public class WrapSaftyContentGridLayoutManager extends GridLayoutManager {
    public static ChangeQuickRedirect changeQuickRedirect;

    public WrapSaftyContentGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public WrapSaftyContentGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        Class[] clsArr = {RecyclerView.Recycler.class, RecyclerView.State.class};
        if (!PatchProxy.proxy(new Object[]{recycler, state}, this, changeQuickRedirect, false, 4319, clsArr, Void.TYPE).isSupported) {
            try {
                super.onLayoutChildren(recycler, state);
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }
}
