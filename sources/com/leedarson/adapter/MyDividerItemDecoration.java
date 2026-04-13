package com.leedarson.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;

public class MyDividerItemDecoration extends RecyclerView.ItemDecoration {
    private static final int[] a = {16843284};
    public static ChangeQuickRedirect changeQuickRedirect;
    private Drawable b;
    private int c;
    private final Rect d = new Rect();

    public MyDividerItemDecoration(Context context, int orientation) {
        TypedArray a2 = context.obtainStyledAttributes(a);
        Drawable drawable = a2.getDrawable(0);
        this.b = drawable;
        if (drawable == null) {
            Log.w("DividerItem", "@android:attr/listDivider was not set in the theme used for this DividerItemDecoration. Please set that attribute all call setDrawable()");
        }
        a2.recycle();
        setOrientation(orientation);
    }

    public void setOrientation(int orientation) {
        Object[] objArr = {new Integer(orientation)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 88, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            if (orientation == 0 || orientation == 1) {
                this.c = orientation;
                return;
            }
            throw new IllegalArgumentException("Invalid orientation. It should be either HORIZONTAL or VERTICAL");
        }
    }

    public void setDrawable(@NonNull Drawable drawable) {
        if (!PatchProxy.proxy(new Object[]{drawable}, this, changeQuickRedirect, false, 89, new Class[]{Drawable.class}, Void.TYPE).isSupported) {
            if (drawable != null) {
                this.b = drawable;
                return;
            }
            throw new IllegalArgumentException("Drawable cannot be null.");
        }
    }

    public void onDraw(Canvas c2, RecyclerView parent, RecyclerView.State state) {
        Class[] clsArr = {Canvas.class, RecyclerView.class, RecyclerView.State.class};
        if (!PatchProxy.proxy(new Object[]{c2, parent, state}, this, changeQuickRedirect, false, 90, clsArr, Void.TYPE).isSupported) {
            if (parent.getLayoutManager() != null && this.b != null) {
                if (this.c == 1) {
                    drawVertical(c2, parent);
                } else {
                    drawHorizontal(c2, parent);
                }
            }
        }
    }

    private void drawVertical(Canvas canvas, RecyclerView parent) {
        int right;
        int left;
        Class[] clsArr = {Canvas.class, RecyclerView.class};
        if (!PatchProxy.proxy(new Object[]{canvas, parent}, this, changeQuickRedirect, false, 91, clsArr, Void.TYPE).isSupported) {
            canvas.save();
            if (parent.getClipToPadding()) {
                left = parent.getPaddingLeft();
                right = parent.getWidth() - parent.getPaddingRight();
                canvas.clipRect(left, parent.getPaddingTop(), right, parent.getHeight() - parent.getPaddingBottom());
            } else {
                left = 0;
                right = parent.getWidth();
            }
            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount - 1; i++) {
                View child = parent.getChildAt(i);
                parent.getDecoratedBoundsWithMargins(child, this.d);
                int bottom = this.d.bottom + Math.round(child.getTranslationY());
                this.b.setBounds(left, bottom - this.b.getIntrinsicHeight(), right, bottom);
                this.b.draw(canvas);
            }
            canvas.restore();
        }
    }

    private void drawHorizontal(Canvas canvas, RecyclerView parent) {
        int bottom;
        int top;
        Class[] clsArr = {Canvas.class, RecyclerView.class};
        if (!PatchProxy.proxy(new Object[]{canvas, parent}, this, changeQuickRedirect, false, 92, clsArr, Void.TYPE).isSupported) {
            canvas.save();
            if (parent.getClipToPadding()) {
                top = parent.getPaddingTop();
                bottom = parent.getHeight() - parent.getPaddingBottom();
                canvas.clipRect(parent.getPaddingLeft(), top, parent.getWidth() - parent.getPaddingRight(), bottom);
            } else {
                top = 0;
                bottom = parent.getHeight();
            }
            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = parent.getChildAt(i);
                parent.getLayoutManager().getDecoratedBoundsWithMargins(child, this.d);
                int right = this.d.right + Math.round(child.getTranslationX());
                this.b.setBounds(right - this.b.getIntrinsicWidth(), top, right, bottom);
                this.b.draw(canvas);
            }
            canvas.restore();
        }
    }

    public void getItemOffsets(Rect outRect, View view, RecyclerView recyclerView, RecyclerView.State state) {
        Class[] clsArr = {Rect.class, View.class, RecyclerView.class, RecyclerView.State.class};
        if (!PatchProxy.proxy(new Object[]{outRect, view, recyclerView, state}, this, changeQuickRedirect, false, 93, clsArr, Void.TYPE).isSupported) {
            Drawable drawable = this.b;
            if (drawable == null) {
                outRect.set(0, 0, 0, 0);
            } else if (this.c == 1) {
                outRect.set(0, 0, 0, drawable.getIntrinsicHeight());
            } else {
                outRect.set(0, 0, drawable.getIntrinsicWidth(), 0);
            }
        }
    }
}
