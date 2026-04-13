package com.google.android.material.progressindicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Px;
import androidx.annotation.StyleRes;
import com.google.android.material.R;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.resources.MaterialResources;

public final class CircularProgressIndicatorSpec extends BaseProgressIndicatorSpec {
    public int indicatorDirection;
    @Px
    public int indicatorInset;
    @Px
    public int indicatorSize;

    public CircularProgressIndicatorSpec(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, R.attr.circularProgressIndicatorStyle);
    }

    public CircularProgressIndicatorSpec(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        this(context, attrs, defStyleAttr, CircularProgressIndicator.DEF_STYLE_RES);
    }

    public CircularProgressIndicatorSpec(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        int defaultIndicatorSize = context.getResources().getDimensionPixelSize(R.dimen.mtrl_progress_circular_size_medium);
        int defaultIndicatorInset = context.getResources().getDimensionPixelSize(R.dimen.mtrl_progress_circular_inset_medium);
        TypedArray a = ThemeEnforcement.obtainStyledAttributes(context, attrs, R.styleable.CircularProgressIndicator, defStyleAttr, defStyleRes, new int[0]);
        this.indicatorSize = Math.max(MaterialResources.getDimensionPixelSize(context, a, R.styleable.CircularProgressIndicator_indicatorSize, defaultIndicatorSize), this.trackThickness * 2);
        this.indicatorInset = MaterialResources.getDimensionPixelSize(context, a, R.styleable.CircularProgressIndicator_indicatorInset, defaultIndicatorInset);
        this.indicatorDirection = a.getInt(R.styleable.CircularProgressIndicator_indicatorDirectionCircular, 0);
        a.recycle();
        validateSpec();
    }

    /* access modifiers changed from: package-private */
    public void validateSpec() {
    }
}
