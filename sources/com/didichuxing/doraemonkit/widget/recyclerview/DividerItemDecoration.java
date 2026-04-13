package com.didichuxing.doraemonkit.widget.recyclerview;

import android.annotation.TargetApi;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DividerItemDecoration extends RecyclerView.ItemDecoration {
    public static final int HORIZONTAL = 0;
    private static final String TAG = "TitleItem";
    public static final int VERTICAL = 1;
    private final Rect mBounds = new Rect();
    private Drawable mDivider;
    private int mOrientation;
    private boolean mShowFooterDivider = true;
    private boolean mShowHeaderDivider;

    public DividerItemDecoration(int orientation) {
        setOrientation(orientation);
    }

    public void setOrientation(int orientation) {
        if (orientation == 0 || orientation == 1) {
            this.mOrientation = orientation;
            return;
        }
        throw new IllegalArgumentException("Invalid orientation. It should be either HORIZONTAL or VERTICAL");
    }

    public void setDrawable(@NonNull Drawable drawable) {
        this.mDivider = drawable;
    }

    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (parent.getLayoutManager() != null && this.mDivider != null) {
            if (this.mOrientation == 1) {
                drawVertical(c, parent);
            } else {
                drawHorizontal(c, parent);
            }
        }
    }

    public void showHeaderDivider(boolean show) {
        this.mShowHeaderDivider = show;
    }

    public void showFooterDivider(boolean show) {
        this.mShowFooterDivider = show;
    }

    private void drawVertical(Canvas canvas, RecyclerView parent) {
        int right;
        int left;
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
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            parent.getDecoratedBoundsWithMargins(child, this.mBounds);
            if (i == 0 && this.mShowHeaderDivider) {
                Drawable drawable = this.mDivider;
                drawable.setBounds(left, 0, right, drawable.getIntrinsicHeight() + 0);
                this.mDivider.draw(canvas);
            }
            if (i != childCount - 1 || this.mShowFooterDivider) {
                int bottom = this.mBounds.bottom + Math.round(child.getTranslationY());
                this.mDivider.setBounds(left, bottom - this.mDivider.getIntrinsicHeight(), right, bottom);
                this.mDivider.draw(canvas);
            }
        }
        canvas.restore();
    }

    @TargetApi(21)
    private void drawHorizontal(Canvas canvas, RecyclerView parent) {
        int bottom;
        int top;
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
            parent.getLayoutManager().getDecoratedBoundsWithMargins(child, this.mBounds);
            int right = this.mBounds.right + Math.round(child.getTranslationX());
            this.mDivider.setBounds(right - this.mDivider.getIntrinsicWidth(), top, right, bottom);
            this.mDivider.draw(canvas);
        }
        canvas.restore();
    }

    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        Drawable drawable = this.mDivider;
        if (drawable == null) {
            outRect.set(0, 0, 0, 0);
        } else if (this.mOrientation == 1) {
            outRect.set(0, 0, 0, drawable.getIntrinsicHeight());
        } else {
            outRect.set(0, 0, drawable.getIntrinsicWidth(), 0);
        }
    }
}
