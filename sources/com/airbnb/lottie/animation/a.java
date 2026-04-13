package com.airbnb.lottie.animation;

import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Build;
import android.os.LocaleList;
import androidx.annotation.NonNull;
import com.airbnb.lottie.utils.g;

/* compiled from: LPaint */
public class a extends Paint {
    public a() {
    }

    public a(int flags) {
        super(flags);
    }

    public a(PorterDuff.Mode porterDuffMode) {
        setXfermode(new PorterDuffXfermode(porterDuffMode));
    }

    public a(int flags, PorterDuff.Mode porterDuffMode) {
        super(flags);
        setXfermode(new PorterDuffXfermode(porterDuffMode));
    }

    public void setTextLocales(@NonNull LocaleList locales) {
    }

    public void setAlpha(int alpha) {
        if (Build.VERSION.SDK_INT < 30) {
            setColor((g.c(alpha, 0, 255) << 24) | (16777215 & getColor()));
            return;
        }
        super.setAlpha(g.c(alpha, 0, 255));
    }
}
