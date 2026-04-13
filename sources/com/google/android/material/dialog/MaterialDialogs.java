package com.google.android.material.dialog;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.os.Build;
import android.util.AttributeSet;
import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import com.google.android.material.R;
import com.google.android.material.internal.ThemeEnforcement;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class MaterialDialogs {
    private MaterialDialogs() {
    }

    @NonNull
    public static InsetDrawable insetDrawable(@Nullable Drawable drawable, @NonNull Rect backgroundInsets) {
        return new InsetDrawable(drawable, backgroundInsets.left, backgroundInsets.top, backgroundInsets.right, backgroundInsets.bottom);
    }

    @NonNull
    public static Rect getDialogBackgroundInsets(@NonNull Context context, @AttrRes int defaultStyleAttribute, int defaultStyleResource) {
        TypedArray attributes = ThemeEnforcement.obtainStyledAttributes(context, (AttributeSet) null, R.styleable.MaterialAlertDialog, defaultStyleAttribute, defaultStyleResource, new int[0]);
        int backgroundInsetStart = attributes.getDimensionPixelSize(R.styleable.MaterialAlertDialog_backgroundInsetStart, context.getResources().getDimensionPixelSize(R.dimen.mtrl_alert_dialog_background_inset_start));
        int backgroundInsetTop = attributes.getDimensionPixelSize(R.styleable.MaterialAlertDialog_backgroundInsetTop, context.getResources().getDimensionPixelSize(R.dimen.mtrl_alert_dialog_background_inset_top));
        int backgroundInsetEnd = attributes.getDimensionPixelSize(R.styleable.MaterialAlertDialog_backgroundInsetEnd, context.getResources().getDimensionPixelSize(R.dimen.mtrl_alert_dialog_background_inset_end));
        int backgroundInsetBottom = attributes.getDimensionPixelSize(R.styleable.MaterialAlertDialog_backgroundInsetBottom, context.getResources().getDimensionPixelSize(R.dimen.mtrl_alert_dialog_background_inset_bottom));
        attributes.recycle();
        int backgroundInsetLeft = backgroundInsetStart;
        int backgroundInsetRight = backgroundInsetEnd;
        if (Build.VERSION.SDK_INT >= 17 && context.getResources().getConfiguration().getLayoutDirection() == 1) {
            backgroundInsetLeft = backgroundInsetEnd;
            backgroundInsetRight = backgroundInsetStart;
        }
        return new Rect(backgroundInsetLeft, backgroundInsetTop, backgroundInsetRight, backgroundInsetBottom);
    }
}
