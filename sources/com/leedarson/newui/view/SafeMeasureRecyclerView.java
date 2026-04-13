package com.leedarson.newui.view;

import android.content.Context;
import android.util.AttributeSet;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;

public class SafeMeasureRecyclerView extends RecyclerView {
    public static ChangeQuickRedirect changeQuickRedirect;

    public SafeMeasureRecyclerView(@NonNull Context context) {
        super(context);
    }

    public SafeMeasureRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SafeMeasureRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void onMeasure(int widthSpec, int heightSpec) {
        Object[] objArr = {new Integer(widthSpec), new Integer(heightSpec)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5232, new Class[]{cls, cls}, Void.TYPE).isSupported) {
            try {
                super.onMeasure(widthSpec, heightSpec);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
