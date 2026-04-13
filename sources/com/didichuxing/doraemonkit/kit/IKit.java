package com.didichuxing.doraemonkit.kit;

import android.content.Context;
import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;
import org.jetbrains.annotations.Nullable;

/* compiled from: IKit.kt */
public interface IKit {
    int getCategory();

    @DrawableRes
    int getIcon();

    @StringRes
    int getName();

    void onAppInit(@Nullable Context context);

    void onClick(@Nullable Context context);
}
