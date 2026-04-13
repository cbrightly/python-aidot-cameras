package io.rmiri.skeleton.master;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.rmiri.skeleton.R$styleable;
import io.rmiri.skeleton.utils.a;
import io.rmiri.skeleton.utils.c;
import java.util.ArrayList;

public class SkeletonMaster extends RelativeLayout {
    /* access modifiers changed from: protected */
    public b c;
    public int d = 0;

    public SkeletonMaster(@NonNull Context context) {
        super(context);
        b(context, (AttributeSet) null);
    }

    public SkeletonMaster(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        b(context, attrs);
    }

    public SkeletonMaster(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        b(context, attrs);
    }

    public void b(Context context, @Nullable AttributeSet attrs) {
        if (!isInEditMode()) {
            a.a("SkeletonMaster init  " + this.d);
            this.c = new b();
            if (attrs != null) {
                TypedArray typedArray = context.obtainStyledAttributes(attrs, R$styleable.Skeleton);
                this.c.e0(typedArray.getBoolean(R$styleable.Skeleton_SK_isShowSkeleton, true));
                this.c.N(typedArray.getBoolean(R$styleable.Skeleton_SK_autoStartAnimation, true));
                this.c.X(typedArray.getBoolean(R$styleable.Skeleton_SK_isHoldTouchEventsFromChildren, false));
                this.c.P(typedArray.getColor(R$styleable.Skeleton_SK_backgroundMainColor, 17170445));
                this.c.R(typedArray.getColor(R$styleable.Skeleton_SK_highLightColor, b.b));
                this.c.Q(typedArray.getColor(R$styleable.Skeleton_SK_BackgroundViewsColor, b.a));
                this.c.K((long) typedArray.getInt(R$styleable.Skeleton_SK_animationDuration, 1000));
                this.c.J(typedArray.getInt(R$styleable.Skeleton_SK_animationDirection, 1));
                this.c.M(typedArray.getInt(R$styleable.Skeleton_SK_animationNormalType, 2));
                this.c.L(typedArray.getInt(R$styleable.Skeleton_SK_animationFinishType, 2));
                this.c.d0(typedArray.getInt(R$styleable.Skeleton_SK_shapeType, 1));
                this.c.S(typedArray.getDimensionPixelSize(R$styleable.Skeleton_SK_cornerRadius, Integer.MIN_VALUE));
                this.c.V(typedArray.getDimensionPixelSize(R$styleable.Skeleton_SK_cornerRadiusTopLeft, Integer.MIN_VALUE));
                this.c.W(typedArray.getDimensionPixelSize(R$styleable.Skeleton_SK_cornerRadiusTopRight, Integer.MIN_VALUE));
                this.c.U(typedArray.getDimensionPixelSize(R$styleable.Skeleton_SK_cornerRadiusBottomLeft, Integer.MIN_VALUE));
                this.c.T(typedArray.getDimensionPixelSize(R$styleable.Skeleton_SK_cornerRadiusBottomLRight, Integer.MIN_VALUE));
                this.c.Y((float) typedArray.getDimensionPixelSize(R$styleable.Skeleton_SK_padding, Integer.MIN_VALUE));
                this.c.c0((float) typedArray.getDimensionPixelSize(R$styleable.Skeleton_SK_paddingTop, Integer.MIN_VALUE));
                this.c.a0((float) typedArray.getDimensionPixelSize(R$styleable.Skeleton_SK_paddingLeft, Integer.MIN_VALUE));
                this.c.Z((float) typedArray.getDimensionPixelSize(R$styleable.Skeleton_SK_paddingBottom, Integer.MIN_VALUE));
                this.c.b0((float) typedArray.getDimensionPixelSize(R$styleable.Skeleton_SK_paddingRight, Integer.MIN_VALUE));
                if (this.c.v() == 3) {
                    this.c.h0(typedArray.getInt(R$styleable.Skeleton_SK_textLineNumber, 3));
                    this.c.g0(typedArray.getInt(R$styleable.Skeleton_SK_textLineLastWidth, 2));
                    this.c.f0(typedArray.getDimensionPixelSize(R$styleable.Skeleton_SK_textLineHeight, c.a(getContext(), 24)));
                    this.c.i0(typedArray.getDimensionPixelSize(R$styleable.Skeleton_SK_textLineSpaceVertical, c.a(getContext(), 4)));
                }
                typedArray.recycle();
            }
        }
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return this.c.G() || super.onInterceptTouchEvent(motionEvent);
    }

    public ArrayList<View> a(View v) {
        a.a("SkeletonMaster getAllChildren " + this.d);
        if (!(v instanceof ViewGroup)) {
            ArrayList<View> viewArrayList = new ArrayList<>();
            viewArrayList.add(v);
            return viewArrayList;
        }
        ArrayList<View> result = new ArrayList<>();
        ViewGroup viewGroup = (ViewGroup) v;
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View child = viewGroup.getChildAt(i);
            ArrayList<View> viewArrayList2 = new ArrayList<>();
            viewArrayList2.add(v);
            viewArrayList2.addAll(a(child));
            result.addAll(viewArrayList2);
        }
        return result;
    }

    public b getSkeletonModel() {
        return this.c;
    }

    public void setSkeletonModel(b skeletonModel) {
        this.c = skeletonModel;
    }

    public void setHoldTouchEventsFromChildren(boolean isHoldTouchEventsFromChildren) {
        this.c.X(isHoldTouchEventsFromChildren);
    }

    public int getPosition() {
        return this.d;
    }

    public void setPosition(int position) {
        this.d = position;
    }
}
