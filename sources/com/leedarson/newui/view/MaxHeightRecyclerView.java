package com.leedarson.newui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import com.leedarson.R$styleable;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;

public class MaxHeightRecyclerView extends RecyclerView {
    public static ChangeQuickRedirect changeQuickRedirect;
    private int c;

    public MaxHeightRecyclerView(Context context) {
        super(context);
    }

    public MaxHeightRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        a(context, attrs);
    }

    public MaxHeightRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        a(context, attrs);
    }

    private void a(Context context, AttributeSet attrs) {
        Class[] clsArr = {Context.class, AttributeSet.class};
        if (!PatchProxy.proxy(new Object[]{context, attrs}, this, changeQuickRedirect, false, 5181, clsArr, Void.TYPE).isSupported) {
            TypedArray arr = context.obtainStyledAttributes(attrs, R$styleable.MaxHeightRecyclerView);
            this.c = arr.getLayoutDimension(R$styleable.MaxHeightRecyclerView_maxHeight, this.c);
            arr.recycle();
        }
    }

    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Object[] objArr = {new Integer(widthMeasureSpec), new Integer(heightMeasureSpec)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5182, new Class[]{cls, cls}, Void.TYPE).isSupported) {
            int i = this.c;
            if (i > 0) {
                heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(i, Integer.MIN_VALUE);
            }
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}
