package com.telink.ble.mesh.core.message;

import com.leedarson.log.elk.a;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.telink.ble.mesh.entity.MsgExtra;

public class PriorityMessage {
    public static ChangeQuickRedirect changeQuickRedirect;
    private Priority a = Priority.a(3, 0);
    private String b = "";

    public Priority b() {
        return this.a;
    }

    public void e(int order) {
        Object[] objArr = {new Integer(order)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 12442, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            this.a = Priority.a(3, order);
        }
    }

    public String a() {
        return this.b;
    }

    public void d(String extra) {
        this.b = extra;
    }

    public void c(MsgExtra extra) {
        if (!PatchProxy.proxy(new Object[]{extra}, this, changeQuickRedirect, false, 12443, new Class[]{MsgExtra.class}, Void.TYPE).isSupported) {
            d(extra.c());
            a.y(this).t("LdsBleMesh").x(extra.c).p(extra.b).o("debug").a().b();
        }
    }
}
