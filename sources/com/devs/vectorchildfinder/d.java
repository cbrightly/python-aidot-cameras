package com.devs.vectorchildfinder;

import android.content.Context;
import android.content.res.Resources;
import android.widget.ImageView;
import com.devs.vectorchildfinder.VectorDrawableCompat;

/* compiled from: VectorChildFinder */
public class d {
    private VectorDrawableCompat a;

    public d(Context context, int vectorRes, ImageView imageView) {
        VectorDrawableCompat c = VectorDrawableCompat.c(context.getResources(), vectorRes, (Resources.Theme) null);
        this.a = c;
        c.i(false);
        imageView.setImageDrawable(this.a);
    }

    public VectorDrawableCompat.b a(String pathName) {
        return (VectorDrawableCompat.b) this.a.e(pathName);
    }
}
