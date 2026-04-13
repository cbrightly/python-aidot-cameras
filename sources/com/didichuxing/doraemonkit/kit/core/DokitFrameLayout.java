package com.didichuxing.doraemonkit.kit.core;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DokitFrameLayout extends FrameLayout implements DokitViewInterface {
    public DokitFrameLayout(@NonNull Context context) {
        super(context);
    }

    public DokitFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DokitFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
