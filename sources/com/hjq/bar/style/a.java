package com.hjq.bar.style;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;

/* compiled from: BaseTitleBarStyle */
public abstract class a implements com.hjq.bar.a {
    private Context a;

    public a(Context context) {
        this.a = context;
    }

    /* access modifiers changed from: protected */
    public Context o() {
        return this.a;
    }

    /* access modifiers changed from: protected */
    public Drawable p(int id) {
        if (Build.VERSION.SDK_INT >= 21) {
            return o().getResources().getDrawable(id, (Resources.Theme) null);
        }
        return o().getResources().getDrawable(id);
    }

    /* access modifiers changed from: protected */
    public int n(float dpValue) {
        return (int) ((dpValue * o().getResources().getDisplayMetrics().density) + 0.5f);
    }

    /* access modifiers changed from: protected */
    public int q(float spValue) {
        return (int) ((spValue * o().getResources().getDisplayMetrics().scaledDensity) + 0.5f);
    }

    public int a() {
        return n(0.0f);
    }

    public float e() {
        return (float) q(14.0f);
    }

    public float g() {
        return (float) q(16.0f);
    }

    public float d() {
        return (float) q(14.0f);
    }
}
