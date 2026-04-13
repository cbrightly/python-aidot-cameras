package com.hjq.bar.style;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import androidx.core.view.ViewCompat;
import com.hjq.bar.R$drawable;
import com.hjq.bar.R$mipmap;

/* compiled from: TitleBarNightStyle */
public class c extends a {
    public c(Context context) {
        super(context);
    }

    public Drawable getBackground() {
        return new ColorDrawable(ViewCompat.MEASURED_STATE_MASK);
    }

    public Drawable m() {
        return p(R$mipmap.bar_icon_back_white);
    }

    public int l() {
        return -855638017;
    }

    public int j() {
        return -285212673;
    }

    public int k() {
        return -855638017;
    }

    public boolean f() {
        return true;
    }

    public Drawable c() {
        return new ColorDrawable(-1);
    }

    public int b() {
        return 1;
    }

    public Drawable i() {
        return p(R$drawable.bar_selector_selectable_black);
    }

    public Drawable h() {
        return p(R$drawable.bar_selector_selectable_black);
    }
}
