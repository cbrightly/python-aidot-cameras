package com.zhy.view.flowlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

public class FlowLayout extends ViewGroup {
    protected List<List<View>> c;
    protected List<Integer> d;
    protected List<Integer> f;
    private int q;
    private List<View> x;

    public FlowLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.c = new ArrayList();
        this.d = new ArrayList();
        this.f = new ArrayList();
        this.x = new ArrayList();
        TypedArray ta = context.obtainStyledAttributes(attrs, R$styleable.TagFlowLayout);
        this.q = ta.getInt(R$styleable.TagFlowLayout_tag_gravity, -1);
        ta.recycle();
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context) {
        this(context, (AttributeSet) null);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int i;
        int i2;
        int sizeWidth;
        int sizeHeight;
        int lineHeight;
        int lineWidth;
        int sizeWidth2 = View.MeasureSpec.getSize(widthMeasureSpec);
        int modeWidth = View.MeasureSpec.getMode(widthMeasureSpec);
        int sizeHeight2 = View.MeasureSpec.getSize(heightMeasureSpec);
        int modeHeight = View.MeasureSpec.getMode(heightMeasureSpec);
        int width = 0;
        int height = 0;
        int lineHeight2 = 0;
        int lineHeight3 = 0;
        int cCount = getChildCount();
        int i3 = 0;
        while (i3 < cCount) {
            View child = getChildAt(i3);
            if (child.getVisibility() != 8) {
                measureChild(child, widthMeasureSpec, heightMeasureSpec);
                ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) child.getLayoutParams();
                sizeHeight = sizeHeight2;
                int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
                View view = child;
                int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
                sizeWidth = sizeWidth2;
                if (lineHeight2 + childWidth > (sizeWidth2 - getPaddingLeft()) - getPaddingRight()) {
                    width = Math.max(width, lineHeight2);
                    lineWidth = childWidth;
                    height += lineHeight3;
                    lineHeight = childHeight;
                } else {
                    lineWidth = lineHeight2 + childWidth;
                    lineHeight = Math.max(lineHeight3, childHeight);
                }
                if (i3 == cCount - 1) {
                    width = Math.max(lineWidth, width);
                    height += lineHeight;
                    lineHeight3 = lineHeight;
                    lineHeight2 = lineWidth;
                } else {
                    lineHeight3 = lineHeight;
                    lineHeight2 = lineWidth;
                }
            } else if (i3 == cCount - 1) {
                width = Math.max(lineHeight2, width);
                height += lineHeight3;
                int i4 = widthMeasureSpec;
                int i5 = heightMeasureSpec;
                sizeWidth = sizeWidth2;
                sizeHeight = sizeHeight2;
            } else {
                int i6 = widthMeasureSpec;
                int i7 = heightMeasureSpec;
                sizeWidth = sizeWidth2;
                sizeHeight = sizeHeight2;
            }
            i3++;
            sizeHeight2 = sizeHeight;
            sizeWidth2 = sizeWidth;
        }
        int i8 = widthMeasureSpec;
        int i9 = heightMeasureSpec;
        int sizeWidth3 = sizeWidth2;
        int sizeHeight3 = sizeHeight2;
        if (modeWidth == 1073741824) {
            i = sizeWidth3;
        } else {
            i = getPaddingLeft() + width + getPaddingRight();
        }
        if (modeHeight == 1073741824) {
            i2 = sizeHeight3;
        } else {
            i2 = getPaddingTop() + height + getPaddingBottom();
        }
        setMeasuredDimension(i, i2);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int l, int t, int r, int b) {
        int i;
        FlowLayout flowLayout = this;
        flowLayout.c.clear();
        flowLayout.d.clear();
        flowLayout.f.clear();
        flowLayout.x.clear();
        int width = getWidth();
        int lineWidth = 0;
        int lineHeight = 0;
        int cCount = getChildCount();
        int i2 = 0;
        while (true) {
            i = 8;
            if (i2 >= cCount) {
                break;
            }
            View child = flowLayout.getChildAt(i2);
            if (child.getVisibility() != 8) {
                ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) child.getLayoutParams();
                int childWidth = child.getMeasuredWidth();
                int childHeight = child.getMeasuredHeight();
                if (childWidth + lineWidth + lp.leftMargin + lp.rightMargin > (width - getPaddingLeft()) - getPaddingRight()) {
                    flowLayout.d.add(Integer.valueOf(lineHeight));
                    flowLayout.c.add(flowLayout.x);
                    flowLayout.f.add(Integer.valueOf(lineWidth));
                    lineWidth = 0;
                    lineHeight = lp.topMargin + childHeight + lp.bottomMargin;
                    flowLayout.x = new ArrayList();
                }
                lineWidth += lp.leftMargin + childWidth + lp.rightMargin;
                lineHeight = Math.max(lineHeight, lp.topMargin + childHeight + lp.bottomMargin);
                flowLayout.x.add(child);
            }
            i2++;
        }
        flowLayout.d.add(Integer.valueOf(lineHeight));
        flowLayout.f.add(Integer.valueOf(lineWidth));
        flowLayout.c.add(flowLayout.x);
        int left = getPaddingLeft();
        int top = getPaddingTop();
        int lineNum = flowLayout.c.size();
        int i3 = 0;
        while (i3 < lineNum) {
            flowLayout.x = flowLayout.c.get(i3);
            int lineHeight2 = flowLayout.d.get(i3).intValue();
            int currentLineWidth = flowLayout.f.get(i3).intValue();
            switch (flowLayout.q) {
                case -1:
                    left = getPaddingLeft();
                    break;
                case 0:
                    left = ((width - currentLineWidth) / 2) + getPaddingLeft();
                    break;
                case 1:
                    left = (width - currentLineWidth) + getPaddingLeft();
                    break;
            }
            int j = 0;
            while (j < flowLayout.x.size()) {
                View child2 = flowLayout.x.get(j);
                if (child2.getVisibility() != i) {
                    ViewGroup.MarginLayoutParams lp2 = (ViewGroup.MarginLayoutParams) child2.getLayoutParams();
                    int lc = lp2.leftMargin + left;
                    int tc = lp2.topMargin + top;
                    int rc = lc + child2.getMeasuredWidth();
                    int bc = tc + child2.getMeasuredHeight();
                    child2.layout(lc, tc, rc, bc);
                    int i4 = bc;
                    left += child2.getMeasuredWidth() + lp2.leftMargin + lp2.rightMargin;
                }
                j++;
                i = 8;
                flowLayout = this;
            }
            top += lineHeight2;
            i3++;
            i = 8;
            flowLayout = this;
        }
    }

    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new ViewGroup.MarginLayoutParams(getContext(), attrs);
    }

    /* access modifiers changed from: protected */
    public ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new ViewGroup.MarginLayoutParams(-2, -2);
    }

    /* access modifiers changed from: protected */
    public ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new ViewGroup.MarginLayoutParams(p);
    }
}
