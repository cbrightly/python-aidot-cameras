package com.leedarson.serviceimpl.zendesk.reports;

import com.leedarson.serviceinterface.ZendeskService;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;

/* compiled from: ReportZendeskInfo */
public class a {
    public static ChangeQuickRedirect changeQuickRedirect;

    public void a(String message) {
        if (!PatchProxy.proxy(new Object[]{message}, this, changeQuickRedirect, false, 9206, new Class[]{String.class}, Void.TYPE).isSupported) {
            com.leedarson.log.elk.a.y(this).c(a.class.getSimpleName()).t("LdsZendesk").o("info").e(ZendeskService.ACTION_OPEN).u("message", message).a().b();
        }
    }
}
