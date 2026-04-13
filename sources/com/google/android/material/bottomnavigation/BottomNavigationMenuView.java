package com.google.android.material.bottomnavigation;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.core.view.ViewCompat;
import com.google.android.material.R;
import com.google.android.material.navigation.NavigationBarItemView;
import com.google.android.material.navigation.NavigationBarMenuView;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class BottomNavigationMenuView extends NavigationBarMenuView {
    private final int activeItemMaxWidth;
    private final int activeItemMinWidth;
    private final int inactiveItemMaxWidth;
    private final int inactiveItemMinWidth;
    private final int itemHeight;
    private boolean itemHorizontalTranslationEnabled;
    private int[] tempChildWidths = new int[5];

    public BottomNavigationMenuView(@NonNull Context context) {
        super(context);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(-2, -2);
        params.gravity = 17;
        setLayoutParams(params);
        Resources res = getResources();
        this.inactiveItemMaxWidth = res.getDimensionPixelSize(R.dimen.design_bottom_navigation_item_max_width);
        this.inactiveItemMinWidth = res.getDimensionPixelSize(R.dimen.design_bottom_navigation_item_min_width);
        this.activeItemMaxWidth = res.getDimensionPixelSize(R.dimen.design_bottom_navigation_active_item_max_width);
        this.activeItemMinWidth = res.getDimensionPixelSize(R.dimen.design_bottom_navigation_active_item_min_width);
        this.itemHeight = res.getDimensionPixelSize(R.dimen.design_bottom_navigation_height);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        MenuBuilder menu = getMenu();
        int width = View.MeasureSpec.getSize(widthMeasureSpec);
        int visibleCount = menu.getVisibleItems().size();
        int totalCount = getChildCount();
        int heightSpec = View.MeasureSpec.makeMeasureSpec(this.itemHeight, 1073741824);
        int i = 8;
        if (!isShifting(getLabelVisibilityMode(), visibleCount) || !isItemHorizontalTranslationEnabled()) {
            int childWidth = Math.min(width / (visibleCount == 0 ? 1 : visibleCount), this.activeItemMaxWidth);
            int extra = width - (childWidth * visibleCount);
            for (int i2 = 0; i2 < totalCount; i2++) {
                if (getChildAt(i2).getVisibility() != 8) {
                    int[] iArr = this.tempChildWidths;
                    iArr[i2] = childWidth;
                    if (extra > 0) {
                        iArr[i2] = iArr[i2] + 1;
                        extra--;
                    }
                } else {
                    this.tempChildWidths[i2] = 0;
                }
            }
        } else {
            View activeChild = getChildAt(getSelectedItemPosition());
            int activeItemWidth = this.activeItemMinWidth;
            if (activeChild.getVisibility() != 8) {
                activeChild.measure(View.MeasureSpec.makeMeasureSpec(this.activeItemMaxWidth, Integer.MIN_VALUE), heightSpec);
                activeItemWidth = Math.max(activeItemWidth, activeChild.getMeasuredWidth());
            }
            int inactiveCount = visibleCount - (activeChild.getVisibility() != 8 ? 1 : 0);
            int activeWidth = Math.min(width - (this.inactiveItemMinWidth * inactiveCount), Math.min(activeItemWidth, this.activeItemMaxWidth));
            int inactiveWidth = Math.min((width - activeWidth) / (inactiveCount == 0 ? 1 : inactiveCount), this.inactiveItemMaxWidth);
            int extra2 = (width - activeWidth) - (inactiveWidth * inactiveCount);
            int i3 = 0;
            while (i3 < totalCount) {
                if (getChildAt(i3).getVisibility() != i) {
                    this.tempChildWidths[i3] = i3 == getSelectedItemPosition() ? activeWidth : inactiveWidth;
                    if (extra2 > 0) {
                        int[] iArr2 = this.tempChildWidths;
                        iArr2[i3] = iArr2[i3] + 1;
                        extra2--;
                    }
                } else {
                    this.tempChildWidths[i3] = 0;
                }
                i3++;
                i = 8;
            }
        }
        int totalWidth = 0;
        for (int i4 = 0; i4 < totalCount; i4++) {
            View child = getChildAt(i4);
            if (child.getVisibility() != 8) {
                child.measure(View.MeasureSpec.makeMeasureSpec(this.tempChildWidths[i4], 1073741824), heightSpec);
                child.getLayoutParams().width = child.getMeasuredWidth();
                totalWidth += child.getMeasuredWidth();
            }
        }
        setMeasuredDimension(View.resolveSizeAndState(totalWidth, View.MeasureSpec.makeMeasureSpec(totalWidth, 1073741824), 0), View.resolveSizeAndState(this.itemHeight, heightSpec, 0));
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int count = getChildCount();
        int width = right - left;
        int height = bottom - top;
        int used = 0;
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != 8) {
                if (ViewCompat.getLayoutDirection(this) == 1) {
                    child.layout((width - used) - child.getMeasuredWidth(), 0, width - used, height);
                } else {
                    child.layout(used, 0, child.getMeasuredWidth() + used, height);
                }
                used += child.getMeasuredWidth();
            }
        }
    }

    public void setItemHorizontalTranslationEnabled(boolean itemHorizontalTranslationEnabled2) {
        this.itemHorizontalTranslationEnabled = itemHorizontalTranslationEnabled2;
    }

    public boolean isItemHorizontalTranslationEnabled() {
        return this.itemHorizontalTranslationEnabled;
    }

    /* access modifiers changed from: protected */
    @NonNull
    public NavigationBarItemView createNavigationBarItemView(@NonNull Context context) {
        return new BottomNavigationItemView(context);
    }
}
