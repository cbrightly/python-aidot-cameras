package com.leedarson.smarthome.robust.reporter;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.core.app.NotificationCompat;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

/* compiled from: CommonActivityReporter */
public class a {
    public static ChangeQuickRedirect changeQuickRedirect;

    public b a() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10715, new Class[0], b.class);
        if (proxy.isSupported) {
            return (b) proxy.result;
        }
        b builder = new b();
        com.leedarson.log.elk.a o = com.leedarson.log.elk.a.y(this).t("LDSRobust").b(8).p("").o("info");
        builder.a = o.x(System.currentTimeMillis() + "").u(NotificationCompat.CATEGORY_EVENT, "ActivityPT").u("code", "200").u(TypedValues.TransitionType.S_DURATION, 0).u("description", "");
        return builder;
    }
}
