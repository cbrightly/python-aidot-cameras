package com.leedarson.newui.view.radar;

import android.os.Handler;
import android.os.Looper;
import com.leedarson.smartcamera.kvswebrtc.beans.a;
import com.leedarson.smartcamera.kvswebrtc.i0;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import java.util.ArrayList;
import java.util.List;

/* compiled from: RadarDataTestUnit */
public class d {
    public static List<a> a = new ArrayList();
    public static ChangeQuickRedirect changeQuickRedirect;
    private i0 b = new i0();
    private Handler c = new Handler(Looper.getMainLooper());

    public void a() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5416, new Class[0], Void.TYPE).isSupported) {
            a.clear();
            this.c.removeCallbacksAndMessages((Object) null);
        }
    }
}
