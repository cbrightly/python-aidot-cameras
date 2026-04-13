package com.hjq.bar.style;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import com.hjq.bar.R$drawable;
import com.hjq.bar.R$mipmap;

/* compiled from: TitleBarTransparentStyle */
public class d extends a {
    public d(Context context) {
        super(context);
    }

    public Drawable getBackground() {
        return new ColorDrawable(0);
    }

    public Drawable m() {
        return p(R$mipmap.bar_icon_back_white);
    }

    public int l() {
        return -1;
    }

    public int j() {
        return -1;
    }

    public int k() {
        return -1;
    }

    public boolean f() {
        return false;
    }

    public Drawable c() {
        return new ColorDrawable(0);
    }

    public int b() {
        return 0;
    }

    public Drawable i() {
        return p(R$drawable.bar_selector_selectable_transparent);
    }

    public Drawable h() {
        return p(R$drawable.bar_selector_selectable_transparent);
    }
}
