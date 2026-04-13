package com.didichuxing.doraemonkit.widget.dropdown;

import android.content.Context;
import android.graphics.drawable.Drawable;

public class Orientation {
    public static final int bottom = 3;
    public static final int left = 0;
    public static final int right = 2;
    public static final int top = 1;
    Context mContext;
    private int orientation;
    private Drawable selectedDrawable;
    private Drawable unSelectedDrawable;

    public Orientation(Context context) {
        this.mContext = context;
    }

    public Drawable getLeft(boolean close) {
        if (this.orientation == 0) {
            return close ? this.unSelectedDrawable : this.selectedDrawable;
        }
        return null;
    }

    public Drawable getTop(boolean close) {
        if (this.orientation == 1) {
            return close ? this.unSelectedDrawable : this.selectedDrawable;
        }
        return null;
    }

    public Drawable getRight(boolean close) {
        if (this.orientation == 2) {
            return close ? this.unSelectedDrawable : this.selectedDrawable;
        }
        return null;
    }

    public Drawable getBottom(boolean close) {
        if (this.orientation == 3) {
            return close ? this.unSelectedDrawable : this.selectedDrawable;
        }
        return null;
    }

    public void init(int orientation2, int menuSelectedIcon, int menuUnselectedIcon) {
        this.unSelectedDrawable = this.mContext.getResources().getDrawable(menuUnselectedIcon);
        this.selectedDrawable = this.mContext.getResources().getDrawable(menuSelectedIcon);
        this.orientation = orientation2;
    }
}
