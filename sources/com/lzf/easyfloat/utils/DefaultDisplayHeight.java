package com.lzf.easyfloat.utils;

import android.content.Context;
import com.lzf.easyfloat.interfaces.OnDisplayHeight;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: DefaultDisplayHeight.kt */
public final class DefaultDisplayHeight implements OnDisplayHeight {
    public int getDisplayRealHeight(@NotNull Context context) {
        k.e(context, "context");
        return DisplayUtils.INSTANCE.rejectedNavHeight(context);
    }
}
