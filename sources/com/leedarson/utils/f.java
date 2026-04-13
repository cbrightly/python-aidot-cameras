package com.leedarson.utils;

import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.widget.SeekBar;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;

/* compiled from: DynamicSettingUtil */
public class f {
    public static ChangeQuickRedirect changeQuickRedirect;

    public static void a(SeekBar seekBar, int color) {
        if (!PatchProxy.proxy(new Object[]{seekBar, new Integer(color)}, (Object) null, changeQuickRedirect, true, 11493, new Class[]{SeekBar.class, Integer.TYPE}, Void.TYPE).isSupported) {
            ((LayerDrawable) seekBar.getProgressDrawable()).getDrawable(2).setColorFilter(color, PorterDuff.Mode.SRC);
            seekBar.invalidate();
        }
    }
}
