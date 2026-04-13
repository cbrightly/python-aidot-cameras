package com.lzf.easyfloat.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;
import com.lzf.easyfloat.interfaces.OnTouchRangeListener;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: BaseSwitchView.kt */
public abstract class BaseSwitchView extends RelativeLayout {
    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public BaseSwitchView(@NotNull Context context) {
        this(context, (AttributeSet) null, 0, 6, (DefaultConstructorMarker) null);
        k.e(context, "context");
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public BaseSwitchView(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, (DefaultConstructorMarker) null);
        k.e(context, "context");
    }

    public void _$_clearFindViewByIdCache() {
    }

    public abstract void setTouchRangeListener(@NotNull MotionEvent motionEvent, @Nullable OnTouchRangeListener onTouchRangeListener);

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ BaseSwitchView(Context context, AttributeSet attributeSet, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i2 & 2) != 0 ? null : attributeSet, (i2 & 4) != 0 ? 0 : i);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public BaseSwitchView(@NotNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        k.e(context, "context");
    }

    public static /* synthetic */ void setTouchRangeListener$default(BaseSwitchView baseSwitchView, MotionEvent motionEvent, OnTouchRangeListener onTouchRangeListener, int i, Object obj) {
        if (obj == null) {
            if ((i & 2) != 0) {
                onTouchRangeListener = null;
            }
            baseSwitchView.setTouchRangeListener(motionEvent, onTouchRangeListener);
            return;
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: setTouchRangeListener");
    }
}
