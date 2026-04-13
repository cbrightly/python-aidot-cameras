package com.leedarson.base.views;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import com.leedarson.module_base.R$id;
import com.leedarson.module_base.R$layout;
import com.leedarson.module_base.R$style;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.tutk.IOTC.AVIOCTRLDEFs;

/* compiled from: LoadingDialogView */
public class g extends Dialog {
    public static ChangeQuickRedirect changeQuickRedirect;
    private final Runnable a1 = new a();
    private ProgressBar c;
    private String d = "";
    private h f;
    private Handler p0 = new Handler();
    private final Runnable p1 = new b();
    /* access modifiers changed from: private */
    public long q = -1;
    /* access modifiers changed from: private */
    public boolean x = false;
    /* access modifiers changed from: private */
    public boolean y = false;
    /* access modifiers changed from: private */
    public boolean z = false;

    /* compiled from: LoadingDialogView */
    public class a implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_GETRECORD_REQ, new Class[0], Void.TYPE).isSupported) {
                boolean unused = g.this.x = false;
                long unused2 = g.this.q = -1;
                g.this.dismiss();
            }
        }
    }

    /* compiled from: LoadingDialogView */
    public class b implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_GETRECORD_RESP, new Class[0], Void.TYPE).isSupported) {
                boolean unused = g.this.y = false;
                if (!g.this.z) {
                    long unused2 = g.this.q = System.currentTimeMillis();
                    try {
                        g.this.show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public g(Context context) {
        super(context, R$style.myDialogTheme2);
    }

    public void onCreate(Bundle savedInstanceState) {
        if (!PatchProxy.proxy(new Object[]{savedInstanceState}, this, changeQuickRedirect, false, 782, new Class[]{Bundle.class}, Void.TYPE).isSupported) {
            super.onCreate(savedInstanceState);
            setContentView(R$layout.layout_loading);
            this.c = (ProgressBar) findViewById(R$id.pb_loading);
            this.f = new h();
            if (this.d.isEmpty()) {
                this.d = SharePreferenceUtils.getPrefString(getContext(), "themeColor", "");
            }
            this.d.isEmpty();
            this.f.setColor(Color.parseColor("#ffffff"));
            this.c.setIndeterminateDrawable(this.f);
        }
    }

    public void f(String color) {
        this.d = color;
    }

    public void g() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 783, new Class[0], Void.TYPE).isSupported) {
            this.q = -1;
            this.z = false;
            this.p0.removeCallbacks(this.a1);
            this.x = false;
            if (!this.y) {
                this.p0.postDelayed(this.p1, 450);
                this.y = true;
            }
        }
    }

    public void e() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_SETRECORD_REQ, new Class[0], Void.TYPE).isSupported) {
            this.z = true;
            this.p0.removeCallbacks(this.p1);
            this.y = false;
            long currentTimeMillis = System.currentTimeMillis();
            long j = this.q;
            long diff = currentTimeMillis - j;
            if (diff >= 300 || j == -1) {
                dismiss();
            } else if (!this.x) {
                this.p0.postDelayed(this.a1, 300 - diff);
                this.x = true;
            }
        }
    }

    public void onDetachedFromWindow() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_SETRECORD_RESP, new Class[0], Void.TYPE).isSupported) {
            super.onDetachedFromWindow();
            this.p0.removeCallbacks(this.a1);
            this.p0.removeCallbacks(this.p1);
        }
    }
}
