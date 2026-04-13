package io.rmiri.skeleton;

import android.content.Context;
import android.util.AttributeSet;
import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.rmiri.skeleton.master.SkeletonMaster;

public class SkeletonView extends SkeletonMaster {
    public SkeletonView(@NonNull Context context) {
        super(context);
    }

    public SkeletonView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SkeletonView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }
}
