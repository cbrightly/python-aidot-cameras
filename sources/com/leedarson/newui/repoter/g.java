package com.leedarson.newui.repoter;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.core.app.NotificationCompat;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

/* compiled from: IPCPlaybackV2Reporter */
public class g extends a {
    public static ChangeQuickRedirect changeQuickRedirect;

    private String e() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4562, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        return System.nanoTime() + "";
    }

    public b f() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4563, new Class[0], b.class);
        if (proxy.isSupported) {
            return (b) proxy.result;
        }
        b builder = new b();
        builder.a = b("").x(e()).t("Playback").u(NotificationCompat.CATEGORY_EVENT, "AiFeedback").u("code", "200").u(TypedValues.TransitionType.S_DURATION, 0).u("description", "");
        return builder;
    }

    public b g() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4564, new Class[0], b.class);
        if (proxy.isSupported) {
            return (b) proxy.result;
        }
        b builder = new b();
        builder.a = b("").x(e()).t("Playback").u(NotificationCompat.CATEGORY_EVENT, "EventList").u("code", "200").u(TypedValues.TransitionType.S_DURATION, 0).u("description", "");
        return builder;
    }

    public b i() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4565, new Class[0], b.class);
        if (proxy.isSupported) {
            return (b) proxy.result;
        }
        b builder = new b();
        builder.a = b("").x(e()).t("Playback").u(NotificationCompat.CATEGORY_EVENT, "VideoShare").u("code", "200").u(TypedValues.TransitionType.S_DURATION, 0).u("description", "");
        return builder;
    }

    public b k() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4566, new Class[0], b.class);
        if (proxy.isSupported) {
            return (b) proxy.result;
        }
        b builder = new b();
        builder.a = b("").x(e()).t("Playback").u(NotificationCompat.CATEGORY_EVENT, "VideoRecord").u("code", "200").u(TypedValues.TransitionType.S_DURATION, 0).u("description", "");
        return builder;
    }

    public b j() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4567, new Class[0], b.class);
        if (proxy.isSupported) {
            return (b) proxy.result;
        }
        b builder = new b();
        builder.a = b("").x(e()).t("Playback").u(NotificationCompat.CATEGORY_EVENT, "VIDEO_SNAP_SHOT").u("code", "200").u(TypedValues.TransitionType.S_DURATION, 0).u("description", "");
        return builder;
    }

    public b h() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4568, new Class[0], b.class);
        if (proxy.isSupported) {
            return (b) proxy.result;
        }
        b builder = new b();
        builder.a = b("").x(e()).t("Playback").u(NotificationCompat.CATEGORY_EVENT, "FirstFrameRender").u("code", "200").u(TypedValues.TransitionType.S_DURATION, 0).u("description", "");
        return builder;
    }
}
