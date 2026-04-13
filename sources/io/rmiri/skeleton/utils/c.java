package io.rmiri.skeleton.utils;

import android.content.Context;
import android.util.TypedValue;

/* compiled from: ConverterUnitUtil */
public class c {
    public static int a(Context context, int dp) {
        return (int) TypedValue.applyDimension(1, (float) dp, context.getResources().getDisplayMetrics());
    }
}
