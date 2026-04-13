package com.alibaba.android.arouter.facade;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.alibaba.android.arouter.facade.callback.b;
import com.alibaba.android.arouter.facade.template.c;
import tv.danmaku.ijk.media.player.IjkMediaCodecInfo;

/* compiled from: Postcard */
public final class a extends com.alibaba.android.arouter.facade.model.a {
    private Uri j;
    private Object k;
    private Bundle l;
    private int m;
    private int n;
    private c o;
    private boolean p;
    private Context q;
    private String r;
    private Bundle s;
    private int t;
    private int u;

    public Bundle v() {
        return this.s;
    }

    public int r() {
        return this.t;
    }

    public int s() {
        return this.u;
    }

    public c w() {
        return this.o;
    }

    public a I(c provider) {
        this.o = provider;
        return this;
    }

    public a() {
        this((String) null, (String) null);
    }

    public a(String path, String group) {
        this(path, group, (Uri) null, (Bundle) null);
    }

    public a(String path, String group, Uri uri, Bundle bundle) {
        this.m = 0;
        this.n = IjkMediaCodecInfo.RANK_SECURE;
        this.t = -1;
        this.u = -1;
        l(path);
        k(group);
        K(uri);
        this.l = bundle == null ? new Bundle() : bundle;
    }

    public boolean B() {
        return this.p;
    }

    public Object x() {
        return this.k;
    }

    public a J(Object tag) {
        this.k = tag;
        return this;
    }

    public Bundle t() {
        return this.l;
    }

    public int y() {
        return this.n;
    }

    public Uri z() {
        return this.j;
    }

    public a K(Uri uri) {
        this.j = uri;
        return this;
    }

    public Object C() {
        return D((Context) null);
    }

    public Object D(Context context) {
        return E(context, (b) null);
    }

    public Object E(Context context, b callback) {
        return com.alibaba.android.arouter.launcher.a.c().f(context, this, -1, callback);
    }

    public void F(Activity mContext, int requestCode) {
        G(mContext, requestCode, (b) null);
    }

    public void G(Activity mContext, int requestCode, b callback) {
        com.alibaba.android.arouter.launcher.a.c().f(mContext, this, requestCode, callback);
    }

    public a A() {
        this.p = true;
        return this;
    }

    public a L(Bundle bundle) {
        if (bundle != null) {
            this.l = bundle;
        }
        return this;
    }

    public a o(int flags) {
        this.m |= flags;
        return this;
    }

    public int u() {
        return this.m;
    }

    public a T(@Nullable String key, @Nullable String value) {
        this.l.putString(key, value);
        return this;
    }

    public a M(@Nullable String key, boolean value) {
        this.l.putBoolean(key, value);
        return this;
    }

    public a S(@Nullable String key, short value) {
        this.l.putShort(key, value);
        return this;
    }

    public a Q(@Nullable String key, int value) {
        this.l.putInt(key, value);
        return this;
    }

    public a R(@Nullable String key, long value) {
        this.l.putLong(key, value);
        return this;
    }

    public a O(@Nullable String key, double value) {
        this.l.putDouble(key, value);
        return this;
    }

    public a N(@Nullable String key, byte value) {
        this.l.putByte(key, value);
        return this;
    }

    public a P(@Nullable String key, float value) {
        this.l.putFloat(key, value);
        return this;
    }

    public String toString() {
        return "Postcard{uri=" + this.j + ", tag=" + this.k + ", mBundle=" + this.l + ", flags=" + this.m + ", timeout=" + this.n + ", provider=" + this.o + ", greenChannel=" + this.p + ", optionsCompat=" + this.s + ", enterAnim=" + this.t + ", exitAnim=" + this.u + "}\n" + super.toString();
    }

    public String p() {
        return this.r;
    }

    public Context q() {
        return this.q;
    }

    public void H(Context context) {
        this.q = context;
    }
}
